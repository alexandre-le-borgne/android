package iut.shootit.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import iut.shootit.CaptureActivity;
import iut.shootit.R;

/**
 * http://haidermushtaq.com/tutorial-for-creating-android-swiping-application-using-tabbed-activity-on-android-studio/
 */
public class MainPhotoFragment extends Fragment {
    private Button takePic;
    private Button seePic;
    private TextView tv;


    private static MainPhotoFragment instance;

    private MainPhotoFragment() {
    }

    public static MainPhotoFragment getInstance() {
        if (instance == null)
            instance = new MainPhotoFragment();

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_photo_fragment,
                container, false);
        takePic = (Button) rootView.findViewById(R.id.take_picture);
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivity(intent);
            }
        });

        seePic = (Button) rootView.findViewById(R.id.see_picture);
        tv = (EditText) rootView.findViewById(R.id.choice_text);
        final String imageId = tv.getText().toString();
        seePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImg(imageId);
            }
        });


        return rootView;
    }

    private void loadImg(String imageId) {
        String url = CaptureActivity.URL;
        JSONObject retour;

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("action", "shot"));
        nameValuePairs.add(new BasicNameValuePair("shot_id", imageId));

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            String str = EntityUtils.toString(response.getEntity());
            Log.v("log_tag", "In the try Loop" + str);

            retour = new JSONObject(str);

        } catch (Exception e) {
            Log.v("log_tag", "Error in http connection ");
            e.printStackTrace();
            return;
        }

        String imageBase64;
        try {
            imageBase64 = retour.getString("base64");
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
        Bitmap image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        ImageView imageView = (ImageView) getActivity().findViewById(R.id.image_shot);
        imageView.setImageBitmap(image);


    }

} // class MainPhotoFragment
