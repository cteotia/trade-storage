package com.example.tradestorage.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.tradestorage.dao.TradeRepository;
import com.example.tradestorage.model.Trade;

@Service
@Qualifier("tradeStorageServiceImpl")
public class TradeStorageServiceImpl implements TradeStorageService {

	@Autowired
	private TradeRepository tradeRepository;

	/**
	 * Save Trade in DB
	 */
	@Override
	public void saveTrade(Trade trade) {
		trade.setCreatedDate(LocalDate.now());
		tradeRepository.save(trade);
	}

	/**
	 *  Find all Trade data
	 */
	@Override
	public List<Trade> findAll() {
		return tradeRepository.findAll();
	}

}
