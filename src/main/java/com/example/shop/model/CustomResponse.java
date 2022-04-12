package com.example.shop.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class CustomResponse {
    public static ResponseEntity<HashMap<String, Object>> generate(String message, Object data, HttpStatus code) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("data", data);
        return new ResponseEntity<>(map, code);
    }

    public static ResponseEntity<HashMap<String, Object>> generate(String message, HttpStatus code) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", message);
        return new ResponseEntity<>(map, code);
    }

}
