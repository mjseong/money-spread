package com.devlop.moneyspread.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(indexes = {
        @Index(name = "receive_user_uniq_idx1", unique = true, columnList = "spre_room,spre_token,rec_user")},
        name = "receive_user")
public class ReceiveUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rec_id")
    private long recId;

//    @Column(name = "rec_money_id")
//    private String recMoneyId;

    @OneToOne(optional = false)
    @JoinColumn(name = "rec_money_id")
    private ReceiveMoney receiveMoney;

    @Column(name = "spre_room")
    private String spreRoom;

    @Column(name = "spre_token")
    private String spreToken;

    @Column(name = "rec_user")
    private long recUser;

    @Builder.Default
    @CreatedDate
    @Column(name = "rec_date")
    private Instant recDate = Instant.now();


}
