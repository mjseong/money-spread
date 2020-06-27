package com.devlop.moneyspread.dao;

import com.devlop.moneyspread.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByRoomIdAndUserId(String roomID, long UserId);
}
