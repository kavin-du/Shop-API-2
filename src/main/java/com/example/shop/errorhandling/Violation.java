package com.example.shop.errorhandling;

public class Violation {
  private final String fieldName; // name of the violated field
  private final String message; // binded error message

  public Violation(String fieldName, String message) {
    this.fieldName = fieldName;
    this.message = message;
  }

  public String getFieldName() {
    return fieldName;
  }

  public String getMessage() {
    return message;
  }
}
