package com.devlop.moneyspread.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "spread_info")
@Getter
@Setter
public class SpreadInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "spre_id")
    private long spreId;

    @Column(name = "spre_user")
    private long spreUser;

    @Column(name = "spre_room")
    private String spreRoom;

    @Column(name = "spre_token")
    private String spreToken;

    @Column(name = "spre_money")
    private long spreMoney;

    @Column(name = "spre_date")
    private Instant spreDate;

    @Column(name = "spre_state")
    private String spre_state;

}
