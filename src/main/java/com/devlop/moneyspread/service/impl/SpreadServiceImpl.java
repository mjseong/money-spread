package com.devlop.moneyspread.service.impl;

import com.devlop.moneyspread.common.MoneySpreadConstant;
import com.devlop.moneyspread.common.UuidUtils;
import com.devlop.moneyspread.domain.ChatRoom;
import com.devlop.moneyspread.domain.ReceiveMoney;
import com.devlop.moneyspread.domain.ReceiveUser;
import com.devlop.moneyspread.domain.SpreadInfo;
import com.devlop.moneyspread.domain.dto.MoneySpreadDto;
import com.devlop.moneyspread.domain.dto.MoneySpreadInfoDto;
import com.devlop.moneyspread.domain.dto.ReceiveCompleteInfoDto;
import com.devlop.moneyspread.exception.TokenExprieException;
import com.devlop.moneyspread.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@Slf4j
@Service("spreadService")
public class SpreadServiceImpl implements SpreadService {

    private final MoneyDistributionService moneyDistributionService;
    private final SpreadTokenService spreadTokenService;
    private final SpreadInfoService spreadInfoService;
    private final ReceiveMoneyService receiveMoneyService;
    private final ReceiveUserService receiveUserService;
    private final ChatRoomService chatRoomService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String getMoneySpreadToken(MoneySpreadDto moneySpreadDto, long spreUserId, String spreRoomId) {

        String spreToken = spreadTokenService.generateToken();
        Set<Long> users = moneySpreadDto.getSpreUsers();
        long spreTotalMoney = moneySpreadDto.getSpreMoney();
        long spresUserSize = 0L;

        if(users != null){
            /**
             * 뿌릴 대상자중에 뿌리기 기능 요청자는 제외시키기 위해
             */
            users.remove(spreUserId);
            spresUserSize = users.size();
        }

        List<Long> moneys = moneyDistributionService.distributeMoney(spreTotalMoney, spresUserSize, "AVG");
        int resultSumUsers = saveUsers(users, spreRoomId);
        try{


            String spreId = UuidUtils.generateUuid(MoneySpreadConstant.ID_PREFIX_SPREADINFO);

            SpreadInfo spreadInfo = SpreadInfo.builder()
                                    .spreId(spreId)
                                    .spreRoom(spreRoomId)
                                    .spreToken(spreToken)
                                    .spreUser(spreUserId)
                                    .spreMoney(spreTotalMoney)
                                    .build();

            spreadInfo = spreadInfoService.saveSpreadInfo(spreadInfo);

            if(spreadInfo == null){
                throw new Exception("fail not save spreadInfo ");
            }

            Instant recCreateDate = Instant.now();
            int resultSumMoneys = saveMonies(moneys, spreId, recCreateDate);

            return spreToken;

        }catch (Exception e){
            log.error(e.getMessage());
        }

        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public long getMoney(long recUserId, String spreRoomId, String spreToken) throws Exception {

        SpreadInfo spreadInfo = spreadInfoService.findSpreadInfo(spreRoomId, spreToken);

        /**
         * 배포한 토큰 만료시간 10분 이후 사용못함.
         */
        Instant currentDate = Instant.now();
        Instant expireDate = spreadInfo.getSpreDate();
        expireDate = expireDate.plus(MoneySpreadConstant.EXPIRE_TOKEN_TIME_MINUTE, ChronoUnit.MINUTES);

       if(expireDate.isBefore(currentDate)){
           throw new TokenExprieException("expire date Token");
       }

        /**
         * token 발급자 get not money check.
         */
        if(spreadInfo.getSpreUser() == recUserId)
        {
            throw new IllegalArgumentException("equal token by generation user");
        }

        /**
         * 대화방 이용자인지 검사
         */
        ChatRoom chatRoom = chatRoomService.findByRoomIdAndUserId(spreRoomId, recUserId);
        if(chatRoom == null){
            throw new NoSuchFieldException("recUserId not found chat_room : " + spreRoomId);
        }

        ReceiveMoney receiveMoney = receiveMoneyService.findTop1BySpreIdAndState(spreadInfo.getSpreId(), "N");
        ReceiveUser receiveUser = ReceiveUser.builder()
                                    .receiveMoney(receiveMoney)
                                    .spreRoom(spreadInfo.getSpreRoom())
                                    .spreToken(spreadInfo.getSpreToken())
                                    .recUser(recUserId)
                                    .build();

        try{
            receiveUser = receiveUserService.saveReceiveUser(receiveUser);

            if(receiveUser!=null){
                //return is transaction commit recUseState : Y
                receiveMoney.setRecUseState(MoneySpreadConstant.RECEIVE_MONEY_STATE_USED);
                return receiveMoney.getRecMoney();
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return -1;
        }
        return 0;
    }

    @Override
    public MoneySpreadInfoDto getMoneySpreadInfos(long spreUserId, String spreRoomId, String spreToken) throws Exception {

        /**
         * 본인이 발급한 토큰만 조회가능
         */
        SpreadInfo spreadInfo = spreadInfoService.findSpreadInfo(spreUserId, spreRoomId, spreToken);

        /**
         * 조회 발급후 7일간만 가능 이후는 상태값 D로 변경
         */
        Instant currentDate = Instant.now();
        Instant expireDate = spreadInfo.getSpreDate();
        expireDate = expireDate.plus(MoneySpreadConstant.EXPIRE_RECORD_HIST_DAY, ChronoUnit.DAYS);

        if(expireDate.isBefore(currentDate)){
            /**
             * 7일이 지나면 정보를 삭제 하기 위해 상태를 D로 변경
             */
            spreadInfo.setSpreState(MoneySpreadConstant.SPREAD_INFO_STATE_DELETE);
            spreadInfoService.saveSpreadInfo(spreadInfo);
            throw new TokenExprieException("expire date Token");
        }

        if(spreadInfo != null){
            List<ReceiveCompleteInfoDto> receiveCompleteInfoDtos = receiveMoneyService.findAllBySpreRoomAndSpreTokenAndSpreId(spreRoomId, spreToken, spreadInfo.getSpreId());

            long recCompleteMoney = receiveCompleteInfoDtos.stream()
                                                            .mapToLong(p->p.getReceiveMoney())
                                                            .sum();

            MoneySpreadInfoDto moneySpreadInfoDto = new MoneySpreadInfoDto();
            moneySpreadInfoDto.setSpreadDate(spreadInfo.getSpreDate());
            moneySpreadInfoDto.setSpreadMoney(spreadInfo.getSpreMoney());
            moneySpreadInfoDto.setReceiveCompleteMoney(recCompleteMoney);
            moneySpreadInfoDto.setReceiveCompleteInfo(receiveCompleteInfoDtos);

            return moneySpreadInfoDto;
        }

        return null;
    }

    private int saveUsers(Set<Long> users, String spreRoomId){
        return users.stream()
                .mapToInt(user->{
                    ChatRoom chatRoom = ChatRoom.builder()
                            .roomId(spreRoomId)
                            .userId(user)
                            .build();
                    if(chatRoomService.findByRoomIdAndUserId(spreRoomId, user) == null){
                        chatRoomService.saveChatRoom(chatRoom);
                    }
                    return 1;

                })
                .sum();
    }

    private int saveMonies(List<Long> moneys, String spreId, Instant recCreateDate) {
        return moneys.stream()
                .mapToInt(money -> {
                    ReceiveMoney receiveMoney = ReceiveMoney.builder()
                            .recMoneyId(UuidUtils.generateUuid(MoneySpreadConstant.ID_PREFIX_RECEIVEMONEY))
                            .spreId(spreId)
                            .recMoney(money)
                            .recCreateDate(recCreateDate)
                            .build();
                    receiveMoney = receiveMoneyService.saveReceiveMoney(receiveMoney);
                    if(receiveMoney != null){
                        return 1;
                    }else{
                        return 0;
                    }
                })
                .sum();
    }
}
