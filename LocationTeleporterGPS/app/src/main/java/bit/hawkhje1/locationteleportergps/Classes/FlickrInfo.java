package bit.hawkhje1.locationteleportergps.Classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import bit.hawkhje1.locationteleportergps.Interfaces.AsyncCallback;
import bit.hawkhje1.locationteleportergps.MainActivity;

/**
 * FlickrInfo class for holding flickr info
 */
public class FlickrInfo {

    // for logcat
    private static final String FLICKR_INFO = "FLICKR_INFO";

    @Override
    public String toString() {
        return "FlickrInfo{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", secret='" + secret + '\'' +
                ", server='" + server + '\'' +
                ", farm=" + farm +
                ", title='" + title + '\'' +
                ", isPublic=" + isPublic +
                ", isFriend=" + isFriend +
                ", isFamily=" + isFamily +
                ", image=" + image +
                ", url=" + getImageURL() +
                '}';
    }

    // JSON Properties
    private String id;
    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;
    private Boolean isPublic;
    private Boolean isFriend;
    private Boolean isFamily;
    private Bitmap image;

    // default constructor
    public FlickrInfo(){}

    // override constructor
    public FlickrInfo(String id, String owner, String secret, String server, int farm,
                      String title, Boolean isPublic, Boolean isFriend, Boolean isFamily) {
        this.id = id;
        this.owner = owner;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        this.title = title;
        this.isPublic = isPublic;
        this.isFriend = isFriend;
        this.isFamily = isFamily;
    }

    public void loadImageForImageView(Context context, final ImageView imageView){

        GetImageAsyncTask geoImageAsyncTask = new GetImageAsyncTask(context);

        geoImageAsyncTask.setCallbackListener(new AsyncCallback<Bitmap>() {
            @Override
            public void run(Bitmap result) {
                imageView.setImageBitmap(result);
            }
        });

        geoImageAsyncTask.execute(getImageURL(), "TEST");
    }


    /**
     * Returns the Flickr Image
     * @return Flickr Image
     */
    public Bitmap getImage() {
        return image;
    }

    /**
     * Sets the Flickr Image
     * @param image Bitmap object
     */
    public void setImage(Bitmap image) {
        this.image = image;
    }

    /**
     * Constructs and returns the url for the image
     * @return URL for Image
     */
    public String getImageURL(){
        return String.format(Globals.Flickr.BASE_IMAGE_URL, farm, server,
                id, secret, Globals.Flickr.DEFAULT_IMAGE_SIZE);
    }

    /**
     * Returns the ID of the Image
     * @return Flickr Image ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the Flickr Image ID
     * @param id Image ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the Owner of the Image
     * @return Owner of Image
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the Image
     * @param owner Owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Gets the Secret of the Image
     * @return Image Secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Sets the Secrete of the Image
     * @param secret Image Secret
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * Gets the Server of the Image
     * @return Image Server
     */
    public String getServer() {
        return server;
    }

    /**
     * Sets the server of the Image
     * @param server Image Server
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * Gets the Server Farm ID of the Image
     * @return Image Srrver Farm ID
     */
    public int getFarm() {
        return farm;
    }

    /**
     * Sets the Server Farm ID of the Image
     * @param farm Server Farm ID
     */
    public void setFarm(int farm) {
        this.farm = farm;
    }

    /**
     * Gets the title of the Image
     * @return Title of Image
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the Image
     * @param title Image Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Shows if the image is public
     * @return Visibility of Image to Public
     */
    public Boolean getIsPublic() {
        return isPublic;
    }

    /**
     * Sets the publicity of the image
     * @param isPublic Visibility
     */
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * Shows if the current image is from the friend who owns APIKey
     * @return Returns true if Image is from a Friend
     */
    public Boolean getIsFriend() {
        return isFriend;
    }

    /**
     * Set true if image is from a friend
     * @param isFriend Image is from a friend
     */
    public void setIsFriend(Boolean isFriend) {
        this.isFriend = isFriend;
    }

    /**
     * Shows if the current image is from a family member of owner of APIKey
     * @return returns true if image is from a family member
     */
    public Boolean getIsFamily() {
        return isFamily;
    }

    /**
     * Set true if image is from a family member
     * @param isFamily Image is from a family member
     */
    public void setIsFamily(Boolean isFamily) {
        this.isFamily = isFamily;
    }
}
