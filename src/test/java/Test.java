import java.util.Arrays;
import java.util.stream.Stream;

public class Test {

    void method(Object[] args) {
        String s = String.format("Kokoko %s", Arrays.toString(args));
        System.out.println(s);
    }

    public Test() {
        method(Stream.of("1", "2", null)
                .toArray());
    }

    public static void main(String[] args) {
        new Test();
    }
}
