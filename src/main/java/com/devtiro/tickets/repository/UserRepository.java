package com.devtiro.tickets.repository;


import com.devtiro.tickets.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {


    public List<User>  findByEmailContainingOrderById(String email);

//    @Query("""
//    Select u
//    FROM User u
//    where u.email LIKE CONCAT('%',:email,'%')
//    AND u.createdAt > :date
//    """)
//    public List<User>  findUsers(@Param("email") String email,
//                                 @Param("date")LocalDate date);

}
