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
 * Async Task corresponding to the Login button on the Login Activity. Checks the clamber server
 * to see if the user exists.
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

            URL url = new URL("http://192.168.0.103:8080/user/" + userName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStream);
            String line;

            while (true) {

                line = reader.readLine();
                if (line == null) {
                    break;
                }
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
