package elia.shapira.elimorse;

import java.io.Serializable;

public class MorseCode implements Serializable {
    private String mCode;
    private String mLetter;

    public MorseCode(String mCode, String mLetter) {
        this.mCode = mCode;
        this.mLetter = mLetter;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmLetter() {
        return mLetter;
    }

    public void setmLetter(String mLetter) {
        this.mLetter = mLetter;
    }

    @Override
    public String toString() {
        return  mCode + "   " + mLetter;
    }
}
