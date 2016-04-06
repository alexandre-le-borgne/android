package iut.shootit.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Random;

import iut.shootit.R;

public class MainMapFragment extends Fragment {

    private Button mapButton;

    private static MainMapFragment instance;

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
        View rootView = inflater.inflate(R.layout.main_map_fragment, container, false);
//        mapButton = (Button) rootView.findViewById(R.id.main_map_button);
//        mapButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mapButton.animate().rotation(new Random().nextInt(360));
//            }
//        });
        return rootView;
    }

} // class MainMapFragment
