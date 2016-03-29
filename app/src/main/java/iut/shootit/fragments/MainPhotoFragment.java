package iut.shootit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import iut.shootit.R;

/**
 * http://haidermushtaq.com/tutorial-for-creating-android-swiping-application-using-tabbed-activity-on-android-studio/
 */
public class MainPhotoFragment extends Fragment {
    private Button ClickMe;
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
        this.ClickMe = (Button) rootView.findViewById(R.id.button);
        this.tv = (TextView) rootView.findViewById(R.id.main_photo_textView);
        ClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv.getText().toString().contains("Hello")) {
                    tv.setText("Hi");
                } else tv.setText("Hello");
            }
        });
        return rootView;
    }
} // class MainPhotoFragment
