package com.example.weblogin.domain.order_item;

import com.example.weblogin.domain.item.Item;
import com.example.weblogin.domain.order.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Order_item{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int count; // 개수

    private int price; // 금액

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;
}
