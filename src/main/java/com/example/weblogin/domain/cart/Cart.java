package com.example.weblogin.domain.cart;

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
@Getter
@Setter
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch =  FetchType.EAGER) // lazy
    @JoinColumn(name="user_id")
    User user;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "cart")
    private List<Cart_item> cart_items = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate;

    @PrePersist
    public void createDate(){
        this.createDate = LocalDate.now();
    }
}
