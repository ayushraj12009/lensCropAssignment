package com.LensCorp.LensCorp.Repository;

import com.LensCorp.LensCorp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long>{

    User findByEmail(String email);

}
