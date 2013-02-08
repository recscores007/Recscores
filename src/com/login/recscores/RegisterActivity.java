package com.login.recscores;
import java.io.UnsupportedEncodingException; 
/*import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;

import org.apache.http.message.BasicNameValuePair;*/
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.entity.StringEntity;
//import com.example.androidhive.DbHelper;
 
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
public class RegisterActivity extends Activity {
 

    String urlParameters ;
    EditText mUsername;
	EditText mPassword;
	EditText mEmail;
	Button mSignUp ;
	TextView temp;
	
	JSONParser jsonParser = new JSONParser();
	
    // Progress Dialog
    private ProgressDialog pDialog;
    
    // url to create new product
    private static String url_create_product = "http://10.0.2.2/api/register";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
       
        
 
        // Edit Text
    	
		mUsername = (EditText)findViewById(R.id.name);
		mEmail = (EditText)findViewById(R.id.email);
		mPassword = (EditText)findViewById(R.id.password);
		
 
        // Create button
        Button mSignUp = (Button) findViewById(R.id.sign_up_button);
 
        // button click event
        mSignUp.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                // creating new product in background thread
                new CreateNewUser().execute();
            }
        });
    }
 
    /**
     * Background Async Task to Create new user
     * */
    
    
    class CreateNewUser extends AsyncTask<String, String, String> {
 
     
    //      Before starting background thread Show Progress Dialog
   
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Signing Up..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
   //     Creating User
      
         
          protected String doInBackground(String... args) {
        	String fname = mUsername.getText().toString();
    		String email = mEmail.getText().toString();
    		String password = mPassword.getText().toString();
    		
    		  StringEntity params = null;
              JSONObject jobj = new JSONObject();
              try {
				jobj.put("email", email);
				 jobj.put("password", password);
	              jobj.put("fname", fname);
	              params = new StringEntity(jobj.toString());
	              Log.d("jobj", jobj.toString());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
 
            // Building Parameters
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("email", email));
//            params.add(new BasicNameValuePair("password", password));
//            params.add(new BasicNameValuePair("fname", fname));
           
 
            // getting JSON Object
            // Note that create user url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);
            
           // check log cat from response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // successfully created USER
                    Intent i = new Intent(getApplicationContext(), UserHome.class);
                    startActivity(i);
 
                    // closing this screen
                   // finish();
                } else {
                    // failed to create USER
                	 // Log.d("No Sign Up.", json.toString());
                }
            } catch(JSONException e){
               // Log.e("log_tag", "Error parsing data "+e.toString());
               // Log.e("log_tag", "Failed data was:\n" + TAG_SUCCESS);
            }
 
            return null;
        }
 
        // After completing background task Dismiss the progress dialog
       
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }
 
    }
}