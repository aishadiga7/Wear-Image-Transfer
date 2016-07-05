package retrofit.aishwarya.com.imagetransferwear;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by aishwarya on 24/6/16.
 */
public class DtaMapSendingTask  extends AsyncTask<String, Void, Void> {
    private Context mCtx;

    public DtaMapSendingTask(Context ctx) {
        mCtx = ctx;
    }


    @Override
    protected Void doInBackground(String... params) {
        String path = params[0];
        DataItemSender.sendAccountListData(mCtx);
        Log.d("DtaMapSendingTask","called");
        return null;
    }

}
