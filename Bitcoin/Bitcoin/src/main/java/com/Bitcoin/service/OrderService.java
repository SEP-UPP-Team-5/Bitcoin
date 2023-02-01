package com.Bitcoin.service;

import com.Bitcoin.dto.CaptureOrderResponseDTO;
import com.Bitcoin.dto.CreateOrderDTO;
import com.Bitcoin.dto.CreateOrderFromPaymentInfoDTO;
import com.Bitcoin.dto.CreateOrderResponseDTO;
import com.Bitcoin.model.BitcoinOrder;
import com.Bitcoin.repository.OrderRepository;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    public BitcoinOrder createOrder(CreateOrderFromPaymentInfoDTO orderDto){

        BitcoinOrder bitcoinOrder = new BitcoinOrder();
        bitcoinOrder.setPriceCurrency("usd");
        bitcoinOrder.setReceiveCurrency("btc");
        bitcoinOrder.setOrderId(orderDto.getOrderId());
        bitcoinOrder.setOrderAmount(orderDto.getTotalAmount());
        bitcoinOrder.setIsPaid(false);
        bitcoinOrder.setDate(new Date().toString());

        return orderRepository.save(bitcoinOrder);
    }

    public BitcoinOrder confirmOrder(CreateOrderResponseDTO orderResponseDTO){
        BitcoinOrder bitcoinOrder = orderRepository.findByOrderId(orderResponseDTO.getOrder_id());
        bitcoinOrder.setPaymentId(bitcoinOrder.getPaymentId());
        bitcoinOrder.setIsPaid(true);
        bitcoinOrder.setBitcoinAmount(orderResponseDTO.getPay_amount());
        bitcoinOrder.setMerchantAddress(orderResponseDTO.getPay_address());
        bitcoinOrder.setPaymentId(orderResponseDTO.getPayment_id().toString());

        String pspUrl = "http://localhost:8761/paymentInfo/confirmBitcoin";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject obj = new JSONObject();
        try {
            obj.put("webShopOrderId", orderResponseDTO.getOrder_id());
            obj.put("payerId", "bc1qpsmnmsf86d0h9swtwfxyhwevh900dvccqnxe6d");
            obj.put("bitcoinWalletAddress", orderResponseDTO.getPay_address());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpEntity<String> request = new HttpEntity<>(obj.toString(), headers);
        CaptureOrderResponseDTO captureOrderResponse = restTemplate.postForObject(pspUrl, request, CaptureOrderResponseDTO.class);
        System.out.println(captureOrderResponse);
        return orderRepository.save(bitcoinOrder);
    }

    public BitcoinOrder save(BitcoinOrder bitcoinOrder){return orderRepository.save(bitcoinOrder);}


}
