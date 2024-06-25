package com.spring.jpastudy.chap02.repository;

import com.spring.jpastudy.chap02.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String>{

//쿼리메서드: 메서드에 이름에 특별한 규칙을 적용하면 SQL 이 규칙에 맞게 생성됨
//    stu_name 이라고 하면 안됨

//    Optional : 단일 조회
    List<Student> findByName(String name);

//    ?? 물음표 2개이다
    List<Student> findByCityAndMajor(String city, String major);

    //where major like "%major%"
    List<Student> findByMajorContaining(String major);

    //where major like "major%"
    List<Student> findByMajorStartingWith(String major);

    //where major like "%major"
    List<Student> findByMajorEndingWith(String major);

    //where age <= ?
//    List<Student> findByAgeLessThanEqual(int age);

//    native sql 사용하기 그냥 순수 sql 쓰고싶어여
    @Query(value = "SELECT * FROM tbl_student WHERE stu_name = :snm OR city = :city", nativeQuery = true)
    List<Student> getStudentByNameOrCity(@Param("snm") String name, @Param("city") String city);

    @Query(value = "SELECT * FROM tbl_student WHERE stu_name = ?1 OR city = ?2", nativeQuery = true)
    List<Student> getStudentByNameOrCity2(@Param("snm") String name, @Param("city") String city);
}
