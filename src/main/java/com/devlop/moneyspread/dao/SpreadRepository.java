package com.devlop.moneyspread.dao;

import com.devlop.moneyspread.domain.Spread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpreadRepository extends JpaRepository<Spread, Long> {
}
