package example;

public class Example {

    public Example() {
        this(Example.exception());
    }

    public Example(int i) {
    }

    public static int exception() {
        throw new IllegalArgumentException("Error");
    }

    public static void main(String[] args) {
        new Example();
    }
}
