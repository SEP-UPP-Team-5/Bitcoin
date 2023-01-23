package com.Bitcoin.dto;

import lombok.Data;

@Data
public class CreateOrderDTO {

    private Double price_amount;
    private String price_currency;
    private String pay_currency;
    private String order_id;
}
