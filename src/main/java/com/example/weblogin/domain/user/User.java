package com.example.weblogin.domain.user;

import com.example.weblogin.domain.cart.Cart;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity // DB에 테이블 자동 생성
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true) // username 중목 안됨
    private String username; // 아이디
    private String password; // 비밀번호
    private String name;
    private String email;
    private String address;
    private String phone;
    private String role; // 권한

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate;

    @PrePersist
    public void createDate(){
        this.createDate = LocalDate.now();
    }
}
