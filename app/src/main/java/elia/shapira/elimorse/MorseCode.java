package elia.shapira.elimorse;

import java.io.Serializable;

/**
 * Represents a mapping between a Morse code sequence and its corresponding alphanumeric character.
 * This class is used to store and retrieve Morse code data for learning and translation.
 */
public class MorseCode implements Serializable {
    /** The Morse code sequence (e.g., ".-"). */
    private String mCode;
    /** The alphanumeric character representing the Morse code (e.g., "A"). */
    private String mLetter;

    /**
     * Constructs a new MorseCode object.
     *
     * @param mCode   The Morse code sequence.
     * @param mLetter The corresponding alphanumeric character.
     */
    public MorseCode(String mCode, String mLetter) {
        this.mCode = mCode;
        this.mLetter = mLetter;
    }

    /**
     * Gets the Morse code sequence.
     * @return The Morse code string.
     */
    public String getmCode() {
        return mCode;
    }

    /**
     * Sets the Morse code sequence.
     * @param mCode The new Morse code string.
     */
    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    /**
     * Gets the alphanumeric character.
     * @return The character string.
     */
    public String getmLetter() {
        return mLetter;
    }

    /**
     * Sets the alphanumeric character.
     * @param mLetter The new character string.
     */
    public void setmLetter(String mLetter) {
        this.mLetter = mLetter;
    }

    /**
     * Returns a string representation of the MorseCode object.
     * @return A string in the format "code   letter".
     */
    @Override
    public String toString() {
        return  mCode + "   " + mLetter;
    }
}
