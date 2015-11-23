package mushirih.hackathon2015;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;



/**
 * Created by p-tah on 20/11/2015.
 */
public class VolumeButtonLongPress extends BroadcastReceiver {
    public boolean isRecieved;
    public static Timer timer = null;
    public static int i=0;
    final int MAX_ITERATION = 7;
    public static boolean isAppWorkingFine = true;
    final int MAX_COUNT = 5;
    int COUNT = 0;
    int FLAG_HAS_SCREAMED = 0;//uncomment
    Handler nullifier=new Handler();
    String notice="";
    PowerManager pm;
    Location location;
    String ill="";
    MediaPlayer alertSound;


    @Override
    public void onReceive(final Context context, final Intent intent) {
//         alertSound = MediaPlayer.create(context, R.raw.whistle);
//        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
//        final int volume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
//        audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMaxVolume(AudioManager.STREAM_RING), 0);
//        audioManager.setSpeakerphoneOn(true);
//        audioManager.setMode(AudioManager.MODE_IN_CALL);

//        alertSound.start();
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Window window = (Window) context.getSystemService(Context.WINDOW_SERVICE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
            window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag");
            wl.acquire();
        }
        isRecieved = true;
        if (timer == null && isAppWorkingFine) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (isRecieved) {


                        Log.d("VolumeButtonLongPress", "WATCHER NOW");

                        i++;
                    } else {
                        cancel();
                        timer.cancel();
                        timer.purge();
                        timer = null;
                        i = 0;
                        Log.d("VolumeButtonLongPress", "RELEASE WATCHER");
                        COUNT++;
                        if (!(COUNT < MAX_COUNT)) {
                            if (FLAG_HAS_SCREAMED == 0) {
                                screamForHelp(context);
                           }
                        }
                    }
                    if (i >= MAX_ITERATION) {
                        cancel();
                        timer.cancel();
                        timer.purge();
                        timer = null;
                        i = 0;
                        Log.d("VolumeButtonLongPress", "LONG WATCHER");

                        //
                        if (FLAG_HAS_SCREAMED == 0) {
                            screamForHelp(context);
                        }
                        Intent danger=new Intent(context,Danger.class);
                        danger.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(danger);
                    }
                    isRecieved = false;
                }
            }, 0, 200);
        }


        // }

        // }
    }

    private void screamForHelp(final Context context) {
        //SOUND PLAY HERE
        //contactNextOfKin(context);
        //--------------
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        setNotice(context);

                        AlertDialog alertDialog = new AlertDialog.Builder(context)
                                .setTitle("FIRST AID procedure for "+ill+". HELP me")
                                .setMessage(notice)
                                .create();

                        alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
                        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                        alertDialog.show();
                    }
                }
        );
       FLAG_HAS_SCREAMED = 1;//uncomment to record sound times



       nullifier.postDelayed(new Runnable() {
         @Override
         public void run() {
          FLAG_HAS_SCREAMED=0;
          }
          },10000);
    }

    private void setNotice(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
       ill=sharedPrefs.getString("ATTACK", "NULL");
        switch (ill){
            case "Asthma Attacks":
                notice=" Reassure them and ask them to breathe slowly and deeply\n" +

                        "help them use their reliever inhaler straight away\n" +

                        "Sit them down in a comfortable position.\n" +

                        "• If it doesn’t get better within a few minutes, it may be a severe attack. Get them to take one or two puffs of their inhaler every two minutes, until they’ve had 10 puffs.\n" +
                        "\n" +
                        "Call for an ambulance.\n" +
                        "\n" +
                        "Help them to keep using their inhaler if they need to.\n" +

                        "check their breathing and prepare to treat someone who’s become unconscious.";
                break;
            case "Epilepsy":
                notice="\n" +
                        "Protect the person from injury - (remove harmful objects from nearby)\n" +
                        " Cushion their head\n" +
                        " Look for an epilepsy identity card or identity jewellery\n" +
                        " Aid breathing by gently placing them in the recovery position once the seizure has finished (see pictures)\n" +
                        " Stay with the person until recovery is complete\n" +
                        " Be calmly reassuring\n";
                break;
            case "Fainting":
                notice="Make the Person Safe\n" +

                        "    Lay the person flat on his or her back.\n" +

                        "    Elevate the person's legs to restore blood flow to the brain.\n" +

                        "    Loosen tight clothing.\n" +

                        " Try to Revive the Person\n" +

                        "    Shake the person vigorously, tap briskly, or yell.\n" +

                        "    If the person doesn't respond, call 911 immediately.\n" +

                        " Do Home Care for Simple Fainting\n" +

                        "    If the person is alert, give fruit juice, especially if the person has not eaten in more than 6 hours or has diabetes.\n" +

                        "    Stay with the person until he or she is fully recovered.\n" +

                        " Call a Health Care Provider";
                break;
            case "Panic Attacks":
                notice=". Remove any triggers of the panic attack (or remove the patient from the trigger!)\n" +
                        "\n" +
                        "2. Provide lots of reassurance and remain calm yourself\n" +
                        "\n" +
                        "3. Focus on controlling the patient’s breathing – encourage them to breath in slowly through their nose, hold their breath, then breath out through their mouth.";
            break;
            default:
                notice="Help the person to get to a medical center immediately";
        }
    }



}
