package com.example.weblogin.service;

import com.example.weblogin.domain.cart.Cart;
import com.example.weblogin.domain.cart.CartRepository;
import com.example.weblogin.domain.cart_item.Cart_item;
import com.example.weblogin.domain.cart_item.Cart_itemRepository;
import com.example.weblogin.domain.item.Item;
import com.example.weblogin.domain.user.User;
import com.example.weblogin.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {

    private final Cart_itemRepository cart_itemRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public void addItem(User user, Item item, int quantity){
        if(item.getStock() - quantity < 0){
            return;
        }
        Cart cart = user.getCart();
        if(user.getId() == cart.getUser().getId()){
            Cart_item cart_item = new Cart_item();
            cart_item.setCart(cart);
            cart_item.setItem(item);
            cart_item.setCount(quantity);
            cart_itemRepository.save(cart_item);
        }
    }

    public Cart_item getCart_item(int id){
        return cart_itemRepository.findById(id).get();
    }

    public List<Cart_item> getCartItems(int id){
       List<Cart_item> temp = cart_itemRepository.findAll();
       List<Cart_item> list = new ArrayList<>();
       for(Cart_item item : temp){
           if(item.getCart().getId() == id){
               list.add(item);
           }
       }
       return list;
    }

    public void deleteCart_item(int id){
        cart_itemRepository.deleteById(id);

    }
    public int getTotalCost(List<Cart_item> list){
        int totalcost = 0;
        for(Cart_item cartItem : list){
            totalcost += cartItem.getItem().getPrice() * cartItem.getCount();
        }
        return totalcost;
    }

    @Transactional
    public void payment(int totalCost, User user){
        User findUser = userRepository.findById(user.getId()).get();
        List<Cart_item> cart_items = cart_itemRepository.findAllByCart(findUser.getCart());

        System.out.println(cart_items);
        // 리스트 확인 ->
        if(totalCost <= findUser.getMoney()) {
            int remain = findUser.getMoney() - totalCost;
            System.out.println(findUser.getMoney());
            // user 저장
            // cart에 있는 아이템들 삭제
//            cart_itemRepository.deleteAllByCart(findUser.getCart());
            cart_itemRepository.deleteAllInBatch(findUser.getCart().getCart_items());
            findUser.updateMoney(remain);
        }
    }
}
