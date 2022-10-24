package com.example.weblogin.web.dto;

import com.example.weblogin.domain.item.Item;
import com.example.weblogin.domain.user.User;
import com.example.weblogin.service.CartService;
import com.example.weblogin.service.ItemService;
import com.example.weblogin.service.UserPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class SalePageController {
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
}
