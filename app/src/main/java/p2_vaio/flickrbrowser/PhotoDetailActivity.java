package p2_vaio.flickrbrowser;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends BaseActivity {
    private static final String TAG = "PhotoDetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
/*        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imageView = (ImageView)findViewById(R.id.photo_image);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        activateToolBar(true);
        Intent intent = getIntent();
        Photo photo = (Photo)intent.getSerializableExtra(PHOTO_TRANSFER);
        if(photo!=null){
            ImageView imageView = (ImageView)findViewById(R.id.photo_image);
            TextView title = (TextView)findViewById(R.id.photo_title);
            TextView author = (TextView)findViewById(R.id.photo_author);
            TextView tags = (TextView)findViewById(R.id.phtot_tags);

            Resources resources = getResources();
            String text = resources.getString(R.string.photo_title_text , photo.getTitle());
            title.setText(text);
            text=resources.getString(R.string.photo_title_tags , photo.getTags());
            tags.setText(text);

//            title.setText(photo.getTitle());
            author.setText(photo.getAuthor());
//            tags.setText(photo.getTags());
            Picasso.with(this).load(photo.getLink())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);
        }
    }

}
