package com.example.weblogin.web.dto;

import com.example.weblogin.config.auth.PrincipalDetails;
import com.example.weblogin.domain.cart_item.Cart_item;
import com.example.weblogin.domain.user.User;
import com.example.weblogin.service.CartService;
import com.example.weblogin.service.OrderService;
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
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    private final UserPageService userPageService;

    @GetMapping("user/{id}/order")
    public String orderForm(@PathVariable("id") Integer id, Model model,
                            @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = userPageService.findUser(id);
        List<Cart_item> cart_items = cartService.getCartItems(user.getCart().getId());
        int totalCost = cartService.getTotalCost(cart_items);

        model.addAttribute("user",user);
        model.addAttribute("totalCost", totalCost);

        return "order";
    }

    @PostMapping("user/{id}/orderPro")
    public String orderPro(@PathVariable("id") Integer id, Model model,
                           @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = userPageService.findUser(id);
        List<Cart_item> cart_items = cartService.getCartItems(user.getCart().getId());
        int totalCost = cartService.getTotalCost(cart_items);
        cartService.payment(totalCost,user);
        return "redirect:/user/{id}/cart";
    }
}
