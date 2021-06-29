package com.example.tradestorage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tradestorage.model.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade,String> {
}
