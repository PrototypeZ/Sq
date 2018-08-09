package prototypez.github.io.sq.util;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Bundle Helper
 * Created by zhounl on 2017/10/24.
 */

public class BundleBuilder {

    private Bundle mBundle = new Bundle();

    public static BundleBuilder newInstance() {
        return new BundleBuilder();
    }

    public BundleBuilder putLong(String key, long value) {
        mBundle.putLong(key, value);
        return this;
    }

    public BundleBuilder putInt(String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }


    public BundleBuilder putString(String key, String value) {
        mBundle.putString(key, value);
        return this;
    }

    public BundleBuilder putSerializable(String key, Serializable value) {
        mBundle.putSerializable(key, value);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public BundleBuilder putBinder(String key, IBinder value) {
        mBundle.putBinder(key, value);
        return this;
    }

    public BundleBuilder putBundle(String key, Bundle value) {
        mBundle.putBundle(key, value);
        return this;
    }

    public BundleBuilder putByte(String key, byte value) {
        mBundle.putByte(key, value);
        return this;
    }

    public BundleBuilder putByteArray(String key, byte[] value) {
        mBundle.putByteArray(key, value);
        return this;
    }

    public BundleBuilder putChar(String key, char value) {
        mBundle.putChar(key, value);
        return this;
    }

    public BundleBuilder putCharArray(String key, char[] value) {
        mBundle.putCharArray(key, value);
        return this;
    }

    public BundleBuilder putCharSequence(String key, CharSequence value) {
        mBundle.putCharSequence(key, value);
        return this;
    }

    public BundleBuilder putCharSequenceArrayList(String key, ArrayList<CharSequence> value) {
        mBundle.putCharSequenceArrayList(key, value);
        return this;
    }

    public BundleBuilder putFloat(String key, float value) {
        mBundle.putFloat(key, value);
        return this;
    }

    public BundleBuilder putFloatArray(String key, float[] value) {
        mBundle.putFloatArray(key, value);
        return this;
    }

    public BundleBuilder putDouble(String key, double value) {
        mBundle.putDouble(key, value);
        return this;
    }

    public BundleBuilder putDoubleArray(String key, double[] value) {
        mBundle.putDoubleArray(key, value);
        return this;
    }

    public BundleBuilder putParcelable(String key, Parcelable value) {
        mBundle.putParcelable(key, value);
        return this;
    }

    public BundleBuilder putParcelableArray(String key, Parcelable[] value) {
        mBundle.putParcelableArray(key, value);
        return this;
    }

    public BundleBuilder putParcelableArrayList(String key, ArrayList<? extends Parcelable> value) {
        mBundle.putParcelableArrayList(key, value);
        return this;
    }

    public BundleBuilder putIntArray(String key, int[] value) {
        mBundle.putIntArray(key, value);
        return this;
    }

    public BundleBuilder putIntegerArrayList(String key, ArrayList<Integer> value) {
        mBundle.putIntegerArrayList(key, value);
        return this;
    }

    public BundleBuilder putShort(String key, short value) {
        mBundle.putShort(key, value);
        return this;
    }

    public BundleBuilder putShortArray(String key, short[] value) {
        mBundle.putShortArray(key, value);
        return this;
    }

    public BundleBuilder putBoolean(String key, boolean value) {
        mBundle.putBoolean(key, value);
        return this;
    }

    public BundleBuilder putBooleanArray(String key, boolean[] value) {
        mBundle.putBooleanArray(key, value);
        return this;
    }

    public BundleBuilder putStringArrayList(String key, ArrayList<String> value) {
        mBundle.putStringArrayList(key, value);
        return this;
    }

    public BundleBuilder putStringArray(String key, String[] value) {
        mBundle.putStringArray(key, value);
        return this;
    }

    public BundleBuilder putSparseParcelableArray(String key, SparseArray<? extends Parcelable> value) {
        mBundle.putSparseParcelableArray(key, value);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BundleBuilder putSize(String key, Size value) {
        mBundle.putSize(key, value);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BundleBuilder putSizeF(String key, SizeF value) {
        mBundle.putSizeF(key, value);
        return this;
    }

    public Bundle build() {
        return mBundle;
    }

    private BundleBuilder() {
    }
}
