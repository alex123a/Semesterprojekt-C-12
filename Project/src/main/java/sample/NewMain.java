package sample;

public class NewMain {
    public static void main(String[] args) {
        // https://stackoverflow.com/questions/52653836/maven-shade-javafx-runtime-components-are-missing
        // Calls the main method in the Main class.
        // The reason is that we get an error when we try to run the program from the Main class
        Main.main(args);
    }
}
