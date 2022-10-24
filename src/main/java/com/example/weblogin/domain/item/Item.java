package com.example.weblogin.domain.item;

import com.example.weblogin.domain.cart_item.Cart_item;
import com.example.weblogin.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    private String fileName;

    private String filePath;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "item" , cascade = CascadeType.ALL)
    private List<Cart_item> cart_items = new ArrayList<>();

    private boolean isSoldOut; // 판매 여부

    private int count; // 팔린 개수

    private int stock; // 상품 재고

    private String text; // 상품 설명

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate;

    @PrePersist
    public void createDate(){
        this.createDate = LocalDate.now();
    }

}
