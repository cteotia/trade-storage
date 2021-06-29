package com.example.tradestorage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.tradestorage.model.Trade;
import com.example.tradestorage.service.TradeStorageService;

@RestController
public class TradeStorageController {
    
    transient private final TradeStorageService tradeService;
    
    /**
     * Constructor
     * @param tradeService
     */
    public TradeStorageController(@Qualifier("tradeStorageValidator") TradeStorageService tradeService)
    {
    	this.tradeService = tradeService;
    }
    
    
    /**
     * Save Data of Trade in database after validation
     * @param trade
     * @return
     */
    @PostMapping("/trade")
    public String saveTrade(@RequestBody Trade trade){
           tradeService.saveTrade(trade);
        return "Trade "+ trade.getTradeId()+" store successfully in database!!!!";
    }

    /**
     * Find all Trades saved in DB
     * @return
     */
    @GetMapping("/trade")
    public List<Trade> findAllTrades(){
        return tradeService.findAll();
    }
}
