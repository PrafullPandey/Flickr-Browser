package p2_vaio.flickrbrowser;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by p2_vaio on 7/6/2017.
 */

public class GetFlickrJSONData extends AsyncTask <String,Void ,String> implements GetRawData.OnDownloadComplete {

    private static final String TAG = "GetFlickrJSONData";
    private ArrayList<Photo> list = null;
    private String baseURL;
    private String language;
    private boolean matchALL;
    private boolean runningOnSameThread = false;

    private final OnDataAvailable mCallback;

    interface OnDataAvailable{
        void onDataAvailable(ArrayList<Photo> photo , DownloadStatus status);
    }

    public ArrayList<Photo> getList() {
        return list;
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: starts");
        String destinationUri = createUri(strings[0],language,matchALL);

        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(destinationUri);

        Log.d(TAG, "doInBackground: ends");
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: starts");
        if(mCallback!=null){
            mCallback.onDataAvailable(list,DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute: ends");
    }


    public GetFlickrJSONData(OnDataAvailable mCallback,String baseURL, String language, boolean matchALL) {
        this.baseURL = baseURL;
        this.language = language;
        this.matchALL = matchALL;
        this.mCallback = mCallback;
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete: starts");
            if(status==DownloadStatus.OK){
                list = new ArrayList<>();
                try{
                    JSONObject jsonData = new JSONObject(data);
                    JSONArray itemsArray = jsonData.getJSONArray("items");
                    JSONObject jsonPhoto;
                    Photo photo = null ;

                    for(int i= 0; i<itemsArray.length();i++){
                        jsonPhoto = itemsArray.getJSONObject(i);
                        photo = new Photo(jsonPhoto.getString("title")
                                ,jsonPhoto.getString("author")
                                ,jsonPhoto.getString("author_id")
                                ,jsonPhoto.getJSONObject("media").getString("m").replaceFirst("_m.","_b.")
                                ,jsonPhoto.getString("tags")
                                ,jsonPhoto.getJSONObject("media").getString("m")
                        );
                        list.add(photo);
//                        Log.d(TAG, "onDownloadComplete: "+photo.toString());

                    }
                }catch(Exception e){
                    e.printStackTrace();
                    Log.e(TAG, "onDownloadComplete: " + e.getMessage() );
                    status = DownloadStatus.FAILED_OR_EMPTY;
                }
            }
            if(runningOnSameThread && mCallback!=null){
                //inform the caller that processing is done and possibily returnin null if was an error
                mCallback.onDataAvailable(list , status);
            }
        Log.d(TAG, "onDownloadComplete: ends");
    }
    void executeOnSameThread(String searchCriteria){
        Log.d(TAG, "executeOnSameThread: starts");
        runningOnSameThread =true;
        String destinationUri = createUri(searchCriteria,language,matchALL);

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUri);
        Log.d(TAG, "executeOnSameThread: ends");
    }

    private String createUri(String searchCriteria , String language , Boolean matchall){
        Log.d(TAG, "createUri: starts");

        String url = Uri.parse(baseURL).buildUpon()
                .appendQueryParameter("tags",searchCriteria)
                .appendQueryParameter("lang",language)
                .appendQueryParameter("format","json")
                .appendQueryParameter("nojsoncallback","1")
                .appendQueryParameter("tagmode",matchall?"ALL":"ANY")
                .build().toString();
        return url;

    }
}
