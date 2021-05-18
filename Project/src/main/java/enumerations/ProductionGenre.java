package enumerations;

public enum ProductionGenre {
    ACTION("Action", 1), BØRNEFILM("Børnefilm", 2), DOKUMENTAR("Dokumentar", 3),
    DRAMA("Drama", 4), FAMILIEFILM("Familiefilm", 5), GYSER("Gyser", 6),
    KOMEDIE("komedie", 7), ROMANTIK("Romantik", 8), THRILLER("Thriller", 9);

    private String genreWord;
    private int id;

    ProductionGenre(String genreWord, int id){
        this.genreWord = genreWord;
        this.id = id;
    }

    public String getGenreWord() {
        return genreWord;
    }

    public int getId() {
        return id;
    }

    public static ProductionGenre getFromID(int id) {
        for (ProductionGenre n: values()){
            if (n.id == id) {
                return n;
            }
        }
        return null;
    }
}
