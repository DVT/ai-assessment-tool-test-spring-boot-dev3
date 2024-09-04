package com.demo.dev3.stocktrade.repository;

import com.demo.dev3.stocktrade.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Victim Musundire
 */
public interface TradeRepository extends JpaRepository<Trade, Long> {
    long countBySymbol(String stockSymbol);

    List<Trade> findAllBySymbolAndTypeAndTimestampBetween(String stockSymbol, String tradeType, Timestamp startTimestamp, Timestamp endTimestamp);

    List<Trade> findAllBySymbolAndTimestampBetween(String stockSymbol, Timestamp startTimestamp, Timestamp endTimestamp);

    List<Trade> findAllByUserId(Long userId);
}
