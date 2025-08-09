package heap;

import org.openjdk.jol.info.GraphLayout;

public class ObjectOverhead {

    public static void main(String[] args) {

        long l = GraphLayout.parseInstance(new ObjectOverhead()).totalSize();
        System.out.println("ObjectOverhead size: " + l + " bytes");
    }
}
