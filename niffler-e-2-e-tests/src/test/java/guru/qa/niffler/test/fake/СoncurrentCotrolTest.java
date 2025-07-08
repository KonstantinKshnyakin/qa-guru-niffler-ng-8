package guru.qa.niffler.test.rest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Execution(ExecutionMode.SAME_THREAD)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class СoncurrentCotrolTest {

    @ResourceLock("C")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @Order(Integer.MIN_VALUE)
    @Nested
    class C {

        @Order(Integer.MIN_VALUE)
        @RepeatedTest(5)
        void parallelTest1() {
            init("C1");
        }

        @RepeatedTest(5)
        void parallelTest2() {
            init("C2");
        }
    }

    @ResourceLock(value = "C", mode = ResourceAccessMode.READ)
    @ResourceLock(value = "A", mode = ResourceAccessMode.READ)
    @Nested
    @Execution(ExecutionMode.CONCURRENT)
    class B {

        @RepeatedTest(5)
        void parallelTest1() {
            init("B1");
        }

        @RepeatedTest(5)
        void parallelTest2() {
            init("B2");
        }
    }

    @ResourceLock("A")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @Order(Integer.MAX_VALUE)
    @Nested
    class A {

        @Order(Integer.MIN_VALUE)
        @RepeatedTest(5)
        void parallelTest1() {
            init("A1");
        }

        @RepeatedTest(5)
        void parallelTest2() {
            init("A2");
        }
    }

    private static final Map<String, Long> map = new ConcurrentHashMap<>();

    @SneakyThrows
    private static void init(String integer) {
        long time = System.currentTimeMillis();
        String string = "%s - %s - %s".formatted(integer, time, Thread.currentThread().getName());
        map.put(string, time);
        Thread.sleep(500);
    }

    @AfterAll
    public static void afterAll() {
        map.entrySet().stream()
            .sorted(Comparator.comparingLong(Map.Entry::getValue))
            .map(Map.Entry::getKey)
            .forEach(System.out::println);
    }
}
