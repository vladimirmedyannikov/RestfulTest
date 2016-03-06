package ru.medyannikov.restfultest;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import ru.medyannikov.restfultest.Adapter.TabPagerFragmentAdapter;
import ru.medyannikov.restfultest.CupboardTest.CupboardSQLiteHelper;
import ru.medyannikov.restfultest.DB.PostDataSource;
import ru.medyannikov.restfultest.Dictr.Dictr;
import ru.medyannikov.restfultest.Fragment.PostsFragment;
import ru.medyannikov.restfultest.Fragment.CupboardFragment;
import ru.medyannikov.restfultest.Fragment.CurboardPostsFragment;

public class MainActivity extends AppCompatActivity {

    //private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private CardView cardView;
    private NavigationView drawerMenu;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private PostDataSource dataSource;
    private int mPreviousVisibleItem;
    private ArrayList<Post> post;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    List<Posts> posts;
    //SwipeRefreshLayout swipeRefreshLayout;
    String[] imageList;
   public static SQLiteDatabase db1;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CupboardSQLiteHelper dbHelper = new CupboardSQLiteHelper(this);
        db1 = dbHelper.getWritableDatabase();
        cupboard();

        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        TabPagerFragmentAdapter adapter = new TabPagerFragmentAdapter(getSupportFragmentManager(), getBaseContext());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        imageList = getResources().getStringArray(R.array.image_array);

        dataSource = new PostDataSource(this);
        dataSource.open();

        if (savedInstanceState != null)
        dataSource.insertPost("Title1", "Short_text", "Text", new Date().getTime(),"");

        //активируем акшн бар
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawermain);

        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.hello_world,R.string.hello_world){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu();
                //supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle("Application");
                invalidateOptionsMenu();
                //supportInvalidateOptionsMenu();
            }
        };

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);

        drawerMenu = (NavigationView) findViewById(R.id.drawermenu);

        drawerMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                selectItem(id);
                return true;
            }
        });

        /*drawerMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });*/

        /*swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh0);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);*/

        //layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
        //initializatePost();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEdit = new Intent(v.getContext(), post_editer.class);
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                intentEdit.putExtra("Mode", Dictr.ID_ADD);
                MainActivity.this.startActivityForResult(intentEdit, Dictr.ID_ADD);
            }
        });
        */
        //fab.attachToRecyclerView(recyclerView);


        /*swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });*/
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    private void selectItem(int position) {
        Fragment fragment = null;
        boolean mainM = false;
        switch (position){
            case R.id.menu_main:
                mainM = true;
                /*CupboardSQLiteHelper dbHelper = new CupboardSQLiteHelper(this);
                db1 = dbHelper.getWritableDatabase();
                Long id_user = cupboard().withDatabase(db1).put(new User("Valera","mail","Token"));
                Toast.makeText(getApplication(),Long.toString(id_user),Toast.LENGTH_SHORT).show();*/
                //fragment = new Fragment();
                break;
            case R.id.menu1:
                fragment = new PostsFragment();
                break;
            case R.id.menu2:
            fragment = new CurboardPostsFragment();
            break;
            case 3:
                fragment = new CupboardFragment();
                break;
            default:
                break;
        }

        if (fragment != null){
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            //fragmentManager.beginTransaction().add(fragment,"TAG").commit();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
            //drawerMenu.setItemChecked(position,true);
        }
        drawerLayout.closeDrawer(drawerMenu);
    }

    private ArrayList<Post> getPosts() {
        return post;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode)
        {
            case Dictr.ID_EDIT:
                editItem(data);
                break;

            case Dictr.ID_ADD:
                addItem(data);
                break;
        }

    }

    private void addItem(Intent data) {
        Bundle bundle = data.getExtras();
        Posts post = (Posts) bundle.getSerializable("Post");
        dataSource.insertPost(post.getTitle(), post.getAbout(), post.getText(), post.getDate(), post.getUrl());
        //refreshAdapter();
    }

    private void editItem(Intent data) {
        Bundle bundle = data.getExtras();


        String title = bundle.getString(Dictr.POST_TITLE);
        String decr = bundle.getString(Dictr.POST_DESCRIPTION);
        long idPost = bundle.getLong(Dictr.POST_ID);

        Post post = new Post(idPost,title,decr,decr,new Date().getTime(),null);
        dataSource.updatePost(post);

        //refreshAdapter();
        //refreshContent();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (drawerToggle.onOptionsItemSelected(item)) //необходимо для активации нажатия хоум  батон
        {
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
