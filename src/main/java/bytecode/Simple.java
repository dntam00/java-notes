package bytecode;

public class Simple {

    private int hello = 5;

    public Simple() {
    }

    public Simple(int hello) {
        this.hello = hello;
    }

    public static void main(String[] args) {
        Simple simple = new Simple();
        Simple simple1 = new Simple(3);
    }
}
