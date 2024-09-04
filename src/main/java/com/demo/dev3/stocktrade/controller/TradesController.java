package com.demo.dev3.stocktrade.controller;

import com.demo.dev3.stocktrade.model.Trade;
import com.demo.dev3.stocktrade.service.api.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/trades")
@RequiredArgsConstructor
public class TradesController {
    private final TradeService tradeService;

    @PostMapping
    public ResponseEntity<Trade> create(@RequestBody Trade trade) {
        return tradeService.create(trade);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trade> get(@PathVariable("id") Long tradeId) {
        return tradeService.get(tradeId);
    }

    @GetMapping
    public ResponseEntity<List<Trade>> getAll() {
        return tradeService.getAll();
    }

    @GetMapping("/users/{userID}")
    public ResponseEntity<List<Trade>> getAllByUser(@PathVariable("userID") Long userId) {
        return tradeService.getAllByUser(userId);
    }

    @GetMapping("/stocks/{stockSymbol}")
    public ResponseEntity<List<Trade>> getAllByStockSymbolAndTradeTypeAndDateRange(
            @PathVariable("stockSymbol") String stockSymbol,
            @RequestParam(value = "type", required = false) String tradeType,
            @RequestParam(value = "start", required = false) String startDate,
            @RequestParam(value = "end", required = false) String endDate
    ) {
        return tradeService.getAllByStockSymbolAndTradeTypeAndDateRange(stockSymbol, tradeType, startDate, endDate);
    }
}
