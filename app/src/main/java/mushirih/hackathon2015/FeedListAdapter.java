package mushirih.hackathon2015;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by p-tah on 02/11/2015.
 */
public class FeedListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<FeedItem> feedItems;

    ImageLoader imageLoader= AppController.getInstance().getImageLoader();

    public FeedListAdapter(Activity activity, List<FeedItem> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int position) {
        return feedItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(layoutInflater==null){
            layoutInflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.feed_item,null);
        }
        if(imageLoader==null){
            imageLoader = AppController.getInstance().getImageLoader();
        }
        TextView name= (TextView) convertView.findViewById(R.id.name);
        TextView timestamp= (TextView) convertView.findViewById(R.id.timestamp);
        TextView statusMessage= (TextView) convertView.findViewById(R.id.txtStatusMsg);
        TextView url= (TextView) convertView.findViewById(R.id.txtUrl);

        NetworkImageView profilePic= (NetworkImageView) convertView.findViewById(R.id.profilePic);
        FeedImageView feedImageView= (FeedImageView) convertView.findViewById(R.id.feedImage1);

        FeedItem feedItem=feedItems.get(position);

        name.setText(feedItem.getName());
//LAST SEEN
       // CharSequence timeAgo= DateUtils.getRelativeTimeSpanString(Long.parseLong(feedItem.getTimeStamp()),System.currentTimeMillis(),DateUtils.SECOND_IN_MILLIS);
        String timeAgo=feedItem.getTimeStamp();
        timestamp.setText(timeAgo);

        if(!TextUtils.isEmpty(feedItem.getStatus())){
            statusMessage.setText(feedItem.getStatus());
            statusMessage.setVisibility(View.VISIBLE);
        }else{
            statusMessage.setVisibility(View.GONE);
        }

        if(feedItem.getUrl()!=null){
           // url.setText(Html.fromHtml("<a href="+feedItem.getUrl()+"\">"+feedItem.getUrl()+"</a>"));
            url.setText(Html.fromHtml("<a href=\"" + feedItem.getUrl() + "\">"
                    + feedItem.getUrl() + "</a> "));
            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);
        }else{
            url.setVisibility(View.GONE);
        }

       // profilePic.setImageUrl(feedItem.getProfilePic(),imageLoader);
        profilePic.setDefaultImageResId(R.drawable.icon);
        if(feedItem.getImage()!=null){
            feedImageView.setImageUrl(feedItem.getImage(),imageLoader);
            feedImageView.setVisibility(View.VISIBLE);
            feedImageView.setResponseObserver(new FeedImageView.ResponseObserver() {
                @Override
                public void onError() {

                }

                @Override
                public void onSuccess() {

                }
            });
        }else{
            feedImageView.setVisibility(View.GONE);
        }
        return convertView;
    }
}
