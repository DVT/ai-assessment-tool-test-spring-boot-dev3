package com.demo.dev3.stocktrade.controller;

import com.demo.dev3.stocktrade.service.api.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/erase")
@RequiredArgsConstructor
public class ResourcesController {
    private final TradeService tradeService;

    @DeleteMapping
    public void eraseAll() {
        tradeService.deleteAll();
    }
}
