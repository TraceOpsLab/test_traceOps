/*

package gcfv2;


import java.io.BufferedWriter;
import java.security.*;
import java.util.*;import

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

public class HelloHttpFunction implements HttpFunction {
    public void service(final HttpRequest request, final HttpResponse response) throws Exception {
        final BufferedWriter writer = response.getWriter();
        writer.write("Hello world!");
        ExceptionThrower.main();

    }
}



class ExceptionThrower {

    private static final int EXCEPTIONS_PER_SECOND = 50;
    private static final long NANOS_PER_SECOND = 1_000_000_000L;
    private static final Random random = new Random();

    private static final List<Class<? extends Exception>> exceptionTypes = new ArrayList<>();

    static {
        // Add 50 different exception types to the list
        exceptionTypes.add(NullPointerException.class);
        exceptionTypes.add(ArrayIndexOutOfBoundsException.class);
        exceptionTypes.add(IllegalArgumentException.class);
        exceptionTypes.add(IllegalStateException.class);
        exceptionTypes.add(ArithmeticException.class);
        exceptionTypes.add(ClassCastException.class);
        exceptionTypes.add(UnsupportedOperationException.class);
        exceptionTypes.add(NumberFormatException.class);
        exceptionTypes.add(IndexOutOfBoundsException.class);
//        exceptionTypes.add(AssertionError.class);
        exceptionTypes.add(NoSuchMethodException.class);
        exceptionTypes.add(NoSuchFieldException.class);
        exceptionTypes.add(InterruptedException.class);
        exceptionTypes.add(CloneNotSupportedException.class);
        exceptionTypes.add(IllegalAccessException.class);
        exceptionTypes.add(InstantiationException.class);
        exceptionTypes.add(ClassNotFoundException.class);
        exceptionTypes.add(NoSuchElementException.class);
        exceptionTypes.add(ConcurrentModificationException.class);
        exceptionTypes.add(UnsupportedOperationException.class);
        exceptionTypes.add(EmptyStackException.class);
        exceptionTypes.add(NoSuchProviderException.class);
        exceptionTypes.add(NoSuchAlgorithmException.class);
        exceptionTypes.add(InvalidKeyException.class);
        exceptionTypes.add(InvalidParameterException.class);
        exceptionTypes.add(KeyException.class);
        exceptionTypes.add(KeyManagementException.class);
        exceptionTypes.add(KeyStoreException.class);
    }

    public static void main() throws Exception{
        long startTime = System.nanoTime();
        long nextSecond = startTime + NANOS_PER_SECOND;



        while (true) {
            long currentTime = System.nanoTime();

            if (currentTime >= nextSecond) {
                nextSecond += NANOS_PER_SECOND;
                for (int i = 0; i < EXCEPTIONS_PER_SECOND; i++) {
                    try {
                        throwRandomException();
                    } catch (Exception e) {
                        // Print the exception type
                        System.out.println("Thrown exception: " + e.getClass().getSimpleName());
                        throw e;
                    }
                }
            }
        }
    }

    private static void throwRandomException() throws Exception {
        int index = random.nextInt(exceptionTypes.size());
        Class<? extends Exception> exceptionClass = exceptionTypes.get(index);
        throw exceptionClass.getDeclaredConstructor().newInstance();
    }
}*/
