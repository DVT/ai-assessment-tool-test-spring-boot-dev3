package com.demo.dev3.stocktrade.service.api;

import com.demo.dev3.stocktrade.model.Trade;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author Victim Musundire
 */
public interface TradeService {
    void deleteAll();

    ResponseEntity<Trade> create(Trade trade);

    ResponseEntity<Trade> get(Long tradeId);

    ResponseEntity<List<Trade>> getAll();
    ResponseEntity<List<Trade>> getAllByUser(Long userId);

    ResponseEntity<List<Trade>> getAllByStockSymbolAndTradeTypeAndDateRange(String stockSymbol, String tradeType, String startDate, String endDate);

    ResponseEntity<Object> getPriceByStockSymbolAndDateRange(String stockSymbol, String startDate, String endDate);
}
