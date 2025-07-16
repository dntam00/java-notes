package reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvocation {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        invokeMethod();
    }

    public static void invokeMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = String.class.getMethod("length");

        // Invoke the method on an instance
        String str = "Hello";
        int length = (int) method.invoke(str); // Returns 5
        System.out.println(length);
    }
}
