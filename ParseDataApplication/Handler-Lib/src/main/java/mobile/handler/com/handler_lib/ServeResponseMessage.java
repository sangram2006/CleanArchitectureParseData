package mobile.handler.com.handler_lib;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sangram.
 */
public class ServeResponseMessage implements Parcelable {

    @SerializedName("userId")
    private int userId;
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;

    public ServeResponseMessage(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    protected ServeResponseMessage(Parcel in) {
        userId = in.readInt();
        id = in.readInt();
        title = in.readString();
        body = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(body);
    }

    public static final Parcelable.Creator<ServeResponseMessage> CREATOR = new Parcelable.Creator<ServeResponseMessage>() {
        @Override
        public ServeResponseMessage createFromParcel(Parcel in) {
            return new ServeResponseMessage(in);
        }

        @Override
        public ServeResponseMessage[] newArray(int size) {
            return new ServeResponseMessage[size];
        }
    };
}

