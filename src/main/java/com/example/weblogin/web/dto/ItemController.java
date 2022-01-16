package com.example.weblogin.web.dto;

import com.example.weblogin.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/item/write")
    public String itemWriteForm(){

        return "itemwrite";
    }
}
