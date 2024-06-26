package com.spring.jpastudy.chap03_page;

import com.spring.jpastudy.chap02.entity.Student;
import com.spring.jpastudy.chap02.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class StudentPageTest {

    @Autowired
    StudentRepository repository;

    @BeforeEach
    void bulkInsert(){
        for (int i = 1; i < 147 ; i++) {
            Student s = Student.builder()
                    .name("김시골" +i)
                    .city("도시" +i)
                    .major("디자인" +i)
                    .build();
            repository.save(s);

        }
    }


    @Test
    @DisplayName("기본적인 페이지 조회 테스트")
    void basicPageTest() {
        //given
        int pageNo = 6;
        int amount = 10;

        //페이지 정보 객체를 생성 (PAGEABLE)
        //야기서는 페이지 번호가 zero-based임 : 1페이지는 0으로 취급
        Pageable pageInfo = PageRequest.of(pageNo - 1, amount);
        //when
        Page<Student> students = repository.findAll(pageInfo);

        //실질적인 데이터 꺼내기
        List<Student> studentList = students.getContent();

        //총 페이지 수
        int totalPages = students.getTotalPages();

        //총 학생수
        long count = students.getTotalElements();

        //then
        System.out.println("\n\n\n");
        studentList.forEach(System.out::println);
        System.out.println("\n\n\n");
        System.out.println("count = " + count);
        System.out.println("totalPages = " + totalPages);
        System.out.println("\n\n\n");
    }


    @Test
    @DisplayName("ㅔ이징+정렬")
    void pagingAndSortTest() {
        //given
//        Sort.by(필드명)
        Pageable pageInfo = PageRequest.of(
                0,10,
//                정열
//                Sort.by("name").descending()
//                여러 조건으로 정렬
                Sort.by(
                        Sort.Order.desc("name"),
                        Sort.Order.asc("city")
                )
        );
        //when
        Page<Student> studentPage = repository.findAll(pageInfo);

        //then
        studentPage.getContent().forEach(System.out::println);
    }


}