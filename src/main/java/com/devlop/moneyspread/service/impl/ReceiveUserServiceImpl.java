package com.devlop.moneyspread.service.impl;

import com.devlop.moneyspread.dao.ReceiveUserRepository;
import com.devlop.moneyspread.domain.ReceiveUser;
import com.devlop.moneyspread.service.ReceiveUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service("receiveUserService")
public class ReceiveUserServiceImpl implements ReceiveUserService {

    private final ReceiveUserRepository receiveUserRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ReceiveUser saveReceiveUser(ReceiveUser receiveUser) {
        return receiveUserRepository.save(receiveUser);
    }
}
