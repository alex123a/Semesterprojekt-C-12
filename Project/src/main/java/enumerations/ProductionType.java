package enumerations;

public enum ProductionType {
    SERIER("Serier", 1), FILM("Film", 2), REALITY("Reality", 3),
    UNDERHOLDNING("Underholdning", 4), COMEDY("Comedy", 5), DOKUMENTAR("Dokumentar", 6),
    REJSER_OG_EVENTYR("Rejser og eventyr", 7), LIVSSTIL("livsstil", 8), MAGASINER("Magasiner", 9);

    private String typeWord;
    private int id;

    ProductionType(String genreWord, int id){
        this.typeWord = genreWord;
        this.id = id;
    }

    public String getTypeWord() {
        return typeWord;
    }

    public int getId() {
        return id;
    }

    public static ProductionType getFromID(int id) {
        for (ProductionType n: values()){
            if (n.id == id) {
                return n;
            }
        }
        return null;
    }
}
