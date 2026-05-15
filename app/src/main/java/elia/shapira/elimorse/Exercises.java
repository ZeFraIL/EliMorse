package elia.shapira.elimorse;

/**
 * Represents a completed exercise record in the EliMorse application.
 * Stores information about the exercise type, performance, and user.
 */
public class Exercises {
    /** The kind or category of the exercise (e.g., "Numbers", "Letters"). */
    private String exKind;
    /** The number of mistakes made during the exercise. */
    private String exMistake;
    /** The date when the exercise was completed. */
    private String exDate;
    /** The username of the person who completed the exercise. */
    private String exUser;

    /**
     * Constructs a new Exercises record.
     *
     * @param exKind    The type of exercise.
     * @param exMistake The number of mistakes.
     * @param exDate    The completion date.
     * @param exUser    The user who performed the exercise.
     */
    public Exercises(String exKind, String exMistake, String exDate, String exUser) {
        this.exKind = exKind;
        this.exMistake = exMistake;
        this.exDate = exDate;
        this.exUser = exUser;
    }

    /**
     * Gets the exercise kind.
     * @return The kind of exercise.
     */
    public String getExKind() {
        return exKind;
    }

    /**
     * Sets the exercise kind.
     * @param exKind The new exercise kind.
     */
    public void setExKind(String exKind) {
        this.exKind = exKind;
    }

    /**
     * Gets the number of mistakes.
     * @return The number of mistakes as a string.
     */
    public String getExMistake() {
        return exMistake;
    }

    /**
     * Sets the number of mistakes.
     * @param exMistake The new number of mistakes.
     */
    public void setExMistake(String exMistake) {
        this.exMistake = exMistake;
    }

    /**
     * Gets the exercise completion date.
     * @return The date string.
     */
    public String getExDate() {
        return exDate;
    }

    /**
     * Sets the exercise completion date.
     * @param exDate The new date string.
     */
    public void setExDate(String exDate) {
        this.exDate = exDate;
    }

    /**
     * Gets the username associated with the exercise.
     * @return The username.
     */
    public String getExUser() {
        return exUser;
    }

    /**
     * Sets the username associated with the exercise.
     * @param exUser The new username.
     */
    public void setExUser(String exUser) {
        this.exUser = exUser;
    }
}
