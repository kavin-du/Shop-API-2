package com.example.shop.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class AppUser {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(unique = true)
  @Email
  @NotEmpty
  private String email;
  @Size(min = 4)
  @NotEmpty
  private String password;

  public AppUser() {
  }

  public AppUser(long id, String email, String password) {
    this.id = id;
    this.email = email;
    this.password = password;
  }

  // public long getId() {
  //   return id;
  // }

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
