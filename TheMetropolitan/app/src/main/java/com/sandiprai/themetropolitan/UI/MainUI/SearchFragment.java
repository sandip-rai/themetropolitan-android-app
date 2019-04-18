package com.sandiprai.themetropolitan.UI.MainUI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.sandiprai.themetropolitan.R;
import com.sandiprai.themetropolitan.TestWordPress;
import com.squareup.picasso.Picasso;

public class SearchFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        //Get the buttons and start their listener
        Button buttonSearch = view.findViewById(R.id.buttonSearch);
        Button buttonClear = view.findViewById(R.id.buttonClear);

        buttonSearch.setOnClickListener(this);
        buttonClear.setOnClickListener(this);

        ImageView imageView1 = view.findViewById(R.id.imageView1);

        Picasso.get().load("https://cybersecuritybysandip.files.wordpress.com/2017/01/pushbullet-encryption-100606774-large.jpg").
                into(imageView1);
//https://photoshd.files.wordpress.com/2014/04/new-zealand.jpg
//        Glide.with(view).load("https://cdn-images-1.medium.com/max/1600/1*VCQULp9m08s4dO2TgXg2Zw.png").into(imageView1);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSearch:
                /*CharSequence text = "Search button clicked!";
                showToast(text);*/

                Intent intent = new Intent(view.getContext(), TestWordPress.class);
                startActivity(intent);
                break;

            case R.id.buttonClear:
                TextInputEditText searchText = getView().findViewById(R.id.textInputSearch);
                searchText.setText("");
                CharSequence text1 = "Search text cleared!";
                showToast(text1);
                break;
        }

    }

    //Shows the passed string as a toast
    public void showToast(CharSequence text) {
        int duration1 = Toast.LENGTH_SHORT;

        Toast toast1 = Toast.makeText(getActivity().getApplicationContext(), text, duration1);
        toast1.show();
    }
}
