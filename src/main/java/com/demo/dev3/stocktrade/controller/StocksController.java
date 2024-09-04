package com.demo.dev3.stocktrade.controller;

import com.demo.dev3.stocktrade.service.api.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/stocks")
@RequiredArgsConstructor
public class StocksController {
    private final TradeService tradeService;

    @GetMapping("/{stockSymbol}/price")
    public ResponseEntity<Object> getAllByStockSymbolAndTradeTypeAndDateRange(
            @PathVariable("stockSymbol") String stockSymbol,
            @RequestParam(value = "start", required = false) String startDate,
            @RequestParam(value = "end", required = false) String endDate
    ) {
        return tradeService.getPriceByStockSymbolAndDateRange(stockSymbol, startDate, endDate);
    }
}
