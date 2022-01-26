package com.example.weblogin.service;

import com.example.weblogin.domain.cart.Cart;
import com.example.weblogin.domain.cart_item.Cart_item;
import com.example.weblogin.domain.cart_item.Cart_itemRepository;
import com.example.weblogin.domain.item.Item;
import com.example.weblogin.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {

    private final Cart_itemRepository cart_itemRepository;

    public void addItem(User user, Item item, int quantity){
        if(item.getStock() - quantity < 0){
            return;
        }
        Cart cart = user.getCart();
        if(user.getId() == cart.getUser().getId()){
            List<Cart_item> cart_items = cart.getCart_items();

            Cart_item cart_item = new Cart_item();
            cart_item.setCart(cart);
            cart_item.setItem(item);
            cart_item.setCount(quantity);
            cart_items.add(cart_item);
            cart_itemRepository.save(cart_item);
        }
    }
    public Cart_item getCart_item(int id){
        return cart_itemRepository.findById(id).get();
    }


    public void deleteCart_item(int id){
        cart_itemRepository.deleteById(id);
    }
}
