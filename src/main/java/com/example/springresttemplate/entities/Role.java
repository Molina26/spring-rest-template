package com.example.springresttemplate.entities;

import javax.persistence.*;

import com.example.springresttemplate.enums.ERole;

import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(length = 30)
  private ERole name;
}
