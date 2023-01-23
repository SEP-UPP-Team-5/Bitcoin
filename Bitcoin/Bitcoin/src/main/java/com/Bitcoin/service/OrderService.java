package com.Bitcoin.service;

import com.Bitcoin.dto.CreateOrderDTO;
import com.Bitcoin.model.BitcoinOrder;
import com.Bitcoin.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    public BitcoinOrder createOrder(CreateOrderDTO orderDto){

        BitcoinOrder bitcoinOrder = new BitcoinOrder();
        bitcoinOrder.setPriceCurrency(orderDto.getPrice_currency());
        bitcoinOrder.setReceiveCurrency(orderDto.getPay_currency());
        bitcoinOrder.setOrderId(orderDto.getOrder_id());
        bitcoinOrder.setOrderAmount(orderDto.getPrice_amount());
        bitcoinOrder.setIsPaid(false);
        bitcoinOrder.setDate(new Date().toString());

        return orderRepository.save(bitcoinOrder);
    }

    public BitcoinOrder save(BitcoinOrder bitcoinOrder){return orderRepository.save(bitcoinOrder);}


}
