package mobile.handler.com.handler_lib;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sangram.
 */
public class ServeResponseMessage {

    @SerializedName("image")
    private String image;
    @SerializedName("description")
    private String description;
    @SerializedName("title")
    private String title;

    public ServeResponseMessage(String image, String description, String title) {
        this.image = image;
        this.description = description;
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

