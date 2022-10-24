package com.example.weblogin.service;

import com.example.weblogin.domain.item.Item;
import com.example.weblogin.domain.item.ItemRepository;
import com.example.weblogin.domain.user.User;
import com.example.weblogin.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public void upload(Item item, MultipartFile file){
        String projectPath = System.getProperty("user.dir") + "/src/main/webapp/files/";
        System.out.println(projectPath);
        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        try {
            file.transferTo(saveFile);
            item.setFileName(fileName);
            item.setFilePath("/files/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        itemRepository.save(item);
    }

    public List<Item> getItems(){
        List<Item> list = itemRepository.findAll();
        return list;
    }

    public Item getItem(Integer id){
        return itemRepository.findById(id).get();
    }

    public Item update(Item item, Item updateItem){
        item.setName(updateItem.getName()); // 상품 이름
        item.setPrice(updateItem.getPrice());
        item.setStock(updateItem.getStock());
        item.setText(updateItem.getText());

        return item;
    }

    @Transactional
    public void deleteItem(Integer id){
        Item item = itemRepository.findById(id).get();

        itemRepository.delete(item);
    }
}
