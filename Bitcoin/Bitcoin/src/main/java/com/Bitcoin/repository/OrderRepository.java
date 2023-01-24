package com.Bitcoin.repository;

import com.Bitcoin.model.BitcoinOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<BitcoinOrder, Long> {
    BitcoinOrder findByOrderId(String orderId);
}
