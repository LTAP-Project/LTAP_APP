package com.ltap.predictorservice.contants;

public enum Duration {
    ONE_TO_30MIN("0-30 mins"),
    LESS_THAN_AN_HOUR("Less than an hour"),
    ONE_TO_TWO_HOURS("1-2 hours"),
    MORE_THAN_2HOURS("More than 2 hours");

    private String value;

    Duration(String s) {
        this.value = s;
    }

    public static Duration valueOfDuration(String label) {
        for (Duration e : values()) {
            if (e.toString().equals(label)) {
                return e;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
