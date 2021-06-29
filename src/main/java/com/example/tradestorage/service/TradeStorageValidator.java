package com.example.tradestorage.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.example.tradestorage.dao.TradeRepository;
import com.example.tradestorage.exception.InvalidTradeException;
import com.example.tradestorage.model.Trade;

@Component
@Qualifier("tradeStorageValidator")
public class TradeStorageValidator implements TradeStorageService {

	private static final Logger logger = LoggerFactory.getLogger(TradeStorageValidator.class);
	private transient final TradeStorageService tradeService;
	private transient final TradeRepository tradeRepository;

	public TradeStorageValidator(@Qualifier("tradeStorageServiceImpl") TradeStorageService tradeService,
			TradeRepository tradeRepository) {
		this.tradeService = tradeService;
		this.tradeRepository = tradeRepository;
	}

	/**
	 * Save Trade in DB
	 */
	@Override
	public void saveTrade(Trade trade) {
		if (isValidTrade(trade)) {
			logger.info("Saving the valid trade in DB");
			tradeService.saveTrade(trade);
		}
	}

	/**
	 * Validating Trade
	 * 
	 * @param trade
	 * @return
	 */
	public boolean isValidTrade(Trade trade) {
		if (validateMaturityDate(trade)) {
			Optional<Trade> exsitingTrade = tradeRepository.findById(trade.getTradeId());
			if (exsitingTrade.isPresent()) {
				return validateVersion(trade, exsitingTrade.get());
			} else {
				return true;
			}
		} else {
			logger.error("Maturity date for " + trade.getTradeId() + " is before today's Date");
			throw new InvalidTradeException("BU102",
					"Maturity date for " + trade.getTradeId() + " is before today's Date");
		}
	}

	/**
	 * validating if the lower version is being received by the store it will reject
	 * the trade and throw an exception.
	 */
	private boolean validateVersion(Trade trade, Trade oldTrade) {

		if (trade.getVersion() >= oldTrade.getVersion()) {
			return true;
		} else {
			logger.error("Lower version is received for " + trade.getTradeId() + " than existing.");
			throw new InvalidTradeException("BU101",
					"Lower version is received for " + trade.getTradeId() + " than existing.");
		}

	}

	/**
	 * Store should not allow the trade which has less maturity date then today date
	 * 
	 * @param trade
	 * @return
	 */
	private boolean validateMaturityDate(Trade trade) {
		return trade.getMaturityDate().isBefore(LocalDate.now()) ? false : true;
	}

	public List<Trade> findAll() {
		logger.info("Fetching all Trades from DB");
		return tradeService.findAll();

	}
}
