package com.devlop.moneyspread.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "receive_user")
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

    @Column(name = "rec_user")
    private long recUser;

    @Column(name = "rec_date")
    private Instant recDate;


}
