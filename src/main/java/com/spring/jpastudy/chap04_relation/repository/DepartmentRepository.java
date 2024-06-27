package com.spring.jpastudy.chap04_relation.repository;

import com.spring.jpastudy.chap04_relation.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    //fetch join 문이다 ㅇ
    @Query("SELECT d FROM Department d JOIN FETCH d.employees")
    List<Department> getFetchEmployees();

}
