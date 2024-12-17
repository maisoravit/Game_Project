package logic;

import utils.CallBackFunc;

public class Timer {
    private long duration;
    private long start;
    private long diff;
    private Thread thread;
    private boolean isRunning;

    public Timer() {
        duration = 0;
    }

    public void start(CallBackFunc update, CallBackFunc end) {
        isRunning = true;
        start = System.currentTimeMillis();
        thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                    if (!isRunning) {
                        diff = duration;
                        update.call();
                        thread.interrupt();
                        break;
                    }
                    long current = System.currentTimeMillis();
                    diff = current - start;
                    if (diff >= duration) {
                        diff = duration;
                        update.call();
                        end.call();
                        break;
                    }
                    update.call();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

//    0 - 100
    public double getPercentage() {
        return (double) (diff * 100) / duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void stop() {
        isRunning = false;
    }
}
