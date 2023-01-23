package com.Bitcoin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BitcoinOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "order_id") //bitcoin order id
    private String orderId;
    @Column(name="price_currency")
    private String priceCurrency;
    @Column(name="receive_currency")
    private String receiveCurrency;
    private Double orderAmount;
    private String bitcoinAmount;
    private Boolean isPaid;
    private String paymentId;
    private String merchantAddress;
    private String date; // vreme kad je izvrseno placanje na bitcoinu

}
