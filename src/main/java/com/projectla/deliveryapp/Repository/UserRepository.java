package com.projectla.deliveryapp.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.projectla.deliveryapp.Entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhoneNumber(String phoneNumber);
}
