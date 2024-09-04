package com.demo.dev3.stocktrade.dto;

import lombok.Data;

/**
 * @author Victim Musundire
 */
@Data
public class StockPriceDto {
    private String symbol;
    private Float lowest;
    private Float highest;
}
