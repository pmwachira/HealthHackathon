package mushirih.hackathon2015;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by p-tah on 21/11/2015.
 */

public class Tips extends AppCompatActivity {

    private static final String TAG=MainActivity.class.getSimpleName();
    private FeedListAdapter feedListAdapter;
    private List<FeedItem> feedItemList;
  TinyDB  tinyDB;
    ArrayList<String> tempId,tempDate,tempName,tempDesc,tempMedia;
    ArrayList<HashMap<String, String>> tipList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
        toolbar.setLogo(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
       // setSupportActionBar(toolbar);
        tinyDB=new TinyDB(this);
           ListView listView = (ListView) findViewById(R.id.list);
           feedItemList=new ArrayList<FeedItem>();
        feedListAdapter=new FeedListAdapter(this,feedItemList);
        listView.setAdapter(feedListAdapter);


        Cache cache= AppController.getInstance().getRequestQueue().getCache();
        String URL_FEED = "http://192.185.77.246/~muchiri/cit/scripts/hack.php";

        Entry entry=cache.get(URL_FEED);//THROWING A NULL


        if(entry!=null){
            try {
                String data=new String(entry.data,"UTF-8");
                try {

                    parseJsonFeed(new JSONObject(data));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else {
            Log.d("NULL ENTRY", "WORKING TILL HERE****************************************************");

            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL_FEED, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    VolleyLog.d(TAG, "Response: " +jsonObject.toString());
                    if (jsonObject != null) {
                        parseJsonFeed(jsonObject);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    VolleyLog.d(TAG, "Error: " +volleyError.getMessage());
                }
            });

            AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        }

/*

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    private void checkExistingLists() {

        if (tinyDB.getListString("date").size() != 0 && tinyDB.getListString("desc").size() != 0 && tinyDB.getListString("id").size() != 0) {
            tempId = new ArrayList<>();
            tempDesc = new ArrayList<>();
            tempName = new ArrayList<>();
            tempMedia=new ArrayList<>();
            tempDate = new ArrayList<>();
            int TempNameCount = tinyDB.getListString("date").size();

            tipList = new ArrayList<HashMap<String, String>>();

            for (int i = 0; i < TempNameCount; i++) {//-----------------------------------------------------------------ADAPT TO LIST
                FeedItem item=new FeedItem();
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", tinyDB.getListString("id").get(i));
                map.put("desc", tinyDB.getListString("desc").get(i));
                map.put("date", tinyDB.getListString("date").get(i));
                map.put("name", tinyDB.getListString("name").get(i));

                item.setId(i);

                item.setName(tinyDB.getListString("name").get(i));


                item.setImage("");
                item.setStatus(tinyDB.getListString("desc").get(i));

                //item.setProfilePic(jsonObj.getString("profilePic"));
                item.setProfilePic("");
                item.setTimeStamp(tinyDB.getListString("date").get(i));

                String feedUrl="";
//                String feedUrl=jsonObj.isNull("url") ? null : jsonObj.getString("url");
                item.setUrl(feedUrl);

                feedItemList.add(item);

//
                tipList.add(map);
            }

        }else{
            Toast.makeText(this,"Please turn on your Internet",Toast.LENGTH_LONG).show();
        }
    }

    private void parseJsonFeed(JSONObject jsonObject) {
        try {
            JSONArray feedArray=jsonObject.getJSONArray("alerts");
            tempId = new ArrayList<>();
            tempDesc = new ArrayList<>();
            tempName = new ArrayList<>();
            tempMedia=new ArrayList<>();
            tempDate = new ArrayList<>();
            for(int i=0;i<feedArray.length();i++){
                JSONObject jsonObj= (JSONObject) feedArray.get(i);
                FeedItem item=new FeedItem();
                item.setId(jsonObj.getInt("id"));
                tempId.add("" + jsonObj.getInt("id"));
                item.setName(jsonObj.getString("title"));
                tempName.add(jsonObj.getString("title"));
                //image may be null
                String image=jsonObj.isNull("media")? null : jsonObj.getString("media");
                tempMedia.add(jsonObj.getString("media"));
                item.setImage(image);
                item.setStatus(jsonObj.getString("desc"));
                tempDesc.add(jsonObj.getString("desc"));
                //item.setProfilePic(jsonObj.getString("profilePic"));
                item.setProfilePic("");
                item.setTimeStamp(jsonObj.getString("date"));
                tempDate.add(jsonObj.getString("date"));
                String feedUrl="";
//                String feedUrl=jsonObj.isNull("url") ? null : jsonObj.getString("url");
                item.setUrl(feedUrl);

                feedItemList.add(item);

                tinyDB.putListString("date",tempDate);
                tinyDB.putListString("desc",tempDesc);
                tinyDB.putListString("id",tempId);
                tinyDB.putListString("name", tempName);

            }
            feedListAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            Log.d("PARSEJSONFEED", "i ENCOUNTERED AN ERROR");
            checkExistingLists();
            e.printStackTrace();
        }
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
