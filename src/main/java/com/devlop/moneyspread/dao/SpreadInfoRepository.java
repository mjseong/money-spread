package com.devlop.moneyspread.dao;

import com.devlop.moneyspread.domain.SpreadInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpreadInfoRepository extends JpaRepository<SpreadInfo, Long> {
}
