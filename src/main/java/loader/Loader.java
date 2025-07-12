package loader;

import java.sql.DriverManager;
import java.util.*;
import java.util.stream.Collectors;

public class Loader {

    public static void main(String[] args) {
//        printLoaderClass();
//        categoryModule();
    }

    public static void categoryModule() {
        ModuleLayer.boot().modules().stream()
                   .collect(Collectors.groupingBy(
                           m -> Optional.ofNullable(m.getClassLoader())
                                        .map(ClassLoader::getName).orElse("boot"),
                           Collectors.mapping(Module::getName,
                                              Collectors.toCollection(TreeSet::new))))
                   .entrySet().stream()
                   .sorted(Comparator.comparingInt(
                           e -> List.of("boot", "platform", "app").indexOf(e.getKey())))
                   .map(e -> e.getKey() + "\n\t" + String.join("\n\t", e.getValue()))
                   .forEach(System.out::println);
    }

    private static void printLoaderClass() {
        System.out.println("Classloader of ArrayList:"
                                   + ArrayList.class.getClassLoader());
        System.out.println("Classloader of DriverManager:"
                                   + DriverManager.class.getClassLoader());
        System.out.println("Classloader of Loader:"
                                   + Loader.class.getClassLoader());
    }


}
