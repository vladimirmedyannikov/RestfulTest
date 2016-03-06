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

import ru.medyannikov.restfultest.CurboardPosts;
import ru.medyannikov.restfultest.MainActivity;
import ru.medyannikov.restfultest.R;
import ru.medyannikov.restfultest.RecycleAdapterPostCurboard;

/**
 * Created by Vladimir on 03.09.2015.
 */
public class CurboardPostsFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_posts;
    private View view;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    List<CurboardPosts> posts;
    private RecycleAdapterPostCurboard adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public CurboardPostsFragment() {
    }

    public static CurboardPostsFragment getInstance()
    {
        Bundle bundle = new Bundle();
        CurboardPostsFragment fragment = new CurboardPostsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.scree_two,container,false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleViewPostsCurboard);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshPostsCurboard);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                posts = cupboard().withDatabase(MainActivity.db1).query(CurboardPosts.class).list();
                if (posts != null) {
                    adapter = new RecycleAdapterPostCurboard(posts, getActivity(), recyclerView);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        return  rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new RecycleAdapterPostCurboard(posts, getActivity(), recyclerView);
        recyclerView.setAdapter(adapter);

    }
}
