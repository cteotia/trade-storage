package com.example.tradestorage;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.tradestorage.controller.TradeStorageController;
import com.example.tradestorage.exception.InvalidTradeException;
import com.example.tradestorage.model.Trade;

@SpringBootTest
public class TradeStorageSolutionApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private TradeStorageController tradeStorageController;

	@Test
	void testSaveTradeForSuccessful() {
		String responseEntity = tradeStorageController.saveTrade(createTrade("T3", 2, "CP-3","B3",LocalDate.now().plusDays(3),LocalDate.now()));
		Assertions.assertEquals("Trade T3 store successfully in database!!!!", responseEntity);
	}

	@Test
	void testSaveTradeForPastMaturityDate() {
		try {
			tradeStorageController.saveTrade(createTrade("T2", 1, "CP-2","B2",LocalDate.now().minusDays(3),LocalDate.now()));
		} catch (InvalidTradeException ie) {
			Assertions.assertEquals("Maturity date for T2 is before today's Date", ie.getMessage());
			Assertions.assertEquals("BU102", ie.getErrorCode());
		}
	}

	@Test
	void testTradeValidateAndStoreWhenOldVersion() {
		String responseEntity = tradeStorageController.saveTrade(createTrade("T1", 2, "CP-1","B1",LocalDate.now().plusDays(3),LocalDate.now()));
		Assertions.assertEquals("Trade T1 store successfully in database!!!!", responseEntity);
		try {
			tradeStorageController.saveTrade(createTrade("T1", 1, "CP-1","B1",LocalDate.now().plusDays(3),LocalDate.now()));

		} catch (InvalidTradeException e) {
			Assertions.assertEquals("Lower version is received for T1 than existing.", e.getMessage());
			Assertions.assertEquals("BU101", e.getErrorCode());
		}
	}

	@Test
	void testSaveTradeOverwrite() {
		String responseEntity = tradeStorageController.saveTrade(createTrade("T1", 2, "CP-1","B1",LocalDate.now().plusDays(3),LocalDate.now()));
		Assertions.assertEquals("Trade T1 store successfully in database!!!!", responseEntity);
		Trade trade2 = createTrade("T1", 3, "CP-1","B1",LocalDate.now().plusDays(3),LocalDate.now());
		trade2.setBookId("B2");
		String responseEntity2 = tradeStorageController.saveTrade(trade2);
		Assertions.assertEquals("Trade T1 store successfully in database!!!!", responseEntity2);
		List<Trade> tradeList2 = tradeStorageController.findAllTrades();
		Assertions.assertEquals("T1", tradeList2.get(0).getTradeId());
		Assertions.assertEquals(3, tradeList2.get(0).getVersion());
		Assertions.assertEquals("B2", tradeList2.get(0).getBookId());
	}

	private Trade createTrade(String tradeId, int version, String counterPartyId,String bookId,LocalDate maturityDate,LocalDate createdDate) {
		Trade trade = new Trade();
		trade.setTradeId(tradeId);
		trade.setVersion(version);
		trade.setCounterPartyId(counterPartyId);
		trade.setBookId(bookId);
		trade.setMaturityDate(maturityDate);
		trade.setMaturityDate(createdDate);
		trade.setExpired("N");
		return trade;
	}
}
