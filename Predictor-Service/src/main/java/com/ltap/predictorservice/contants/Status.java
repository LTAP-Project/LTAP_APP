package com.ltap.predictorservice.contants;

public enum Status {
    GOOD("Good"),
    HECTIC("Hectic"),
    NORMAL("Normal");

    private String status;

    Status(String s) {
        this.status = s;
    }

    public static Status valueOfDuration(String label) {
        for (Status e : values()) {
            if (e.toString().equals(label)) {
                return e;
            }
        }
        return null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
