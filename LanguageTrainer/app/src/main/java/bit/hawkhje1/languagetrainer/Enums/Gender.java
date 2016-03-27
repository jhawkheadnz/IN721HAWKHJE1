package bit.hawkhje1.languagetrainer.Enums;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Gender Enum
 */
public enum Gender implements Parcelable
{
    Neutral,
    Masculine,
    Feminine;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }

    public static final Parcelable.Creator<Gender> CREATOR = new Parcelable.Creator<Gender>() {
        @Override
        public Gender createFromParcel(final Parcel source) {
            return Gender.values()[source.readInt()];
        }

        @Override
        public Gender[] newArray(final int size) {
            return new Gender[size];
        }
    };

}
