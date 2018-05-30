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
