package com.devlop.moneyspread.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "spread")
@Getter
@Setter
public class Spread {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "money")
    private long money;

}
