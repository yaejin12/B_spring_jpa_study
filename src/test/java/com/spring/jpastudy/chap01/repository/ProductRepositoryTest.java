package com.spring.jpastudy.chap01.repository;

import com.spring.jpastudy.chap01.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.spring.jpastudy.chap01.entity.Product.Category.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
//@Rollback
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @BeforeEach //테스트 전 기본 값 넣고 진행
    void insertBeforeTest(){
        Product p1 = Product.builder()
                .name("아이폰")
                .category(ELECTRONIC)
                .price(2000000)
                .build();
        Product p2 = Product.builder()
                .name("탕수육")
                .category(FOOD)
                .price(20000)
                .build();
        Product p3 = Product.builder()
                .name("구두")
                .category(FASHION)
                .price(300000)
                .build();
        Product p4 = Product.builder()
                .name("주먹밥")
                .category(FOOD)
                .price(1500)
                .build();

        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
        productRepository.save(p4);
    }


    @Test
    @DisplayName("상품을 데이터 베이스에 저장한다")
    void saveTest() {
        //given
        Product product = Product.builder()
                .name("정장")
//                .price(12000)
//                .category(Product.Category.FASHION)
                .build();
        //when
        //insert 후 저장된 데이터의 객체를 반환
        Product saved = productRepository.save(product);
        //then
        assertNotNull(saved);
    }


    @Test
    @DisplayName("1번 상품을 삭제한다")
    void deleteTest() {
        //given
        Long id = 1L;
        //when
        productRepository.deleteById(id);
        //then
        Product foundProduct = productRepository.findById(id)
                .orElse(null);//orElse() : null이면 어떻게 할거냐   orElseThrow:예외 처리를 할거다

        assertNull(foundProduct);

    }



    @Test
    @DisplayName("3번 상품을 단일조회하면 그 상품명이 구두이다")
    void findOneTest() {
        //given
        Long id=  3L;
        //when
        Product foundProduct = productRepository.findById(id).orElse(null);

        //then
        assertEquals("구두", foundProduct.getName());

    }

    @Test
    @DisplayName("상품을 전체조회하면 상품의 총 개수가 4개이다.")
    void findAllTest() {
        //given

        //when
        List<Product> productList = productRepository.findAll();

        //then
        System.out.println("\n\n\n");

        productList.forEach(System.out::println);

        System.out.println("\n\n\n");

        assertEquals(4, productList.size());
    }

    @Test
    @DisplayName("2번 상품의 이름과 카테고리를 수정한다")
    void modifyTest() {
        //given
        Long id = 2L;
        String newName = "청소기";
        Product.Category newCategory = ELECTRONIC;
        //when

        /*jpa 에서느 수정메서드를 따로 제공하지 않습ㄴ디ㅏ
        * 단일 조회를 수행한 후 setter 를 통해 값을 변경하고
        * 다시 save 를 하면 insert 대신에 update 뭄이 나갑니다
        * */

        Product product = productRepository.findById(id).orElse(null);
        product.setName(newName);
        product.setCategory(newCategory);

        Product saved = productRepository.save(product);
        //then
        assertEquals(newName, saved.getName());
    }



}