package mushirih.hackathon2015;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class Home extends Activity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    Button babout,bcontact;
    //String [] titles={"My claims","Find provider","My cover","Find us"};
    String [] titles={"Tips and Advice","Contact Doctor","Settings","About App"};
    int icons[]={R.drawable.tips,R.drawable.call,R.drawable.settings,R.drawable.about};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            setRecyclerAdapter(recyclerView);
        }else{

                setRecyclerAdapter(recyclerView);
            }

//        RecyclerView.LayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            setRecyclerAdapter(recyclerView);
//        }
/*
        babout= (Button) findViewById(R.id.about);
        babout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),AboutUs.class));
            }
        });
        bcontact= (Button) findViewById(R.id.contact);
        bcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),ContactUs.class));
            }
        });
        */
//
//
//
//
//
//
//
//
//        final GestureDetector gestureDetector=new GestureDetector(MainActivity.this,new GestureDetector.SimpleOnGestureListener() {
//
//
//            @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//                return true;
//            }
//
//
//
//        });
//        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                View child = rv.findChildViewUnder(e.getX(), e.getY());
//                if (child != null && gestureDetector.onTouchEvent(e)) {
//
//                    int pos = recyclerView.getChildPosition(child);
//
//                    switch (pos) {
//                        case 0:
//                            startActivity(new Intent(getBaseContext(),MyClaims.class));
//
//                            break;
//                        case 1:
//                            startActivity(new Intent(getBaseContext(),MyProvider.class));
//
//                            break;
//                        case 2:
//                            startActivity(new Intent(getBaseContext(),Login.class));
//
//                            break;
//                    }
//                    return true;
//                }
//                return false;
//            }
//            @Override
//            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//            }
//        });
//

    }

    @Override
    protected void onResume() {
        super.onResume();
//        recyclerView= (RecyclerView) findViewById(R.id.recycler);
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//            setRecyclerAdapter(recyclerView);
//        }


        final GestureDetector gestureDetector=new GestureDetector(Home.this,new GestureDetector.SimpleOnGestureListener() {


            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }



        });
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {

                    int pos = recyclerView.getChildPosition(child);

                    switch (pos) {
                        case 0:
                            startActivity(new Intent(getBaseContext(),Tips.class));

                            break;
                        case 1:
                            startActivity(new Intent(getBaseContext(),ContactUs.class));

                            break;
                        case 2:
                            startActivity(new Intent(getBaseContext(),Settings.class));

                            break;
                        case 3:
                            startActivity(new Intent(getBaseContext(),AboutUs.class));
                            break;
                    }
                    return true;
                }
                return false;
            }
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }
        });


    }



    private void setRecyclerAdapter(RecyclerView recyclerView) {
        adapter=new MyAdapter(titles,icons);

        recyclerView.setAdapter(adapter);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        recyclerView.setAdapter(adapter);
        recyclerView.scheduleLayoutAnimation();

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
            startActivity(new Intent(getBaseContext(),Settings.class));
            return true;
        }
        if (id == R.id.action_service) {

            startService(new Intent(Home.this, MyService.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
