package bit.hawkhje1.languagetrainer.Classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Work on 3/26/2016.
 */
public class ParcelableTest implements Parcelable {

    private String test = "test";

    public String getTest(){ return test; }

    public ParcelableTest(){}

    protected ParcelableTest(Parcel in) {

        test = in.readString();

    }

    public static final Creator<ParcelableTest> CREATOR = new Creator<ParcelableTest>() {
        @Override
        public ParcelableTest createFromParcel(Parcel in) {
            return new ParcelableTest(in);
        }

        @Override
        public ParcelableTest[] newArray(int size) {
            return new ParcelableTest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(test);
    }
}
