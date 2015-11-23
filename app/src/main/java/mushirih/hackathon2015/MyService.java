package mushirih.hackathon2015;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by p-tah on 20/11/2015.
 */
public class MyService extends Service {
    VolumeButtonLongPress mReceiver;
   // protected PowerManager.WakeLock mWakeLock;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        //final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
       // this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        ///this.mWakeLock.acquire();



            IntentFilter intentfliter = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
            //intentfliter.addAction(Intent.ACTION_SCREEN_OFF);
            mReceiver = new VolumeButtonLongPress();
           //--


        //--
            getApplicationContext().registerReceiver(mReceiver, intentfliter);
            AudioManager manager = (AudioManager) getSystemService(AUDIO_SERVICE);
            manager.registerMediaButtonEventReceiver(new ComponentName(getPackageName(), VolumeButtonLongPress.class.getName()));


    }

    @Override
    public void onDestroy() {
  // this.mWakeLock.release();
    unregisterReceiver(mReceiver);
    }
}
