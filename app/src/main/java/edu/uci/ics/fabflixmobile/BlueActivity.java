package edu.uci.ics.fabflixmobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class BlueActivity extends ActionBarActivity {
    private EditText mEmailView;
    private EditText mPasswordView;

    private int mStatusCode = 0;

    Context that;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue);

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        that = this;
    }



//    public void attemptLogin(){
//
//        Intent goToIntent = new Intent(this, RedActivity.class);
//
//        goToIntent.putExtra("last_activity", "blue");
//
//        startActivity(goToIntent);
//    }


    public void attemptLogin(){
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        final Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        params.put("isMobileLogin", "true");


        // no user is logged in, so we must connect to the server
        RequestQueue queue = Volley.newRequestQueue(this);

        final Context context = this;
        String url = "http://54.200.63.169/fabflix/servlet/loginAuthentication";


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.i("email", String.valueOf(email));
                        Log.i("password", String.valueOf(password));
                        Log.i("statusCode", String.valueOf(mStatusCode));

                        Log.d("response", response);
//                        ((TextView)findViewById(R.id.http_response)).setText(response);
                        Intent goToIntent = new Intent(that, GreenActivity.class);
                        startActivity(goToIntent);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        System.out.println("im not here bitches");
                        Log.d("security.error", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                if (response != null) {
                    mStatusCode = response.statusCode;
                }
                return super.parseNetworkResponse(response);
            }
        };


        // Add the request to the RequestQueue.
        queue.add(postRequest);


        return ;
    }




}
