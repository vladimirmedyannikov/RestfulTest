package ru.medyannikov.restfultest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import ru.medyannikov.restfultest.Dictr.Dictr;

public class post_editer extends AppCompatActivity {

    private Button btnEdit;
    private Button btnCancel;
    private TextView editTitle;
    private TextView editDesc;
    private CalendarView editDate;
    private Posts post;
    private int mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_editer);

        btnEdit = (Button) findViewById(R.id.editItemPost);
        editTitle = (TextView) findViewById(R.id.editTitle);
        editDesc = (TextView) findViewById(R.id.editDesc);
        editDate = (CalendarView) findViewById(R.id.editDate);

        mode = getIntent().getIntExtra("Mode",0);
        post = (Posts) getIntent().getSerializableExtra("Post");

        if (post != null)
        {
            System.out.println(post.getId_post());

            editTitle.setText(post.getTitle());
            editDesc.setText(post.getAbout());
            editDate.setDate(post.getDate());
        }
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (mode)
                {
                    case Dictr.ID_ADD:
                        String title = editTitle.getText().toString();
                        String desc = editDesc.getText().toString();
                        Long date = editDate.getDate();
                        Posts postAdd = new Posts(0,title,desc,desc,"","",date);
                        Intent intentAdd = new Intent();
                        intentAdd.putExtra("Post", postAdd);
                        setResult(Dictr.ID_ADD, intentAdd);
                        finish();
                        break;

                    case Dictr.ID_EDIT:
                        Intent intent = new Intent();
                        intent.putExtra(Dictr.POST_TITLE,editTitle.getText().toString());
                        intent.putExtra(Dictr.POST_DESCRIPTION, editDesc.getText().toString());
                        intent.putExtra(Dictr.POST_ID, post.getId_post());
                        setResult(Dictr.ID_EDIT, intent);
                        finish();
                        break;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_editer, menu);
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
