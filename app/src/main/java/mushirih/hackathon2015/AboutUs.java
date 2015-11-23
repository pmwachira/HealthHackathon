package mushirih.hackathon2015;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by p-tah on 21/10/2015.
 */
public class AboutUs extends ActionBarActivity {
    TextView tvTerms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        tvTerms= (TextView) findViewById(R.id.underlinethis);
        tvTerms.setPaintFlags(tvTerms.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        tvTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://mushirih.branded.me")));
            }
        });
    }
}
