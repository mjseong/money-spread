package com.devlop.moneyspread.dao;

import com.devlop.moneyspread.domain.ReceiveMoney;
import com.devlop.moneyspread.domain.dto.ReceiveCompleteInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReceiveMoneyRepository extends JpaRepository<ReceiveMoney, String> {

    Optional<ReceiveMoney> findTop1BySpreIdAndRecState(String spreId, String recState);

    @Query(value =
            "SELECT new com.devlop.moneyspread.domain.dto.ReceiveCompleteInfoDto(rm.recMoney, ru.recUser) " +
            "FROM ReceiveMoney rm " +
            "INNER JOIN ReceiveUser ru " +
            "ON rm.recMoneyId = ru.receiveMoney.recMoneyId " +
            "AND rm.spreId = :spreInfoId " +
            "AND ru.spreRoom = :spreRoom " +
            "AND ru.spreToken = :spreToken "
    )
    List<ReceiveCompleteInfoDto> findAllBySpreRoomAndSpreTokenAndSpreId(@Param("spreRoom") String spreRoom,
                                                                        @Param("spreToken") String spreToken,
                                                                        @Param("spreInfoId") String spreInfoId);
}
