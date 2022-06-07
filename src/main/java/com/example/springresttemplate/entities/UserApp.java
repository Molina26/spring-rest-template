package com.example.springresttemplate.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "users",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "password")
  })
@Data
public class UserApp{
  

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username", length = 150, nullable = false)
  private String username;

  @Column(name = "password", columnDefinition = "TEXT", nullable = false)
  private String password;

  @Column(name = "is_available", length = 150, nullable = false)
  private Boolean isAvailable;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

}
