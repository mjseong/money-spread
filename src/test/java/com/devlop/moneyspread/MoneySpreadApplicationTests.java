package com.devlop.moneyspread;

import com.devlop.moneyspread.service.MoneyDistributionService;
import com.devlop.moneyspread.service.TokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MoneySpreadApplicationTests {

	@Autowired
	TokenService tokenService;

	@Autowired
	MoneyDistributionService moneyDistributionService;

	@Test
//	@DisplayName("token generation")
	void contextLoads() {

		for(int a=0; a<1; a++){
			System.out.println("token : " + tokenService.generateToken());
		}

	}

	@Test
//	@DisplayName("Distribution Money")
	void distributeMoneyTest(){
		List<Long> moneys = moneyDistributionService.distributeMoney(10000, 18, "AVG");
		System.out.println("moneys : " + moneys);

		long sum = moneys.stream()
				.mapToLong(p->p.intValue())
				.sum();

		System.out.println("moneys sum: " + sum);

		Assertions.assertEquals(10000, sum);

	}

}
