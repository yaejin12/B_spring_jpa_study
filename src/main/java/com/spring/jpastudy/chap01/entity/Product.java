package com.spring.jpastudy.chap01.entity;

import javax.persistence.*;

@Entity
@Table(name = "tbl_product")
public class Product {
    @Id
    @Column(name= "prod_id")
    private Long id;//pk

    @Column(name= "prod_nm", length = 30, nullable = false)
    private String name;//상품명

    @Column(name= "price")
    private int price;//상품 가격

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Category category;//상품 카테고리

    public enum Category{
        FOOD, FASHION, ELECTRONIC
    }

}
