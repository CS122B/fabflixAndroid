package edu.uci.ics.fabflixmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GreenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green);


        Button mEmailSignInButton = (Button) findViewById(R.id.search_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMovies();
            }
        });
    }

    public void searchMovies(){
        String query = ((EditText)findViewById(R.id.search)).getText().toString();
        Intent goToIntent = new Intent(this, RedActivity.class);
        goToIntent.putExtra("query", query);

        startActivity(goToIntent);
    }

}
