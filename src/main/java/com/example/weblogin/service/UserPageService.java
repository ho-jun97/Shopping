package com.example.weblogin.service;

import com.example.weblogin.domain.item.Item;
import com.example.weblogin.domain.user.User;
import com.example.weblogin.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserPageService {
    private final UserRepository userRepository;

    public User findUser(Integer id){
        return userRepository.findById(id).get();
    }

    public List<Item> sellList(User user,List<Item> itemList){
        List<Item> list = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            if(user.equals(itemList.get(i).getUser())){
                list.add(itemList.get(i));
            }
        }
        return list;
    }
}
