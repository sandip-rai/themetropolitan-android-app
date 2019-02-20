package com.sandiprai.themetropolitan;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HelloFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HelloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HelloFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hello, container, false);
    }


}
