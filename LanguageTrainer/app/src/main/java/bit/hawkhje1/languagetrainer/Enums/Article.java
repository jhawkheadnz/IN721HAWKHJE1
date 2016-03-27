package bit.hawkhje1.languagetrainer.Enums;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Article Enum
 */
public enum Article implements Parcelable {

    Der,
    Die,
    Das;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(final Parcel source) {
            return Article.values()[source.readInt()];
        }

        @Override
        public Article[] newArray(final int size) {
            return new Article[size];
        }
    };

}
