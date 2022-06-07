package com.example.springresttemplate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springresttemplate.entities.UserApp;

@Repository
public interface UserAppRepository extends JpaRepository<UserApp, Long>{
  public UserApp findByUsername(String username);
}
