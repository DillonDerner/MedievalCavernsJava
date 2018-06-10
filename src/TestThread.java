import java.util.Random;

public class TestThread implements Runnable {
    String name;
    int time;
    Random r = new Random();

    TestThread(String n){
        name = n;
        time = r.nextInt(2000);
        //run();
    }
    public void run() {
        try {
            System.out.println(name + " is sleeping for " + time);
            Thread.sleep(time);
            System.out.println(name + " woke up!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}