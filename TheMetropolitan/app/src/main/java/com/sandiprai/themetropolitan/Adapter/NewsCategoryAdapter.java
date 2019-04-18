package com.sandiprai.themetropolitan.Adapter;

import android.content.Context;

import com.sandiprai.themetropolitan.R;
import com.sandiprai.themetropolitan.UI.NewsCategories.ArtsFragment;
import com.sandiprai.themetropolitan.UI.NewsCategories.NewsCategoryFragment;
import com.sandiprai.themetropolitan.UI.NewsCategories.OpinionFragment;
import com.sandiprai.themetropolitan.UI.NewsCategories.StudentLifeFragment;
import com.sandiprai.themetropolitan.UI.NewsCategories.TechFragment;
import com.sandiprai.themetropolitan.UI.NewsCategories.TopStoriesFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class NewsCategoryAdapter extends FragmentPagerAdapter {
    private Context context;

    public NewsCategoryAdapter(Context context, FragmentManager fm){
        super(fm);
        this.context = context;

    }

    @Override
    public int getCount() {
        return 6;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TopStoriesFragment();
            case 1:
                return new NewsCategoryFragment();
            case 2:
                return new StudentLifeFragment();
            case 3:
                return new OpinionFragment();
            case 4:
                return new ArtsFragment();
            case 5:
                return new TechFragment();
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getResources().getText(R.string.nav_topStories);
            case 1:
                return context.getResources().getText(R.string.nav_news);
            case 2:
                return context.getResources().getText(R.string.nav_studentlife);
            case 3:
                return context.getResources().getText(R.string.nav_opinion);
            case 4:
                return context.getResources().getText(R.string.nav_arts);
            case 5:
                return context.getResources().getText(R.string.nav_tech);

        }

        return null;
    }
}
