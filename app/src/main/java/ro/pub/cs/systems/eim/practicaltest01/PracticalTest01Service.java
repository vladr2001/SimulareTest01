package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PracticalTest01Service extends Service {
    public ProcessingThread processingThread = null;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int a = intent.getIntExtra("first", -1);
        int b = intent.getIntExtra("second", -1);
        processingThread = new ProcessingThread(this, a, b);
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw null;
    }
}