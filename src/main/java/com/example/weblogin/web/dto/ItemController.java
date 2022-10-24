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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;
    private final UserPageService userPageService;

    // 상품 등록 폼
    @GetMapping("/item/upload")
    public String itemUploadForm(){

        return "itemupload";
    }

    // 상품 등록
    @PostMapping("/item/uploadpro")
    public String itemUploadPro(Item item,MultipartFile file, @AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("file : " + file);
        User user = userPageService.findUser(principalDetails.getUser().getId());
        item.setUser(principalDetails.getUser());
        itemService.upload(item, file);
        return "redirect:/index";
    }

    // 상세 페이지
    @GetMapping("item/view")
    public String itemView(Model model, Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = userPageService.findUser(principalDetails.getUser().getId());
        model.addAttribute("item", itemService.getItem(id));
        model.addAttribute("user",user);
        return "itemview";
    }

    // 수정 폼
    @GetMapping("item/modify/{id}")
    public String itemModify(Model model,@PathVariable("id") Integer id){
            model.addAttribute("item", itemService.getItem(id));
            return "itemmodify";
    }

    // 수정 업데이트
    @PostMapping("item/update/{id}")
    public String itemUpadte(@PathVariable("id") Integer id, Item updateItem,
                             @AuthenticationPrincipal PrincipalDetails principalDetails, MultipartFile file){
        if(itemService.getItem(id).getUser().getId() == principalDetails.getUser().getId()) {
            Item Item = itemService.getItem(id);
            Item after = itemService.update(Item, updateItem);
            itemService.upload(after, file);
        }
        return "redirect:/item/view?id="+id;
    }

    // 상품 삭제
    @GetMapping("item/delete/{id}")
    public String itemDelete(@PathVariable("id") Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        itemService.deleteItem(id);

        return "redirect:/index";
    }
}
