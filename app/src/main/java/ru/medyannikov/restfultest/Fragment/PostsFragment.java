package ru.medyannikov.restfultest.Fragment;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ru.medyannikov.restfultest.CurboardPosts;
import ru.medyannikov.restfultest.MainActivity;
import ru.medyannikov.restfultest.Posts;
import ru.medyannikov.restfultest.R;
import ru.medyannikov.restfultest.RecycleAdapterPost;
import ru.medyannikov.restfultest.RestAPI.PostService;

/**
 * Created by Vladimir on 03.09.2015.
 */
public class PostsFragment extends Fragment {
    private static final int LAYOUT = R.layout.fragment_posts;
    public static PostsFragment instance;
    private LinearLayoutManager  layoutManager;
    private RecyclerView recyclerView;
    List<Posts> posts;
    private RecycleAdapterPost adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fab;
    private int page = 1;
    Handler handler;
    private int pos;
    public static PostsFragment getInstance()
    {
        Bundle bundle = new Bundle();
        PostsFragment fragment = new PostsFragment();
        fragment.setArguments(bundle);
        instance = fragment;
        return fragment;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("State","on Save");
        outState.putSerializable("Posts", (Serializable) posts);
        super.onSaveInstanceState(outState);


    }

    @Override
    public void onResume() {
        Log.d("State","On resume");
        super.onResume();

    }

    @Override
    public void onPause() {
        Log.d("State", "On pause");
        pos = recyclerView.getVerticalScrollbarPosition();
        super.onPause();
        Bundle bundle = new Bundle();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("State","onActivity Created");
        super.onActivityCreated(savedInstanceState);
        if (posts != null && adapter != null){
            adapter = new RecycleAdapterPost(posts, getActivity(),recyclerView);

            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(pos);

            adapter.setOnLoadMoreListener(new RecycleAdapterPost.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    //add progress item
                    Toast.makeText(getContext(), "Load next", Toast.LENGTH_SHORT).show();
                    loadMore(posts, ++page, posts.size());
                }
            });
        }
    }

    @Override
    public void onStop() {
        Log.d("State","On stop");
        super.onStop();
    }

    @Override
    public void onStart() {
        Log.d("State","On start");
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("State","Create View");
        handler = new Handler();


        View view = inflater.inflate(LAYOUT, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh0);
        fab = (FloatingActionButton) view.findViewById(R.id.fabFrame);
        fab.attachToRecyclerView(recyclerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        //recyclerView.setLayoutManager(layoutManager);

        return view;
        //return  rootView;
    }

    private  void refreshContent()  {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://mangystapi.ru")
                        //addConverter(PostsExplorer.class, new PostsExplorerDeserializerJson())
                        //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        PostService service = restAdapter.create(PostService.class);
        Callback<List<Posts>> a = new Callback<List<Posts>>() {
            @Override
            public void success(List<Posts> postses, Response response) {
                posts = postses;
                for(Posts p:posts){
                    cupboard().withDatabase(MainActivity.db1).put(new CurboardPosts(new Long(p.getId_post()),p.getTitle(),p.getAbout(),p.getText(),p.getUrl(),p.getUrl_thm(),p.getDate()));
                }
                adapter = new RecycleAdapterPost(posts, getActivity(),recyclerView);
                recyclerView.setAdapter(adapter);

                adapter.setOnLoadMoreListener(new RecycleAdapterPost.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        //add progress item
                        Toast.makeText(getContext(), "Load next", Toast.LENGTH_SHORT).show();
                        loadMore(posts, ++page, posts.size());
                        System.out.println("load");
                    }
                });

                swipeRefreshLayout.setRefreshing(false);
                //result = 1;
            }
            @Override
            public void failure(RetrofitError error) {
                posts = new LinkedList<Posts>();
                //result = 0;
                //retrofitError = error;
                //Toast.makeText(view.getContext(),retrofitError.getMessage(),Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        };
        service.postList(a);


    }

    private void loadMore(final List<Posts> posts, int i, int i1) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://mangystapi.ru")
                .build();
        PostService service = restAdapter.create(PostService.class);
        Callback<List<Posts>> a = new Callback<List<Posts>>() {
            @Override
            public void success(List<Posts> postses, Response response) {
                for(Posts post:postses)
                {
                    posts.add(post);
                    adapter.notifyItemInserted(posts.size());
                }
                swipeRefreshLayout.setRefreshing(false);
                adapter.setLoaded();
                //result = 1;
            }
            @Override
            public void failure(RetrofitError error) {
                //posts = new LinkedList<Posts>();
                //result = 0;
                //retrofitError = error;
                swipeRefreshLayout.setRefreshing(false);
            }
        };
        service.postList(i1,15,a);
    }


}
