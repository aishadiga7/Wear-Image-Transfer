package retrofit.aishwarya.com.imagetransferwear;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import retrofit.aishwarya.com.sharemodule.MsgApiUtils;

/**
 * Created by aishwarya on 24/6/16.
 */
public class DataItemSender {

    private static Asset createAssetFromBitmap(Bitmap bitmap) {
        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
        return Asset.createFromBytes(byteStream.toByteArray());
    }

    public static void sendAccountListData(Context ctx) {

        GoogleApiClient googleApiClient = MsgApiUtils.initGoogleApiClient(ctx);
        ConnectionResult connectionResult = MsgApiUtils.initConnectionResult(googleApiClient);
        if (connectionResult.isSuccess() && googleApiClient.isConnected()) {
            ArrayList<DataMap> dataMapDepositAccountList = new ArrayList<>();

       /*     for (String accountItem : accountList) {
                DataMap dataMap = new DataMap();
                //dataMap.putString("id", accountItem.getId());
                dataMap.putString("name", accountItem);
                dataMapDepositAccountList.add(dataMap);
            }*/

            Log.d("data map size is",dataMapDepositAccountList.size()+"");
/*

            Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.card);
            Asset asset = createAssetFromBitmap(bitmap);
            PutDataRequest request = PutDataRequest.create("/image");
            request.putAsset("profileImage", asset);
            DataApi.DataItemResult result = Wearable.DataApi.putDataItem(googleApiClient, request).await();
*/


            Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.card);
            Asset asset = createAssetFromBitmap(bitmap);
            PutDataMapRequest dataMap = PutDataMapRequest.create("/image");
            dataMap.getDataMap().putAsset("profileImage", asset);
            dataMap.getDataMap().putDouble("timeStamp", System.currentTimeMillis());
            PutDataRequest request = dataMap.asPutDataRequest();
            DataApi.DataItemResult result = Wearable.DataApi
                    .putDataItem(googleApiClient, request).await();






           /* PutDataMapRequest putDataMap = PutDataMapRequest.create(PathConstants.SENDING_DATA_IMAGE);
            putDataMap.getDataMap().putDataMapArrayList(Constants.Keys.ACCOUNT_LIST_ITEM, dataMapDepositAccountList);
            putDataMap.getDataMap().putDouble("timeStamp", System.currentTimeMillis());
            PutDataRequest request = putDataMap.asPutDataRequest();*/
            //DataApi.DataItemResult result = Wearable.DataApi.putDataItem(googleApiClient,
              //      request).await();

            Log.d("DataItemSender","called");
            if (!result.getStatus().isSuccess()) {
                Log.d("data_sender", "Failed to send data");
            }
            else
            {
                Log.d("data_sender", "sucess");
            }

        } else {
            GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
            googleAPI.showErrorNotification(ctx, connectionResult.getErrorCode());

        }

    }
}
