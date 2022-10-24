package com.example.weblogin.domain.cart_item;

import com.example.weblogin.domain.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Cart_itemRepository extends JpaRepository<Cart_item, Integer> {
    List<Cart_item> findCart_itemByItemId(int id);

    List<Cart_item> findAllByCart(Cart cart);

    void deleteAllInBatchByCart(Cart cart);
}
