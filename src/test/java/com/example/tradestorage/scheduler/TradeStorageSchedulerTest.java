package com.example.tradestorage.scheduler;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
public class TradeStorageSchedulerTest {

	@SpyBean
	private TradeStorageScheduler tradeStorageScheduler;

	@Test
	public void whenWaitOneSecond_thenScheduledIsCalledAtLeastTenTimes() {
		await().atMost(Duration.ONE_MINUTE)
				.untilAsserted(() -> verify(tradeStorageScheduler, atLeast(2)).updateExpiredTrade());
	}

}