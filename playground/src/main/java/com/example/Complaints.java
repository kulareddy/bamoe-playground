package com.example;

import java.util.List;
import java.util.ArrayList;

public record Complaints(String id, String slaDuration, String status, List<Complaint> complaints) {

    public Complaints {
        // Null-safe initialization
        complaints = (complaints == null) ? new ArrayList<>() : new ArrayList<>(complaints);
    }

}
