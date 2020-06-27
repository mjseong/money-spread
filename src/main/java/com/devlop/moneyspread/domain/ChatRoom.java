package com.devlop.moneyspread.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(indexes = {
        @Index(name = "chat_room_uniq_idx1", unique = true, columnList = "room_id,user_id")},
        name = "chat_room")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    @Column(name = "room_id")
    private String roomId;

    @Column(name = "user_id")
    long userId;
}
