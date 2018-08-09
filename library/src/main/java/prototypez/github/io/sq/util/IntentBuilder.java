package prototypez.github.io.sq.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Intent Helper
 */
public class IntentBuilder {

    private Intent mIntent;

    public static IntentBuilder newInstance() {
        return new IntentBuilder();
    }

    public static IntentBuilder newInstance(Context context, Class<?> clazz) {
        return new IntentBuilder(context, clazz);
    }

    public IntentBuilder putExtra(String key, boolean value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, byte value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, char value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, short value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, int value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, long value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, float value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, double value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, String value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, CharSequence value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, Parcelable value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, Parcelable[] value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putParcelableArrayListExtra(String key, ArrayList<? extends Parcelable> value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putIntegerArrayListExtra(String key, ArrayList<Integer> value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putStringArrayListExtra(String key, boolean value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putCharSequenceArrayListExtra(String key, boolean value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, Serializable value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, boolean[] value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, byte[] value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, short[] value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, char[] value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, int[] value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, long[] value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, float[] value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, double[] value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, String[] value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, CharSequence[] value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtra(String key, Bundle value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public IntentBuilder putExtras(Intent value) {
        mIntent.putExtras(value);
        return this;
    }

    public IntentBuilder putExtras(Bundle value) {
        mIntent.putExtras(value);
        return this;
    }

    public IntentBuilder replaceExtras(Intent value) {
        mIntent.replaceExtras(value);
        return this;
    }

    public IntentBuilder replaceExtras(Bundle value) {
        mIntent.replaceExtras(value);
        return this;
    }

    public IntentBuilder removeExtra(String key) {
        mIntent.removeExtra(key);
        return this;
    }

    public Intent build() {
        return mIntent;
    }

    private IntentBuilder() {
        mIntent = new Intent();
    }

    private IntentBuilder(Context context, Class<?> clazz) {
        mIntent = new Intent(context, clazz);
    }
}
