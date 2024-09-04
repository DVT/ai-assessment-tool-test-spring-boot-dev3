package com.demo.dev3.stocktrade.service.impl;

import com.demo.dev3.stocktrade.dto.StockPriceDto;
import com.demo.dev3.stocktrade.model.Trade;
import com.demo.dev3.stocktrade.repository.TradeRepository;
import com.demo.dev3.stocktrade.repository.UserRepository;
import com.demo.dev3.stocktrade.service.api.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Victim Musundire
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
    private final TradeRepository tradeRepository;
    private final UserRepository userRepository;

    @Override
    public void deleteAll() {
        tradeRepository.deleteAll();
    }

    @Override
    public ResponseEntity<Trade> create(Trade trade) {
        Long tradeId = trade.getId();
        Optional<Trade> optionalTrade = tradeRepository.findById(tradeId);
        if (optionalTrade.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Trade savedTrade = tradeRepository.save(trade);
        return new ResponseEntity<>(savedTrade, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Trade> get(Long tradeId) {
        return tradeRepository.findById(tradeId)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<Trade>> getAll() {
        List<Trade> trades = tradeRepository.findAll();
        trades.sort(Comparator.comparing(Trade::getId));
        return ResponseEntity.ok(trades);
    }

    @Override
    public ResponseEntity<List<Trade>> getAllByUser(Long userId) {
        return userRepository.findById(userId).map(user -> {
            List<Trade> trades = tradeRepository.findAllByUserId(userId);
            trades.sort(Comparator.comparing(Trade::getId));
            return ResponseEntity.ok(trades);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<Trade>> getAllByStockSymbolAndTradeTypeAndDateRange(
            String stockSymbol,
            String tradeType,
            String startDate,
            String endDate
    ) {
        long byStockSymbol = tradeRepository.countBySymbol(stockSymbol);
        if (byStockSymbol == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Timestamp startTimestamp = getTimestamp(startDate, true);
        Timestamp endTimestamp = getTimestamp(endDate, false);
        List<Trade> trades = tradeRepository.findAllBySymbolAndTypeAndTimestampBetween(stockSymbol, tradeType, startTimestamp, endTimestamp);
        trades.sort(Comparator.comparing(Trade::getId));
        return ResponseEntity.ok(trades);
    }

    @Override
    public ResponseEntity<Object> getPriceByStockSymbolAndDateRange(String stockSymbol, String startDate, String endDate) {
        long byStockSymbol = tradeRepository.countBySymbol(stockSymbol);
        if (byStockSymbol == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Timestamp startTimestamp = getTimestamp(startDate, true);
        Timestamp endTimestamp = getTimestamp(endDate, false);
        List<Trade> trades = tradeRepository.findAllBySymbolAndTimestampBetween(stockSymbol, startTimestamp, endTimestamp);
        Map<String, String> response = new HashMap<>();
        if (trades.isEmpty()) {
            response.put("message", "There are no trades in the given date range");
            return ResponseEntity.ok(response);
        }
        trades.sort(Comparator.comparing(Trade::getPrice));
        Trade lowestPriceTrade = trades.get(0);
        Trade highestPriceTrade = trades.get(trades.size() - 1);
        StockPriceDto stockPriceDto = new StockPriceDto();
        stockPriceDto.setSymbol(stockSymbol);
        stockPriceDto.setLowest(lowestPriceTrade.getPrice());
        stockPriceDto.setHighest(highestPriceTrade.getPrice());
        return ResponseEntity.ok(stockPriceDto);
    }

    private Timestamp getTimestamp(String startDate, boolean isRequireStartOfDay) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(startDate, dateTimeFormatter);
        return Timestamp.valueOf(LocalDateTime.of(localDate, isRequireStartOfDay? LocalTime.MIN: LocalTime.MAX));
    }
}
