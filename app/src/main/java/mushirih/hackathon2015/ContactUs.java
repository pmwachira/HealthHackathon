package mushirih.hackathon2015;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeIntents;

/**
 * Created by p-tah on 21/10/2015.
 */
public class ContactUs extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        Button you= (Button) findViewById(R.id.btnY);
        you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Opening our YouTube Channel", Toast.LENGTH_SHORT).show();

                Intent intent = YouTubeIntents.createSearchIntent(getBaseContext(), "first aid");
                try {
                    startActivity(intent);
                }catch (Exception e2){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=first+aid")));
                }
            }
        });
        Button sms= (Button) findViewById(R.id.sms);
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:0712613052");
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "Enter Text");
                startActivity(it);
            }
        });
        Button email= (Button) findViewById(R.id.email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "pmwachira@gmail.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,"subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "body");


                startActivity(Intent.createChooser(emailIntent, "Chooser App To send with"));
            }
        });
        Button button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:0712613052")));
            }
        });
    }
}
