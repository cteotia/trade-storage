package com.example.tradestorage.scheduler;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.tradestorage.dao.TradeRepository;

@Component
public class TradeStorageScheduler {

	private static final Logger logger = LoggerFactory.getLogger(TradeStorageScheduler.class);

	@Autowired
	private TradeRepository tradeRepository;

	/**
	 * Scheduled a job to update expired falg
	 */
	@Scheduled(cron = "${trade.expiry.schedule}")
	public void updateExpiredTrade() {
		logger.info("Updating Trade expiry flag");
		tradeRepository.findAll().stream().filter(t -> t.getMaturityDate().isBefore(LocalDate.now())).forEach(t -> 
		{
			t.setExpired("Y");
			logger.info("Trades which needs to be updated are - "+t);
			tradeRepository.save(t);
			logger.info("Trades which get updated are - " +t);
		});
	}
}