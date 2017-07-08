package p2_vaio.flickrbrowser;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by p2_vaio on 7/8/2017.
 */

public class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerItemClickListen";

    interface onRecyclerClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private final onRecyclerClickListener listener ;
    private final GestureDetectorCompat gestureDetectorCompat;

    public RecyclerItemClickListener(Context context,final RecyclerView recyclerView,onRecyclerClickListener listener) {
        this.listener = listener;
        gestureDetectorCompat=null;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: starts");

        return super.onInterceptTouchEvent(rv, e);
    }
}
