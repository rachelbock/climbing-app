package com.rage.clamber.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.rage.clamber.Data.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Async Task to correspond to New User Button in Login Activity. Checks the clamber server to see
 * if a user exists and if not it posts the new user information to the clamber database.
 */
public class NewUserAsyncTask extends AsyncTask<User, Integer, NewUserAsyncTask.CreateUserResult> {

    public static final String TAG = NewUserAsyncTask.class.getSimpleName();

    public enum CreateUserResult {
        SUCCESS,
        USER_ALREADY_EXISTS,
        CONNECTION_FAILURE
    }

    public NewUserAsyncTask(newUserLoginInterface listener) {
        newUserlistener = listener;
    }

    public interface newUserLoginInterface {
        void onNewUserSubmittedListener(CreateUserResult result);
    }

    private final newUserLoginInterface newUserlistener;

    @Override
    protected CreateUserResult doInBackground(User... params) {

        StringBuilder builder = new StringBuilder();
        JSONObject json = null;

        if (params.length == 0) {
            return CreateUserResult.CONNECTION_FAILURE;
        }

        User user = params[0];
        try {
            //Use ip address/port
            URL url = new URL("http://192.168.0.105:8080/user/" + user.getName());
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
                    return CreateUserResult.CONNECTION_FAILURE;
                }
            }

            json = new JSONObject(builder.toString());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        try {
            if (json != null && json.getString("userName").equals(user.getName())) {
                return CreateUserResult.USER_ALREADY_EXISTS;
            } else {
                URL url = new URL("http://192.168.0.105:8080/user");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                //Set up OutputStream and what will be passed into it.
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                OutputStream body = new BufferedOutputStream(connection.getOutputStream());

                JSONObject bodyObj = new JSONObject();
                bodyObj.put("username", user.getName());
                bodyObj.put("height", user.getHeight());
                bodyObj.put("skill", user.getSkillLevel());

                Writer writer = new OutputStreamWriter(body);
                Log.d(TAG, bodyObj.toString());
                writer.write(bodyObj.toString());
                writer.close();


                InputStreamReader iStream = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(iStream);
                String text;

                while (true) {

                    text = bufferedReader.readLine();
                    if (text == null) {
                        break;
                    }
                    builder.append(text);
                    if (isCancelled()) {
                        return CreateUserResult.CONNECTION_FAILURE;
                    }
                }
                return CreateUserResult.SUCCESS;
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, user.getName());


        return CreateUserResult.CONNECTION_FAILURE;

    }

    @Override
    protected void onPostExecute(CreateUserResult result) {
        newUserlistener.onNewUserSubmittedListener(result);
    }
}
