package com.Bitcoin.dto;

import lombok.Data;

@Data
public class CreateOrderResponseDTO {

    private Long payment_id;
    private String payment_status;
    private String pay_address;  //adresa na koju se placa
    private String price_amount;
    private String price_currency;
    private String pay_currency;
    private String pay_amount;
    private String order_id;
}
