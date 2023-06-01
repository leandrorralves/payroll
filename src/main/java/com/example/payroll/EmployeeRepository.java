package com.example.payroll;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByName(String name);

    //using JPQL
    @Query("SELECT e FROM Employee e WHERE e.name like ?1%")
    List<Employee> findByFirstName(String firstName);

    //using Native query
    @Query(value = "SELECT * FROM Employee e WHERE e.name like %?1",
            nativeQuery = true)
    List<Employee> findByLastName(String lastName);

    //using JPQL and param annotation
    @Query("SELECT e FROM Employee e WHERE e.role = :role and e.name = :name")
    List<Employee> findUserByRoleAndName( @Param("role") String role, @Param("name") String name);
}