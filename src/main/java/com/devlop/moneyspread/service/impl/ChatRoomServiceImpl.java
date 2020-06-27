package com.devlop.moneyspread.service.impl;

import com.devlop.moneyspread.dao.ChatRoomRepository;
import com.devlop.moneyspread.domain.ChatRoom;
import com.devlop.moneyspread.service.ChatRoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ChatRoom saveChatRoom(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    @Transactional(readOnly = true)
    @Override
    public ChatRoom findByRoomIdAndUserId(String roomId, long userId) {
        return chatRoomRepository.findByRoomIdAndUserId(roomId, userId)
                .orElse(null);
    }
}
