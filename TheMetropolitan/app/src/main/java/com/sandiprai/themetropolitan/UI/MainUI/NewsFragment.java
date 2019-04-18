package com.sandiprai.themetropolitan.UI.MainUI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.sandiprai.themetropolitan.Adapter.NewsCategoryAdapter;
import com.sandiprai.themetropolitan.R;


public class NewsFragment extends Fragment {
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NewsCategoryAdapter newsCategoryAdapter = new NewsCategoryAdapter(getContext(),getChildFragmentManager());
        ViewPager pager = view.findViewById(R.id.pagerNewsCategory);
        pager.setAdapter(newsCategoryAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tabsCategory);
        tabLayout.setupWithViewPager(pager);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
