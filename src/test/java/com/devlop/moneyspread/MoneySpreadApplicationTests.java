package com.devlop.moneyspread;

import com.devlop.moneyspread.common.UuidUtils;
import com.devlop.moneyspread.domain.dto.MoneySpreadDto;
import com.devlop.moneyspread.service.MoneyDistributionService;
import com.devlop.moneyspread.service.SpreadService;
import com.devlop.moneyspread.service.SpreadTokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MoneySpreadApplicationTests {

	@Autowired
    SpreadTokenService spreadTokenService;

	@Autowired
	MoneyDistributionService moneyDistributionService;

	@Autowired
	SpreadService spreadService;

	@Test
	@DisplayName("token generation")
	void contextLoads() {

		for(int a=0; a<10; a++){
			System.out.println("token : " + spreadTokenService.generateToken());
			System.out.println("uuid : " + UuidUtils.generateUuid("spre_"));
		}

	}

	@Test
	@DisplayName("Distribution Money")
	void distributeMoneyTest(){
		List<Long> moneys = moneyDistributionService.distributeMoney(10000, 18, "AVG");
		System.out.println("moneys : " + moneys);

		long sum = moneys.stream()
				.mapToLong(p->p.intValue())
				.sum();

		System.out.println("moneys sum: " + sum);

		Assertions.assertEquals(10000, sum);

	}

	@Test
	void getMoneySpreadTokenTest(){

		MoneySpreadDto moneySpreadDto = new MoneySpreadDto();

		String roomId = "room_id_01";
		long reqUserId = 112;
		moneySpreadDto.setSpreCount(11);
		moneySpreadDto.setSpreMoney(10000);


		String token = spreadService.getMoneySpreadToken(moneySpreadDto, 112, "room_id_01");
		Assertions.assertNotEquals(null, token);
		System.out.println("token : " + token);
	}


}
