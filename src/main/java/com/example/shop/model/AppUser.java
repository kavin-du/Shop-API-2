package com.example.shop.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;

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

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "role_id")
  @Schema(hidden = true)
  private Collection<Role> roles = new ArrayList<>();

  // no args constructor
  public AppUser() {
  }

  // all args constructor
  public AppUser(long id, String email, String password, Collection<Role> roles) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.roles = roles;
  }

  public AppUser(String email, String password, Collection<Role> roles) {
    this.email = email;
    this.password = password;
    this.roles = roles;
  }

  // getters and setters
   public long getId() {
     return id;
   }

  public Collection<Role> getRoles() {
    return roles;
  }

  public void setRoles(Collection<Role> roles) {
    this.roles = roles;
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
