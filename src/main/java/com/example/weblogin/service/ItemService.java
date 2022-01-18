package com.example.weblogin.service;

import com.example.weblogin.domain.item.Item;
import com.example.weblogin.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public void upload(Item item){
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

    public void deleteItem(Integer id){
        itemRepository.deleteById(id);
    }
}
