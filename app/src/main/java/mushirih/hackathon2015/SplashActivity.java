package mushirih.hackathon2015;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;


public class SplashActivity extends Activity {



    ProgressBar progressBar;


    Handler handler=new Handler();
    Runnable r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //
        SharedPreferences settings=getSharedPreferences("prefs",0);
        boolean firstrun=settings.getBoolean("firstRun",false);
        if(firstrun==false){
            //new user
            SharedPreferences.Editor editor=settings.edit();
            editor.putBoolean("firstRun",true);
            editor.commit();
           // Intent a=new Intent(SplashActivity.this,Register.class);
            //startActivity(i);
            //finish();
            r=new Runnable() {
                @Override
                public void run() {
                    Intent startActivity = new Intent(SplashActivity.this, Register.class);
                    startActivity(startActivity);
                    finish();
                }
            };
        }
        else
        {
            //subsequent user
            //Intent a=new Intent(SplashActivity.this,Register.class);
            //startActivity(a);
            //finish();
           r=new Runnable() {
                @Override
                public void run() {
                    Intent startActivity = new Intent(SplashActivity.this,Home.class);
                    startActivity(startActivity);
                    finish();
                }
            };
        }


        //
        handler.postDelayed(r,2500);

    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(r);
        super.onPause();
    }

    @Override

    protected void onDestroy() {

        handler.removeCallbacks(r);
        super.onDestroy();
    }


}
