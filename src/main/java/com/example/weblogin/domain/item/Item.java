package com.example.weblogin.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Entity

@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 고유 id

    private String name; // 상품 이름

    private int price; // 상품 가격

    private boolean isSoldOut; // 판매 여부

    private int count; // 팔린 개수

    private int stock; // 상품 재고

    private String text; // 상품 설명

}
