package ca.team5032.robot.tasks;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Task {

    private int taskLength = 1000;
    private int rate = -1;
    private int delay = 0;
    private Runnable callback = () -> {};

    public void start() {}
    public void loop() {}
    public void finish() {}

    public Task delay(int milliseconds) {
        this.delay = milliseconds;
        return this;
    }

    public Task rate(int milliseconds) {
        this.rate = milliseconds;
        return this;
    }

    public Task runFor(int milliseconds) {
        this.taskLength = milliseconds;
        return this;
    }

    public Task then(Runnable callback) {
        this.callback = callback;
        return this;
    }

    public void run() {
        // Check if the rate is the default value (unmodified)
        if (this.rate == 0) {
            this.runAsGenericDelayedTask();
        } else {
            this.runAsGenericLoopingTask();
        }
    }

    private void runAsGenericLoopingTask() {
        // Initialize a new timer.
        Timer timer = new Timer();

        // Schedule starting task after initial delay.
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                start();
            }
        }, this.delay);
        // Initialize the looping task.
        TimerTask loopingTask = new TimerTask() {
            @Override
            public void run() {
                loop();
            }
        };
        // Schedule the looping task after the initial delay with the fixed rate.
        timer.schedule(loopingTask, this.delay, this.rate);
        // Schedule finishing task after the initial delay + the task length.
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
                callback.run();
                loopingTask.cancel();
            }
        }, this.delay + this.taskLength);
    }

    private void runAsGenericDelayedTask() {
        // Initialize a new timer.
        Timer timer = new Timer();
        // Schedule the starting task after the initial delay.
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                start();
            }
        }, this.delay);
        // Schedule the finishing task after the initial delay + the task length.
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
                callback.run();
            }
        }, this.delay + this.taskLength);
    }

}
