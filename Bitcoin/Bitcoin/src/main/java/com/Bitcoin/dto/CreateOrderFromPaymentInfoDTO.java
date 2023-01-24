package com.Bitcoin.dto;

import lombok.Data;

@Data
public class CreateOrderFromPaymentInfoDTO {

    private Double totalAmount;
    private String transactionId; //id transakcije sa psp
    private String orderId;  //id ordera sa web shopa, odnosno sa psp
}
