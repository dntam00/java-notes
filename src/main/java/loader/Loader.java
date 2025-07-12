package loader;

import java.sql.DriverManager;
import java.util.ArrayList;

public class Loader {

    public static void main(String[] args) {
        System.out.println("Classloader of ArrayList:"
                                   + ArrayList.class.getClassLoader());
        System.out.println("Classloader of DriverManager:"
                                   + DriverManager.class.getClassLoader());
        System.out.println("Classloader of Loader:"
                                   + Loader.class.getClassLoader());
    }
}
