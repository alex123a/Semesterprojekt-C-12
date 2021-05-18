package enumerations;

public enum ProductionType {
    SERIER("Action", 1), FILM("Børnefilm", 2), REALITY("Dokumentar", 3),
    UNDERHOLDNING("Drama", 4), COMEDY("Familiefilm", 5), DOKUMENTAR("Gyser", 6),
    REJSER_OG_EVENTYR("komedie", 7), LIVSSTIL("Romantik", 8), MAGASINER("Thriller", 9);

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
