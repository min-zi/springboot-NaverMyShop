package com.ming.navermyshop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor // 기본생성자를 만들어줌
@Getter // get 함수를 일괄적으로 자동 생성
@Entity // DB 테이블 역할을 한다고 스프링에게 알려줌
public class Product extends Timestamped{

    @GeneratedValue(strategy = GenerationType.AUTO) // ID가 자동 생성, 자동 증가 함
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private int lprice;

    @Column(nullable = false)
    private int myprice;

    public Product(ProductRequestDto requestdto) {
        this.title = requestdto.getTitle();
        this.image = requestdto.getImage();
        this.link = requestdto.getLink();
        this.lprice = requestdto.getLprice();
        this.myprice = 0;
    }

    public void update(ProductMypriceRequestDto mypridto) {
        this.myprice = mypridto.getMyprice();
    }
}
