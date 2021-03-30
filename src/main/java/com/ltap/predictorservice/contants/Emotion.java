package com.ltap.predictorservice.contants;

public enum Emotion {
    Happy("Happy"),
    Angry("Angry"),
    Sad("Sad "),
    Stressed("Stressed");

    private String emotion;

    Emotion(String s) {

        this.emotion = s;
    }

    public static Emotion valueOfDuration(String label) {
        for (Emotion e : values()) {
            if (e.toString().equals(label)) {
                return e;
            }
        }
        return null;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
