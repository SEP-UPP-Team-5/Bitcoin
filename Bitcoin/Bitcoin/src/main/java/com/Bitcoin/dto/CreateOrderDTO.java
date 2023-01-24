package com.Bitcoin.dto;

import lombok.Data;

@Data
public class CreateOrderDTO {

    private Double price_amount; // treba
    private String price_currency; //treba
    private String pay_currency; //sa bitcoina
    private String order_id; //treba
}
