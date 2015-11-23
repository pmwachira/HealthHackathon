package mushirih.hackathon2015;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "BOOT SERVICE START OVER/TEST SOUND", Snackbar.LENGTH_LONG)
                       /* .setAction("START CUSTOM SERVICE", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //SOUND WORKS



                            }
                        })*/.setAction("SERVICE", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Test SERVICE
                        startService(new Intent(MainActivity.this, MyService.class));
                    }
                }).show();
            }
        });
/*
        IntentFilter intentfliter  = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
       VolumeButtonLongPress mReceiver  = new VolumeButtonLongPress();

        getApplicationContext().registerReceiver(mReceiver, intentfliter);
        AudioManager manager= (AudioManager) getSystemService(AUDIO_SERVICE);
       manager.registerMediaButtonEventReceiver(getCallingActivity());
*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
