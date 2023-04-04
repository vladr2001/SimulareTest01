package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;
import java.util.Random;

public class ProcessingThread extends Thread {

    private Context context = null;
    private Integer first;
    private Integer second;

    private Random random = new Random();

    private Boolean isRunning = true;

    public ProcessingThread(android.content.Context context, Integer a, Integer b) {
        this.context = context;
        this.first = a;
        this.second = b;
    }

    @Override
    public void run() {
        Log.d("THREAD DEBUG", "Thread has started!");
        while(isRunning) {
            sendMessage();
            sleep();
        }
        Log.d("THREAD DEBUG", "Thread has stopped!");

    }

    private void sendMessage() {
        Intent intent = new Intent();
        float arithmeticMean = (first + second) / 2;
        float geometricMean = (float) Math.sqrt(first * second);
        intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.length)]);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                new Date(System.currentTimeMillis()) + " " + arithmeticMean + " " + geometricMean);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopThread() {
        this.isRunning = false;
    }
}
