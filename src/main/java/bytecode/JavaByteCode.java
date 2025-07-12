package bytecode;

public class JavaByteCode {

    // javac bytecode/JavaByteCode.java
    // javap -c -v bytecode/JavaByteCode.class
    public static void main(String[] args) {
        JavaByteCode javaByteCode = new JavaByteCode();
        int result = javaByteCode.add(5, 6);
        System.out.println("Sum: " + result);
        javaByteCode.testConstant();
    }

    public int add(int a, int b) {
        return a + b;
    }

    void testConstant() {
        String var = "Hello";
    }
}
