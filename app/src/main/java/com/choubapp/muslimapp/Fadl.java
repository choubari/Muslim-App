package com.choubapp.muslimapp;

public class Fadl {
    private String mThought;
    private String mSaidby;

    public Fadl(String text1, String text2) {
        mThought = text1;
        mSaidby = text2;
    }

    public String getThought() {
        return mThought;
    }
    public String getSaidby() {
        return mSaidby;
    }
}
