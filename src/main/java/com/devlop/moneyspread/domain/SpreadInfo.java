package com.devlop.moneyspread.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(indexes = {
        @Index(name = "spread_info_uniq_idx1", unique = true, columnList = "spre_user, spre_room, spre_token")},
        name = "spread_info")
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

    @CreatedDate
    @Column(name = "spre_date")
    private Instant spreDate = Instant.now();

    @Column(name = "spre_state")
    private String spre_state = "N";

}
