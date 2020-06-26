package com.devlop.moneyspread.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(indexes = {
        @Index(name = "receive_user_uniq_idx1", unique = true, columnList = "spre_room,spre_token")},
        name = "receive_user")
@Getter
@Setter
public class ReceiveUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rec_id")
    private long recId;

    @Column(name = "spre_room")
    private String spreRoom;

    @Column(name = "spre_token")
    private String spreToken;

    @Column(name = "rec_money")
    private long recMoney;

    @Column(name = "rec_state")
    private String recState;

    @Column(name = "rec_create_date")
    private Instant recCreateDate;

    @Column(name = "rec_user")
    private long recUser;

    @Column(name = "rec_date")
    private Instant recDate;


}
