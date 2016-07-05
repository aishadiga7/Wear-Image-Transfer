package retrofit.aishwarya.com.imagetransferwear;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import retrofit.aishwarya.com.sharemodule.PathConstants;

public class ImageSendActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_send);
        findViewById(R.id.load_image).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.load_image) {
            new DtaMapSendingTask(this).execute(PathConstants.SENDING_DATA_IMAGE,  " ");
        }
    }

}
