package com.example.weblogin.web.dto;

import com.example.weblogin.config.auth.PrincipalDetails;
import com.example.weblogin.domain.cart.Cart;
import com.example.weblogin.domain.cart_item.Cart_item;
import com.example.weblogin.domain.item.Item;
import com.example.weblogin.domain.user.User;
import com.example.weblogin.domain.user.UserRepository;
import com.example.weblogin.service.CartService;
import com.example.weblogin.service.ItemService;
import com.example.weblogin.service.UserPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserPageController {

    private final UserPageService userPageService;
    private final CartService cartService;
    private final ItemService itemService;

    // 구매자 마이페이지
    @GetMapping("user/{id}")
    public String userPage(@PathVariable("id") Integer id, Model model){
        User user = userPageService.findUser(id);
        model.addAttribute("user",user);
        return "mypage";
    }
    // 구매자 카트목록
    @GetMapping("user/{id}/cart")
    public String userCart(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = userPageService.findUser(id);
        Cart userCart = user.getCart();
        List<Cart_item> cart_items = cartService.getCartItems(userCart.getId());
        int totalCost = cartService.getTotalCost(cart_items);

        model.addAttribute("cartItems", cart_items);
        model.addAttribute("user", user);
        model.addAttribute("totalCost",totalCost);

        return "usercart";
    }

    @PostMapping("user/{id}/cart/{itemId}")
    public String addCartItem(@PathVariable("id") Integer id, @PathVariable("itemId") Integer itemId, int quantity,
                              @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = userPageService.findUser(id);
        Item item = itemService.getItem(itemId);
        cartService.addItem(user, item, quantity);
        List<Cart_item> list = cartService.getCartItems(user.getCart().getId());
        user.getCart().setCart_items(list);
        return "redirect:/item/view?id=" +itemId;

    }

    @GetMapping("user/{id}/cart/{cart_itemId}/delete")
    public String deleteCartItem(@PathVariable("id") Integer id, @PathVariable("cart_itemId") Integer cart_itemId,Model model,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails){
        cartService.deleteCart_item(cart_itemId);
        User user = userPageService.findUser(id);
        List<Cart_item> list = cartService.getCartItems(user.getCart().getId());
        user.getCart().setCart_items(list);

        return "redirect:/user/{id}/cart";
    }

    // 충전 폼
    @GetMapping("user/{id}/chargeForm")
    public String charge(@PathVariable("id") Integer id, Model model,
                         @AuthenticationPrincipal PrincipalDetails principalDetails){
        if(principalDetails.getUser().getId() == id) {
            User user = userPageService.findUser(id);
            model.addAttribute("user", user);
            return "charge";
        }else{
            return "redirect:/index";
        }
    }

    // 충전 진행
    @PostMapping("user/{id}/chargePro")
    public String chargePro(int money, @PathVariable("id") Integer id){
        userPageService.chargeMoney(id, money);

        return "redirect:/index";
    }
}
