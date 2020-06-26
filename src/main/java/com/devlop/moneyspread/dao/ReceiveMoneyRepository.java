package com.devlop.moneyspread.dao;

import com.devlop.moneyspread.domain.ReceiveMoney;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceiveMoneyRepository extends JpaRepository<ReceiveMoney, String> {

    Optional<ReceiveMoney> findTop1BySpreIdAndRecState(String spreId, String recState);
}
