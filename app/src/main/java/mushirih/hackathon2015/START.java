package mushirih.hackathon2015;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by p-tah on 20/11/2015.
 */
public class START extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("START", "******************STARTED************************************");
        Toast.makeText(context,"Starting Service",Toast.LENGTH_LONG).show();
        Intent startServiceIntent = new Intent(context,MyService.class);
        context.startService(startServiceIntent);


    }
}
