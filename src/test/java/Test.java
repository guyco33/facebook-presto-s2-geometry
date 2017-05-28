/**
 * Created by guycohen on 18/05/2017.
 */
import static java.lang.Math.toIntExact;

public class Test {

    public static void main(String[] args) {
        long foo = 10L;
        int bar = toIntExact(foo);
        System.out.println(bar);
    }}
