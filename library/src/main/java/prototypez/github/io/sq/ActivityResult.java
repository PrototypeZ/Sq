package prototypez.github.io.sq;

import android.content.Intent;
import android.os.Bundle;

public class ActivityResult {

    private int requestCode;
    private int resultCode;
    private Intent data;

    public ActivityResult(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Intent getData() {
        return data;
    }

    public void setData(Intent data) {
        this.data = data;
    }

    public Bundle getRequestContextData() {
        Bundle requestData = getRequestData();
        if (requestData != null) {
            return requestData.getBundle(SqConstant.KEY_REQUEST_CONTEXT_DATA);
        } else {
            return null;
        }
    }

    public Bundle getRequestData() {
        if (data != null) {
            return data.getBundleExtra(SqConstant.KEY_REQUEST_DATA);
        } else {
            return null;
        }
    }
}
