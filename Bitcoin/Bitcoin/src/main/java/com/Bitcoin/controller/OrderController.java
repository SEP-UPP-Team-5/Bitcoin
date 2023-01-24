package com.Bitcoin.controller;
import com.Bitcoin.dto.CreateOrderDTO;
import com.Bitcoin.dto.CreateOrderFromPaymentInfoDTO;
import com.Bitcoin.dto.CreateOrderResponseDTO;
import com.Bitcoin.model.BitcoinOrder;
import com.Bitcoin.service.OrderService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Value("${coingate.authkey}")
    private String apiKey;


    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderFromPaymentInfoDTO orderDto) throws JSONException {
        BitcoinOrder bitcoinOrder = orderService.createOrder(orderDto);

        String bitcoinApiUrl = "https://api-sandbox.nowpayments.io/v1/payment";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", apiKey);
        JSONObject obj = new JSONObject();
        obj.put("price_amount", orderDto.getTotalAmount());
        obj.put("price_currency", "usd");
        obj.put("pay_currency", "btc");
        obj.put("ipn_callback_url", "https://nowpayments.io");
        obj.put("order_id", orderDto.getOrderId());

        HttpEntity<String> request = new HttpEntity<>(obj.toString(), headers);
        CreateOrderResponseDTO orderResponseDto = restTemplate.postForObject(bitcoinApiUrl, request, CreateOrderResponseDTO.class);
        System.out.println(orderResponseDto.toString());
        orderService.confirmOrder(orderResponseDto);
        //todo: redirect user-a na successful url

        return new ResponseEntity<>(orderResponseDto.getPay_address(), HttpStatus.OK);

    }

    public static void browse(String url) {
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
