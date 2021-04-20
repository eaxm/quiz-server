package util;

public class SessionExpirationManager implements Runnable {


    private long sessionExpirationTime;
    private boolean run = true;

    public SessionExpirationManager(long sessionExpirationTime) {
        this.sessionExpirationTime = sessionExpirationTime;
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }

    @Override
    public void run() {
        while (run){
            try {
                Thread.sleep(1000*10);
                System.out.println("check db");

                // TODO
                // check date + session expiration time for each entry

                // delete session from db if calculated time is less than current time

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
