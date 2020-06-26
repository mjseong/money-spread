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
        @Index(name = "spread_info_uniq_idx1", unique = true, columnList = "spre_room, spre_token, spre_user")},
        name = "spread_info")
public class SpreadInfo {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "spre_id")
    private String spreId;

    @Column(name = "spre_room")
    private String spreRoom;

    @Column(name = "spre_token")
    private String spreToken;

    @Column(name = "spre_user")
    private long spreUser;

    @Column(name = "spre_money")
    private long spreMoney;

    @Builder.Default
    @CreatedDate
    @Column(name = "spre_date")
    private Instant spreDate = Instant.now();

    @Builder.Default
    @Column(name = "spre_state")
    private String spreState = "N";

}
