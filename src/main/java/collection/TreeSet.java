package collection;

public class TreeSet {

    public static void main(String[] args) {
//        nonNullableTreeSet();
        nullableTreeSet();
    }

    private static void nonNullableTreeSet() {
        java.util.TreeSet<Long> set = new java.util.TreeSet<>();
        set.add(1L);
        set.add(2L);
        set.add(3L);
        set.add(4L);
        set.add(5L);

        System.out.printf("Set size: %d%n", set.size());

        // Attempting to add a duplicate
        boolean added = set.add(3L);
        System.out.printf("Was 3 added again? %b%n", added);

        // Displaying the elements in the TreeSet
        System.out.println("Elements in the TreeSet: " + set);
    }

    private static void nullableTreeSet() {
        java.util.TreeSet<Long> set = new java.util.TreeSet<>(
                java.util.Comparator.nullsLast(Long::compareTo)
        );
        set.add(null);
        set.add(2L);
        set.add(1L);
        set.add(3L);
        System.out.println(set); // Output: [1, 2, 3, null]
    }

}
