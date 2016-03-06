package ru.medyannikov.restfultest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import ru.medyannikov.restfultest.Dictr.Dictr;
import ru.medyannikov.restfultest.Transform.CircleTransform;

/**
 * Created by Vladimir on 01.09.2015.
 */
public class RecycleAdapterPost extends RecyclerView.Adapter<RecycleAdapterPost.ViewHolder> {

    private List<Posts> listPost;
    public Picasso picasso;
    private Activity mainActivity;
    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTitle;
        public TextView mAbout;
        public TextView mDate;
        public Button button;
        public Button editButton;
        public ImageView imageView;
        Context context;


        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.textTitle);
            mAbout = (TextView) itemView.findViewById(R.id.textAbout);
            mDate = (TextView) itemView.findViewById(R.id.textDate);
            button = (Button) itemView.findViewById(R.id.button);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            editButton = (Button) itemView.findViewById(R.id.buttonEdit);
        }

    }

    @Override
    public RecycleAdapterPost.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycle_post_item,viewGroup,false);
        ViewHolder vh = new ViewHolder(v);
        vh.context = viewGroup.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterPost.ViewHolder viewHolder,final int i) {
        String url = listPost.get(i).getUrl_thm();
        if (url != null && url.length() > 1) {
            picasso.with(viewHolder.context).load(url)
                    .noFade()
                    .resize(50, 50)
                    .centerCrop()
                    .transform(new CircleTransform())
                    .into(viewHolder.imageView);
        }
        viewHolder.mTitle.setText(listPost.get(i).getId_post() +" "+ listPost.get(i).getTitle());
        viewHolder.mAbout.setText(listPost.get(i).getAbout());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        viewHolder.mDate.setText(dateFormat.format(listPost.get(i).getDate()));

        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //refreshItem(i,viewHolder);
                Intent intent = new Intent(viewHolder.context, PostDetails.class);
                intent.putExtra("Post", listPost.get(i));
                viewHolder.context.startActivity(intent);
            }
        });

        viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEdit = new Intent(viewHolder.context,post_editer.class);
                //viewHolder.context.startActivity(intentEdit);

                AlertDialog.Builder builder = new AlertDialog.Builder(viewHolder.context);
                intentEdit.putExtra("Mode",Dictr.ID_EDIT);
                intentEdit.putExtra("Post",listPost.get(i));
                mainActivity.startActivityForResult(intentEdit, Dictr.ID_EDIT);
            }
        });

    }


    private void refreshItem(int i, ViewHolder viewHolder) {

    }

    @Override
    public int getItemCount() {
        if (listPost != null){
            return listPost.size();
        }
        else return 0;
    }

    public RecycleAdapterPost(List<Posts> list,Activity activity, RecyclerView recyclerView)
    {
        listPost = list;
        mainActivity = activity;
        if(recyclerView.getLayoutManager()instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if(onLoadMoreListener!=null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    public void add(Posts post)
    {
        listPost.add(post);
    }

    public void setLoaded(){
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }
}
