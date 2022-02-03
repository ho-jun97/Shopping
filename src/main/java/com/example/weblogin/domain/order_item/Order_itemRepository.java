package com.example.weblogin.domain.order_item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Order_itemRepository extends JpaRepository<Order_item,Integer> {
}
