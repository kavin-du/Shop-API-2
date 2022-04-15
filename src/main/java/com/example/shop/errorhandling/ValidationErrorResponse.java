package com.example.shop.errorhandling;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {
    // list of violated errors
    private List<Violation> violations = new ArrayList<>();

    public List<Violation> getViolations() {
        return violations;
    }

    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }
}
