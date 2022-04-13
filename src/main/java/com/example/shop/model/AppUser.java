package com.example.shop.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "users")
public class AppUser {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Schema(hidden = true) // swagger related
  private long id;

  @Column(unique = true)
  @Email
  @NotBlank
  private String email;

  @Size(min = 4, max = 50)
  @NotBlank
  private String password;

  public AppUser() {
  }

  public AppUser(long id, String email, String password) {
    this.id = id;
    this.email = email;
    this.password = password;
  }

   public long getId() {
     return id;
   }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
