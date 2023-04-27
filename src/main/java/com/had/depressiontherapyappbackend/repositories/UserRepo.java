package com.had.depressiontherapyappbackend.repositories;

import com.had.depressiontherapyappbackend.entities.Demographics;
import com.had.depressiontherapyappbackend.entities.Doctor;
import com.had.depressiontherapyappbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    interface UserProjection {
        Demographics getDemographics();
        String getEmail();
        Doctor getDoctor();
    }

    List<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.userRole.roleId = :roleId")
    List<UserProjection> findByRoleId(@Param("roleId") int roleId);
} 
