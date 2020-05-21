package edu.uci.ics.fabflixmobile;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Search extends ActionBarActivity {

    private EditText movieTitle;
    private TextView message;
    private Button searchButton;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // upon creation, inflate and initialize the layout
        setContentView(R.layout.search);
        movieTitle = findViewById(R.id.movieTitle);
        message = findViewById(R.id.message);
        searchButton = findViewById(R.id.searchButton);
        /**
         * In Android, localhost is the address of the device or the emulator.
         * To connect to your machine, you need to use the below IP address
         * **/
//        url = "https://192.168.0.106:8443/fabflix/api/";
        url = "https://71.69.162.72:47373/fabflix/api/";

        //assign a listener to call a function to handle the user request when clicking a button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
    }

    public void search() {

        message.setText("Searching...");
        // Use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        //request type is POST
        final StringRequest searchRequest = new StringRequest(Request.Method.GET, url + "home", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //TODO should parse the json response to redirect to appropriate functions.
                ArrayList<Movie> movies = new ArrayList<>();
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    if(jsonArray != null){
                        Log.d("search.success", response);
                        //initialize the activity(page)/destination
                        Intent listPage = new Intent(Search.this, ListViewActivity.class);
                        listPage.putExtra("message", response);
                        //without starting the activity/page, nothing would happen
                        startActivity(listPage);
//                        //initialize the activity(page)/destination
//                        Intent listPage = new Intent(Search.this, ListViewActivity.class);
//                        //without starting the activity/page, nothing would happen
//                        startActivity(listPage);
                    }else{
                        Log.d("login.failed", response);
                        message.setText("Movie not found, please try again");
                    }
                }catch(JSONException e){
                    Log.e("MYAPP", "unexpected JSON exception", e);
                }
//                Log.d("login.success", response);
//                //initialize the activity(page)/destination
//                Intent listPage = new Intent(Login.this, ListViewActivity.class);
//                //without starting the activity/page, nothing would happen
//                startActivity(listPage);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("login.error", error.toString());
                    }
                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                // Post request form data
//                final Map<String, String> params = new HashMap<>();
//                params.put("email", username.getText().toString());
//                params.put("password", password.getText().toString());
//
//                return params;
//            }
        };

        // !important: queue.add is where the login request is actually sent
        queue.add(searchRequest);

    }
}