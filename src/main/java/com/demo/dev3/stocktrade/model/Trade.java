package com.demo.dev3.stocktrade.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Trade {
    @Id
    private Long id;
    private String type;
    private String symbol;
    private Integer shares;
    private Float price;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp timestamp;

    @ManyToOne(targetEntity = User.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private User user;
}
