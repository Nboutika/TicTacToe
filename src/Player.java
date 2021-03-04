public enum Player {
    PLAYERX("X"),
    PLAYERO("O");

    private String abbreviation;

     Player(String abbreviation){
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation(){
        return this.abbreviation;
    }

}
