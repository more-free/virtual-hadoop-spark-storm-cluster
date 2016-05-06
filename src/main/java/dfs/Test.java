package dfs;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by morefree on 3/16/16.
 */
public class Test {


    void test() throws ExecutionException, InterruptedException {
        Future<Optional<Void>> task = new FutureTask<Optional<Void>>(
                () -> {
                    throw new RuntimeException("some exception");
                }
        );
        task.get();
    }


    public static void main(String [] args) {
        Test t = new Test();

        try {
            t.test();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {

        }

        System.out.println("done main");
    }
}
