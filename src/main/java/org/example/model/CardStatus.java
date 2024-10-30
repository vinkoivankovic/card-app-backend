package org.example.model;

public enum CardStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String status;

    CardStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}