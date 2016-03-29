package com.rage.clamber.AsyncTasks;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rage on 3/18/16.
 */
public class LoginAsyncTask extends AsyncTask<String, Integer, JSONObject> {

    public static final String TAG = LoginAsyncTask.class.getSimpleName();

    public LoginAsyncTask(existingUserLoginInterface listener) {
        userListener = listener;
    }

    public interface existingUserLoginInterface {
        void onUserNameRetrieved(@Nullable JSONObject jsonObject);
    }

    private final existingUserLoginInterface userListener;

    @Override
    protected JSONObject doInBackground(String... params) {

        StringBuilder builder = new StringBuilder();
        JSONObject json = null;

        if (params.length == 0) {
            return null;
        }

        String userName = params[0];

        try {
            //Use ip address/port
            URL url = new URL("http://192.168.0.103:8080/user" + userName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Set up OutputStream and what will be passed into it.
//            connection.setDoOutput(true);
//            connection.setRequestProperty("Content-Type", "application/json");
//            OutputStream body = new BufferedOutputStream(connection.getOutputStream());
//
//            JSONObject bodyObj = new JSONObject();
//            bodyObj.put("username", userName);
//
//            Writer writer = new OutputStreamWriter(body);
//            writer.write(bodyObj.toString());
//            writer.close();

            InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStream);
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);

                if (isCancelled()) {
                    return null;
                }

            }

            json = new JSONObject(builder.toString());


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        Log.d("tag", userName);

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {

        if (jsonObject == null) {
            Log.d(TAG, "json is null");
            userListener.onUserNameRetrieved(null);
        }
        else {
            userListener.onUserNameRetrieved(jsonObject);
        }
    }
}
