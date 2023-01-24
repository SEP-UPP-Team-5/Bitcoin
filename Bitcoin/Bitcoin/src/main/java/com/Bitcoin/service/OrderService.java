package com.Bitcoin.service;

import com.Bitcoin.dto.CreateOrderDTO;
import com.Bitcoin.dto.CreateOrderFromPaymentInfoDTO;
import com.Bitcoin.model.BitcoinOrder;
import com.Bitcoin.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    public BitcoinOrder createOrder(CreateOrderFromPaymentInfoDTO orderDto){

        BitcoinOrder bitcoinOrder = new BitcoinOrder();
       // bitcoinOrder.setPriceCurrency(orderDto.getPrice_currency());
        //bitcoinOrder.setReceiveCurrency(orderDto.getPay_currency());
        bitcoinOrder.setOrderId(orderDto.getOrderId());
        bitcoinOrder.setOrderAmount(orderDto.getTotalAmount());
        bitcoinOrder.setIsPaid(false);
        bitcoinOrder.setDate(new Date().toString());

        return orderRepository.save(bitcoinOrder);
    }

    public BitcoinOrder save(BitcoinOrder bitcoinOrder){return orderRepository.save(bitcoinOrder);}


}
