package ru.medyannikov.restfultest.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.medyannikov.restfultest.CupboardTest.User;
import ru.medyannikov.restfultest.Fragment.CupboardFragment;
import ru.medyannikov.restfultest.Fragment.CurboardPostsFragment;
import ru.medyannikov.restfultest.Fragment.PostsFragment;
import ru.medyannikov.restfultest.MainActivity;
import ru.medyannikov.restfultest.R;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by Vladimir on 16.09.2015.
 */
public class TabPagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] tabs;
    private Context context;
    private FragmentManager fm;
    private Fragment[] frArray;


    public TabPagerFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        tabs = new String[]
                {
                  "Посты", "Новости","Посты Cu"," d "
                };
        this.context = context;
        this.fm = fm;
        frArray = new Fragment[]
                {
                        PostsFragment.getInstance(),CupboardFragment.getInstance(),CurboardPostsFragment.getInstance()
                };
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Long id_user = cupboard().withDatabase(MainActivity.db1).put(new User("Valera","mail","Token"));
                //PostsFragment fragment = (PostsFragment)fm.findFragmentById(R.id.framePosts);
                //if (fragment == null) return Fragment.instantiate(context, PostsFragment.class.getName());
                //return Fragment.instantiate(context, PostsFragment.class.getName());
                //return fragment;
                return frArray[0];
            case 1:
                return frArray[1];
            case 2:
                return frArray[2];
        }
        return CupboardFragment.getInstance();
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
