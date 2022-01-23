package com.example.weblogin.web.dto;

import com.example.weblogin.config.auth.PrincipalDetails;
import com.example.weblogin.domain.cart.Cart;
import com.example.weblogin.domain.cart_item.Cart_item;
import com.example.weblogin.domain.item.Item;
import com.example.weblogin.domain.user.User;
import com.example.weblogin.service.CartService;
import com.example.weblogin.service.ItemService;
import com.example.weblogin.service.UserPageService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MyPageController {

    private final UserPageService userPageService;
    private final ItemService itemService;
    private final CartService cartService;
    //---------------------------------------------------------------------------SellerPage
    // 판매자 마이페이지
    @GetMapping("/seller/{id}")
    public String sellerPage(@PathVariable("id") Integer id, Model model) {
        User user = userPageService.findUser(id);
        model.addAttribute("user",user);
        return "mypage";
    }

    // 판매목록
    @GetMapping("/seller/{id}/salelist")
    public String salePage(@PathVariable("id")Integer id, Model model){
        User user = userPageService.findUser(id);
        List<Item> list = itemService.getItems();
        List<Item> sellList = userPageService.sellList(user,list);
        model.addAttribute("user",user);
        model.addAttribute("sellList",sellList);
        return "salelist";
    }
//---------------------------------------------------------------------------UserPage
    // 구매자 마이페이지
    @GetMapping("user/{id}")
    public String userPage(@PathVariable("id") Integer id, Model model){
        User user = userPageService.findUser(id);
        model.addAttribute("user",user);
        return "mypage";
    }
    @GetMapping("user/{id}/cart")
    public String userCart(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = principalDetails.getUser();
        Cart userCart = user.getCart();
        List<Cart_item> cart_items = userCart.getCart_items();

        model.addAttribute("cartItems", cart_items);
        model.addAttribute("user", user);
        return "usercart";
    }

    @PostMapping("user/{id}/cart/{itemId}")
    public String addCartItem(@PathVariable("id") Integer id, @PathVariable("itemId") Integer itemId, int quantity,
                              @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = principalDetails.getUser();
        Item item = itemService.getItem(itemId);
        cartService.addItem(user, item, quantity);
        return "redirect:/item/view?id=" +itemId;

    }

    @GetMapping("user/{id}/cart/{cart_itemId}/delete")
    public String deleteCartItem(@PathVariable("id") Integer id, @PathVariable("cart_itemId") Integer cart_itemId,Model model,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails){
        cartService.deleteCart_item(cart_itemId);
//        User user = principalDetails.getUser();
//        Cart cart = user.getCart();
//
//        List<Cart_item> cart_items = cart.getCart_items();
//
//        cart_items.remove(itemService.getItem(cart_itemId));

        return "redirect:/user/{id}/cart";
    }
}
