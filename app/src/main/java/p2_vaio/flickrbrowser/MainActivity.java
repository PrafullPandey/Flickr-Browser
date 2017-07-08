package p2_vaio.flickrbrowser;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
/*import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;*/
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
//import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.data;
import static android.R.attr.logo;

public class MainActivity extends AppCompatActivity implements GetFlickrJSONData.OnDataAvailable,
        RecyclerItemClickListener.onRecyclerClickListener {

    private static final String TAG = "MainActivity";
    private static final String ImageURL="IMAGEURL";
    private FlickrRecyclerViewAdapter flickrRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,recyclerView,this));

        flickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(MainActivity.this, new ArrayList<Photo>());
        recyclerView.setAdapter(flickrRecyclerViewAdapter);

       /* GetRawData getRawData = new GetRawData(this);
        getRawData.execute("https://api.flickr.com/services/feeds/photos_public.gne?tags=apple&format=json&nojsoncallback=1");
*/

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();
        GetFlickrJSONData getFlickrJSONData = new GetFlickrJSONData(this,"https://api.flickr.com/services/feeds/photos_public.gne",
                "en-us",true);
//        getFlickrJSONData.executeOnSameThread("sun,beach,moon");
        getFlickrJSONData.execute("sun,beach,moon");
        Log.d(TAG, "onResume: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(TAG, "onCreateOptionsMenu() returned: " + true);
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
        Log.d(TAG, "onOptionsItemSelected() returned: " + true);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDataAvailable(ArrayList<Photo> photo, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: starts ");
        if(status == DownloadStatus.OK){
//            Log.d(TAG, "onDataAvailable: data is"+ photo);

            flickrRecyclerViewAdapter.loadNewData(photo);

/*            FlickrRecyclerViewAdapter adapter = new FlickrRecyclerViewAdapter(MainActivity.this , photo);
            recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
            recyclerView.setAdapter(adapter);*/
        }else {
            //download or processing failed
            Log.e(TAG, "onDataAvailable: failed with status "+status );
        }
        Log.d(TAG, "onDataAvailable: ends");
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: in");
        Toast.makeText(MainActivity.this,"Item is clicked",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, "onItemLongClick: in");
        Toast.makeText(MainActivity.this,"Item is long clicked",Toast.LENGTH_LONG).show();
    }
}
