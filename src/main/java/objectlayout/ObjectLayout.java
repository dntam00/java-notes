package objectlayout;


import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.util.logging.Logger;

public class ObjectLayout {

    private static final Logger logger = Logger.getLogger(ObjectLayout.class.getName());

    public static void main(String[] args) {

        ObjectLayout o = new ObjectLayout();
        o.hello();
        logger.info(VM.current().details());
        logger.info(ClassLayout.parseInstance(o).toPrintable());
    }

    private synchronized void hello() {
        logger.info("Hello, World");
        logger.info(ClassLayout.parseInstance(this).toPrintable());
    }
}
