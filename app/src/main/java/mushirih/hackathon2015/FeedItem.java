package mushirih.hackathon2015;

/**
 * Created by p-tah on 02/11/2015.
 */
public class FeedItem {
    private int id;
    private String name,status,image,profilePic,timeStamp,url;

    public FeedItem(){

    }

    public FeedItem(int id, String image, String name, String profilePic, String status, String timeStamp, String url) {
        super();
        this.id = id;
        this.image = image;
        this.name = name;
        this.profilePic = profilePic;
        this.status = status;
        this.timeStamp = timeStamp;
        this.url = url;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
