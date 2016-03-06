package ru.medyannikov.restfultest.Fragment;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.medyannikov.restfultest.CupboardTest.User;
import ru.medyannikov.restfultest.MainActivity;
import ru.medyannikov.restfultest.R;
import ru.medyannikov.restfultest.RecycleAdapterUser;

/**
 * Created by Vladimir on 03.09.2015.
 */
public class CupboardFragment extends Fragment {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager layoutManager;
    List<User> userList;
    RecycleAdapterUser adapter;

    public static CupboardFragment getInstance()
    {
        Bundle bundle = new Bundle();
        CupboardFragment fragment = new CupboardFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.screen_three, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleViewCupboard);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshCupboard);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userList = cupboard().withDatabase(MainActivity.db1).query(User.class).list();
                if (userList != null) {
                    adapter = new RecycleAdapterUser(userList, recyclerView);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new RecycleAdapterUser(userList, recyclerView);
        recyclerView.setAdapter(adapter);

    }
}
