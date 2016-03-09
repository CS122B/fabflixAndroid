package edu.uci.ics.fabflixmobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RedActivity extends ActionBarActivity {

    ListView movieListView;
    Context that;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red);


        movieListView = (ListView) findViewById(R.id.movieListView);
        that = this;

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String query = bundle.getString("query");
            getMovieList(query);
        }
    }

    public void getMovieList(String query) {


        // no user is logged in, so we must connect to the server
        RequestQueue queue = Volley.newRequestQueue(this);

        final Map<String, String> params = new HashMap<String, String>();

        final Context context = this;
        String url = "http://54.200.63.169/fabflix/servlet/autocompleteSearch/" + query;


        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<String> values = new ArrayList<String>();

                            JSONObject reader = new JSONObject(response);
                            JSONArray suggestions = reader.getJSONArray("suggestions");
                            for (int i = 0; i < suggestions.length(); ++i) {
                                JSONObject movie = suggestions.getJSONObject(i);

                                String title = movie.optString("title", "NO MOVIE NAME");

//                                Log.d("title", title);
                                values.add(title);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(that, android.R.layout.simple_list_item_1, android.R.id.text1, values);
                            movieListView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        Log.d("response", response);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(postRequest);


        return ;
    }

}
