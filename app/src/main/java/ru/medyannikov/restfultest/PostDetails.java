package ru.medyannikov.restfultest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;



public class PostDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        Posts post = (Posts) getIntent().getSerializableExtra("Post");
        Picasso picasso = null;
        TextView titleDetail = (TextView) findViewById(R.id.titleDetail);
        ImageView imageDetail = (ImageView) findViewById(R.id.imageDetail);
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
        if (post != null)
        {
            String url = post.getUrl();

            titleDetail.setText(post.getTitle());
            if (url != null && url.length() > 1) {
                picasso.with(this.getBaseContext()).load(post.getUrl())
                        //.fit()
                        //.noFade()
                        // .centerInside()

                        .into(imageDetail);
            }

            imageDetail.postInvalidate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
