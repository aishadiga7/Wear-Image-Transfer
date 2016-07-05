package retrofit.aishwarya.com.imagetransferwear;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import retrofit.aishwarya.com.sharemodule.PathConstants;
import retrofit.aishwarya.com.sharemodule.SendMessageTask;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final long TIMEOUT_MS = 2000;
    protected final String TAG = MainActivity.class.getSimpleName();
    private ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.card_image);
        findViewById(R.id.get_image).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.get_image) {
            new SendMessageTask(this).execute(PathConstants.CALL_IMAGE_LOADER, "");
        }
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        super.onDataChanged(dataEvents);
        Log.d(TAG, "onDataChanged:");
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED &&
                    event.getDataItem().getUri().getPath().equals("/image")) {
                DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                 final Asset profileAsset = dataMapItem.getDataMap().getAsset("profileImage");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = loadBitmapFromAsset(profileAsset);
                        loadBitMapIntoImageView(bitmap);
                    }
                }).start();

                Toast.makeText(this, "Bitmap received", Toast.LENGTH_LONG).show();
                // Do something with the bitmap
            }
        }
    }

    private void loadBitMapIntoImageView(final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
              mImageView.setImageBitmap(bitmap);
            }
        });

    }

    public Bitmap loadBitmapFromAsset(Asset asset) {
        if (asset == null) {
            throw new IllegalArgumentException("Asset must be non-null");
        }
        ConnectionResult result =
                mGoogleApiClient.blockingConnect(TIMEOUT_MS, TimeUnit.MILLISECONDS);
        if (!result.isSuccess()) {
            return null;
        }
        // convert asset into a file descriptor and block until it's ready
        InputStream assetInputStream = Wearable.DataApi.getFdForAsset(
                mGoogleApiClient, asset).await().getInputStream();
        mGoogleApiClient.disconnect();

        if (assetInputStream == null) {
            Log.w(TAG, "Requested an unknown Asset.");
            return null;
        }
        // decode the stream into a bitmap
        return BitmapFactory.decodeStream(assetInputStream);
    }
}
