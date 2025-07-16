package dynamicinvoke;

import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.stream.Stream;

public class DynamicInvoke {

    public static void main(String[] args) throws Throwable {
        buildAndInvokeLambda();
    }

    private static void buildAndInvokeLambda() throws Throwable {
        Method samMethod = FUNCTIONAL_INTERFACE_METHOD_MAP.get(MethodHandler.class);

        Method targetMethod = getMethod(TargetHandler.class, TargetHandler.HELLO_METHOD_NAME);

        Class<?> declaringClass = targetMethod.getDeclaringClass();

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle targetMethodHandle = lookup.in(declaringClass).unreflect(targetMethod);

        MethodType instantiatedMethodType =
                MethodType.methodType(
                        targetMethod.getReturnType(), declaringClass, targetMethod.getParameterTypes());

        MethodType samMethodType =
                MethodType.methodType(samMethod.getReturnType(), samMethod.getParameterTypes());

        CallSite callSite =
                LambdaMetafactory.metafactory(
                        lookup,
                        samMethod.getName(),
                        MethodType.methodType(MethodHandler.class),
                        samMethodType,
                        targetMethodHandle,
                        instantiatedMethodType);

        MethodHandler lambdaFunction = (MethodHandler) callSite.getTarget().invoke();

        lambdaFunction.invoke(new TargetHandler());
    }

    public static Method getMethod(Class<?> clazz, String methodName) throws NoSuchMethodException {
        return clazz.getMethod(methodName);
    }

    private static final FunctionalInterfaceMethodMap FUNCTIONAL_INTERFACE_METHOD_MAP =
            new FunctionalInterfaceMethodMap();

    private static class FunctionalInterfaceMethodMap extends ClassValue<Method> {
        /** Get the single abstract method (SAM) of the functional interface. */
        @Override
        protected Method computeValue(Class<?> functionalInterface) {
            Method[] methods =
                    Stream.of(functionalInterface)
                          .filter(Class::isInterface)
                          .flatMap(m -> Stream.of(m.getMethods()))
                          .filter(m -> Modifier.isAbstract(m.getModifiers()))
                          .toArray(Method[]::new);

            if (methods.length != 1) {
                throw new IllegalArgumentException(
                        "Class is not a functional interface: " + functionalInterface.getName());
            }

            return methods[0];
        }
    }
}
