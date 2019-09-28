import edu.NTUB.Client;
import edu.NTUB.Server;

public class Main {
    public static void main(String[] args) {
        System.out.println("Guest Number ~~~");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Server.main();
            }
        }).start();

        new Thread(() -> Client.main()).start();
    }
}