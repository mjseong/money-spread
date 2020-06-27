package com.devlop.moneyspread.service;

import com.devlop.moneyspread.domain.ChatRoom;

public interface ChatRoomService {

    ChatRoom saveChatRoom(ChatRoom chatRoom);
    ChatRoom findByRoomIdAndUserId(String roomId, long userId);
}
