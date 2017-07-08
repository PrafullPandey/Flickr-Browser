package p2_vaio.flickrbrowser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p2_vaio on 7/8/2017.
 */

class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrImageViewHolder>{

    private static final String TAG = "FlickrRecyclerViewAdapt";
    private List<Photo> list;
    private Context context ;

    public FlickrRecyclerViewAdapter(Context context,ArrayList<Photo> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //called by layout manager when it needs a new view
        Log.d(TAG, "onCreateViewHolder: in");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse,parent,false);
        return new FlickrImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlickrImageViewHolder holder, int position) {
        //called by LayoutManager when it wants new data in an exixting row
//        Photo currentphoto = list.get(position);

        holder.title.setText(list.get(position).getTitle());
        Picasso.with(context).load(list.get(position).getImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: in");
        Log.d(TAG, "getItemCount: out");
        return ((list!=null)&&(list.size()!=0)?list.size():0);
    }

    public void loadNewData(List<Photo> newPhoto){
        list = newPhoto;
        notifyDataSetChanged();
    }
    public Photo getPhoto(int position){
        return((list!=null)&&(list.size()!=0)?list.get(position):null);
    }

    static class FlickrImageViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "FlickrImageViewHolder";
        ImageView thumbnail = null;
        TextView title = null ;

        public FlickrImageViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "FlickrImageViewHolder: in");
            this.thumbnail = (ImageView)itemView.findViewById(R.id.thumbnail);
            this.title = (TextView)itemView.findViewById(R.id.title);
            Log.d(TAG, "FlickrImageViewHolder: out");
        }

    }
}
