package mushirih.hackathon2015;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by p-tah on 21/11/2015.
 */
public class Danger extends AppCompatActivity {

    AudioManager audioManager;
    String notice="";

    Location location;

    MediaPlayer alertSound;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            //Do whatever you want to do on Volume Up
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            //Do whatever you want to do on Volume Down
            return true;
        }
        return false;
    }
    /*
     @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean result;
        switch( event.getKeyCode() ) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                Toast.makeText(getBaseContext(),"UP",Toast.LENGTH_LONG).show();
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Toast.makeText(getBaseContext(),"UP",Toast.LENGTH_LONG).show();
                break;
                result = true;
                break;

            default:
                result= super.dispatchKeyEvent(event);
                break;
        }

        return result;
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger);

        alertSound=MediaPlayer.create(this,R.raw.whistle);


        audioManager= (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
        final int volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        audioManager.setSpeakerphoneOn(true);//NA HII NI BACK UP JUST INCASE UMEVAA HEADPHONES


        alertSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
            }
        });
        alertSound.start();
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this);
        final String phonenumber=settings.getString("NUMBER", "Null");
       //Toast.makeText(getBaseContext(),"NUMBER"+phonenumber,Toast.LENGTH_SHORT).show();


        Button call= (Button) findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +phonenumber)));
            }
        });

        Button text= (Button) findViewById(R.id.text);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(),"Pressed",Toast.LENGTH_SHORT).show();
                SmsManager smsManager = SmsManager.getDefault();
                String sendTo = phonenumber;
                String message ="Sorry Dear,I pressed the panic button by mistake.Sorry and thanks for the concern";
                smsManager.sendTextMessage(sendTo, null, message, null, null);
            }
        });

        //SOUND PLAY HERE
        Context context=this;
        contactNextOfKin(context);
    }
    private void contactNextOfKin(Context context) {
        Location location=null;

        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        Criteria critera = new Criteria();
        String provider = locationManager.getBestProvider(critera, true);
        double latitude=0.0;
        double longitude=0.0;
        if (provider != null) {
            location = locationManager.getLastKnownLocation(provider);
            // while (location == null) {
             //  context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//             }
            } else {
               Toast t = Toast.makeText(context, "Turn on Location Services to get your location", Toast.LENGTH_LONG);
               t.show();
            }

            if (location != null) {
                 latitude = location.getLatitude();
                 longitude = location.getLongitude();
            }




            String uri = "http://maps.google.com/maps?saddr=" + latitude + "," + longitude;

            String intro="I am having an attack,Please call back to confirm.If I dont answer, here is my current location.";
            SharedPreferences settings = PreferenceManager
                    .getDefaultSharedPreferences(this);
            final String phonenumber=settings.getString("NUMBER", "Null");

            SmsManager smsManager = SmsManager.getDefault();
            String sendTo = phonenumber;
            String message = intro + uri;
            smsManager.sendTextMessage(sendTo, null, message, null, null);

            // alertSound.release();



        }

    @Override
    protected void onPause() {
        super.onPause();
        alertSound.release();
    }
}

