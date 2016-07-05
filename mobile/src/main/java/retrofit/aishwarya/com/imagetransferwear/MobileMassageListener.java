package retrofit.aishwarya.com.imagetransferwear;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import retrofit.aishwarya.com.sharemodule.PathConstants;

/**
 * Created by aishwarya on 24/6/16.
 */
public class MobileMassageListener extends WearableListenerService {
    public static final String TAG = MobileMassageListener.class.getSimpleName();

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        Log.d(TAG, messageEvent.getPath());

        switch (messageEvent.getPath())
        {
            case PathConstants.CALL_IMAGE_LOADER:
                Intent intent = new Intent(this, UtilityService.class);
                startService(intent);
                break;
        }
    }
}
