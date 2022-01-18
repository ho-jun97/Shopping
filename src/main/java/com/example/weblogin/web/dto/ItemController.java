package com.example.weblogin.web.dto;

import com.example.weblogin.domain.item.Item;
import com.example.weblogin.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;

    // 상품 등록 폼
    @GetMapping("/item/upload")
    public String itemUploadForm(){

        return "itemupload";
    }

    // 상품 등록
    @PostMapping("/item/uploadpro")
    public String itemUploadPro(Item item){
        itemService.upload(item);
        return "redirect:/index";
    }

    // 상세 페이지
    @GetMapping("item/view")
    public String itemView(Model model, Integer id){
        model.addAttribute("item", itemService.getItem(id));
        return "itemview";
    }

    // 수정 폼
    @GetMapping("item/modify/{id}")
    public String itemModify(Model model,@PathVariable("id") Integer id){
        model.addAttribute("item",itemService.getItem(id));
        return "itemmodify";
    }

    // 수정 업데이트
    @PostMapping("item/update/{id}")
    public String itemUpadte(@PathVariable("id") Integer id, Item updateItem){
        Item Item = itemService.getItem(id);
        Item after=itemService.update(Item, updateItem);

        itemService.upload(after);
        return "redirect:/item/view"+"?id="+id;
    }

    // 상품 삭제
    @GetMapping("item/delete/{id}")
    public String itemDelete(@PathVariable("id") Integer id){
        itemService.deleteItem(id);
        return "redirect:/index";
    }
}
