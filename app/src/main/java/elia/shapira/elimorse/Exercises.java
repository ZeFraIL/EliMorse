package elia.shapira.elimorse;

public class Exercises {
    private String exKind;
    private String exMistake;
    private String exDate;
    private String exUser;

    public Exercises(String exKind, String exMistake, String exDate, String exUser) {
        this.exKind = exKind;
        this.exMistake = exMistake;
        this.exDate = exDate;
        this.exUser = exUser;
    }

    public String getExKind() {
        return exKind;
    }

    public void setExKind(String exKind) {
        this.exKind = exKind;
    }

    public String getExMistake() {
        return exMistake;
    }

    public void setExMistake(String exMistake) {
        this.exMistake = exMistake;
    }

    public String getExDate() {
        return exDate;
    }

    public void setExDate(String exDate) {
        this.exDate = exDate;
    }

    public String getExUser() {
        return exUser;
    }

    public void setExUser(String exUser) {
        this.exUser = exUser;
    }
}
