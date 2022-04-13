package com.example.shop.controller;

import com.example.shop.model.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class Default {
  
  @GetMapping()
  @ResponseBody()
  public ResponseEntity<?> hello() {
    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    return CustomResponse.generate("Welcome to the Shop API!", timeStamp, HttpStatus.OK);
  }
}
