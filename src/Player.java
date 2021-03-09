public enum Player {
    PLAYERX("X"),
    PLAYERO("O"),
    AI("O");

    private String abbreviation;

     Player(String abbreviation){
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation(){
        return this.abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
