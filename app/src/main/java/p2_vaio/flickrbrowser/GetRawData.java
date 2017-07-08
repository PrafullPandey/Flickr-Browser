package p2_vaio.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.R.attr.data;
import static android.content.ContentValues.TAG;

/**
 * Created by p2_vaio on 7/6/2017.
 */

enum DownloadStatus {
    IDLE, PROCESSING, NOT_INTIALISED, FAILED_OR_EMPTY, OK
}

public class GetRawData extends AsyncTask<String, Void, String> {
    private static final String TAG = "GetRawData";
    private DownloadStatus mDownloadStatus;
    private final OnDownloadComplete mcallback;

    interface OnDownloadComplete {
        void onDownloadComplete(String data, DownloadStatus status);
    }


    public GetRawData(OnDownloadComplete callback) {
        this.mDownloadStatus = DownloadStatus.IDLE;
        this.mcallback = callback;
    }

    public void runInSameThread(String s){
        Log.d(TAG, "runInSameThread: starts");
//        onPostExecute(doInBackground(s));
        if (mcallback != null) {
            String result = doInBackground(s);
            mcallback.onDownloadComplete(result, mDownloadStatus);
        }
        Log.d(TAG, "runInSameThread: ends");
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: starts");
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String result ;

        if (strings == null) {
            mDownloadStatus = DownloadStatus.NOT_INTIALISED;
            return null;
        } else {
            result = downloadJSON(strings[0], connection, reader);
        }

        Log.d(TAG, "doInBackground: ends");

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: start");
//        super.onPostExecute(s);
        if (mcallback != null) {
            mcallback.onDownloadComplete(s, mDownloadStatus);
        }
        Log.d(TAG, "onPostExecute: ends");

    }

    private String downloadJSON(String urlpath, HttpURLConnection connection, BufferedReader reader) {
        Log.d(TAG, "downloadJSON: starts");
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlpath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while (null != (line = reader.readLine())) {
                result.append(line).append("\n");
            }
            mDownloadStatus = DownloadStatus.OK;

            return result.toString();

        } catch (Exception e) {
            Log.e(TAG, "doInBackground: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    Log.e(TAG, "downloadJSON: " + e.getMessage());
                }
            }
        }
        mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        Log.d(TAG, "downloadJSON: ends");
        return null;
    }


}
