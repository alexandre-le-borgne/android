package iut.shootit.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import iut.shootit.R;

public class MainMapFragment extends Fragment {

    private Button mapButton;
    private List<Bitmap> images = new ArrayList<>();
    private int currentImage;
    ImageView imageView;

    private static MainMapFragment instance;

    private View rootView;

    private MainMapFragment() {
    }

    public static MainMapFragment getInstance() {
        if (instance == null)
            instance = new MainMapFragment();

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_map_fragment, container, false);
//        mapButton = (Button) rootView.findViewById(R.id.main_map_button);
//        mapButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mapButton.animate().rotation(new Random().nextInt(360));
//            }
//        });
        Button b = (Button) rootView.findViewById(R.id.reloadBtn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadImages();
            }
        });

        Button next = (Button) rootView.findViewById(R.id.nextButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentImage < images.size()-1) {
                    ++currentImage;
                    imageView.setImageBitmap(images.get(currentImage));
                }
            }
        });

        Button prev = (Button) rootView.findViewById(R.id.prevButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentImage >= 0) {
                    --currentImage;
                    imageView.setImageBitmap(images.get(currentImage));
                }
            }
        });
        return rootView;
    }

    public void reloadImages() {


//        String url = "http://alex83690.alwaysdata.net/shootit/?action=shot&shot_id=" + 1;

        String androidId = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        String url = "http://alex83690.alwaysdata.net/shootit/?action=gallery&android_id=" + androidId;

        JSONObject json;

        try {
            //On récupère les entités, et on parse le JSON.
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpResponse response = httpclient.execute(httppost);
            String str = EntityUtils.toString(response.getEntity());

            json = new JSONObject(str);

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        byte[] decodedString = new byte[0];
        try {
            ArrayList shots = (ArrayList) json.get("shots");
            for (int i = 0; i < shots.size(); ++i) {

                JSONObject shot = (JSONObject) shots.get(i);
                decodedString = Base64.decode(shot.getString("base64"), Base64.DEFAULT);
                Bitmap image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                images.add(image);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        imageView.setImageBitmap(images.get(0));
        currentImage = 0;

        Log.d("cc", "Chargment des images terminé");
    }
} // class MainMapFragment
