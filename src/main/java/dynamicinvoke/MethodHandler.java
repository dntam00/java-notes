package dynamicinvoke;

@FunctionalInterface
public interface MethodHandler {

    void invoke(Object requestHandlerInstance);
}
