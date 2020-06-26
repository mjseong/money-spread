package com.devlop.moneyspread.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "receive_money")
public class ReceiveMoney {

    @Id
    @Column(name = "rec_money_id")
    private String recMoneyId;

    @Column(name="spre_id")
    private String spreId;

    @Column(name="rec_money")
    private long recMoney;

    @Builder.Default
    @Column(name="rec_use_state")
    private String recUseState = "N";

    @Column(name="rec_create_date")
    private Instant recCreateDate;

    @OneToOne(mappedBy = "receiveMoney")
    private ReceiveUser receiveUser;
}
