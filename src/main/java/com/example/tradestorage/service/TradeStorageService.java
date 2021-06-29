package com.example.tradestorage.service;

import java.util.List;

import com.example.tradestorage.model.Trade;

public interface TradeStorageService {

	void saveTrade(Trade trade);

	List<Trade> findAll();
}
