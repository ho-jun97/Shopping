package com.example.weblogin.web.dto;

import com.example.weblogin.config.auth.PrincipalDetails;
import com.example.weblogin.domain.item.Item;
import com.example.weblogin.domain.user.User;
import com.example.weblogin.service.ItemService;
import com.example.weblogin.service.UserPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ShopController {
    private final ItemService itemService;
    private final UserPageService userPageService;
    @GetMapping("/")
    public String NondLoginPage(Model model){
        List<Item> itemList = itemService.getItems();
        model.addAttribute("itemList",itemList);

        return "index";
    }

    @GetMapping("/index")
    public String home(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        List<Item> itemList = itemService.getItems();
        User user = userPageService.findUser(principalDetails.getUser().getId());
        model.addAttribute("user", user);
        model.addAttribute("itemList", itemList);
        return "index";
    }
}
