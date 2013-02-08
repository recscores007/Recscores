package com.login.recscores;


import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
//import android.content.Context;
import android.content.Intent;
//import android.database.Cursor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("Registered")
public class DatabaseActivity extends Activity implements OnClickListener {
    
    // Progress Dialog
    private ProgressDialog pDialog;
    
    // url to create new product
 private static String url_create_product = "http://10.0.2.2/api/login";
 
    // JSON Node names
   private static final String TAG_SUCCESS = "success";
   
   JSONParser jsonParser = new JSONParser();
   String urlParameters ;
   
	Button mUser;
	Button mRegisterUser;
	
	EditText mEmail;
	EditText mPassword;
	DbHelper db ;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DbHelper(this);
        mUser = (Button)findViewById(R.id.sign_in_button);
        mRegisterUser = (Button)findViewById(R.id.register);
        mUser.setOnClickListener(this);
        mRegisterUser.setOnClickListener(this);
        

   
    }
    
	public void onClick(View v) {
	
		switch(v.getId()){
		
		case R.id.sign_in_button:
			mEmail = (EditText)findViewById(R.id.email);
			mPassword = (EditText)findViewById(R.id.password);
			
			String email = mEmail.getText().toString();
			String pass = mPassword.getText().toString();
			
			if(email.equals("") || email == null){
				Toast.makeText(getApplicationContext(), "email Empty", Toast.LENGTH_SHORT).show();
			}else if(pass.equals("") || pass == null){
				Toast.makeText(getApplicationContext(), "Password Empty", Toast.LENGTH_SHORT).show();
			}else{
				new getUser().execute();
				
			}
			break;
			
	        case R.id.register:
	        System.out.println("In Valid");
	        Intent i_register = new Intent(DatabaseActivity.this, RegisterActivity.class);
			startActivity(i_register);
		    finish();
			break;

		}
	}

//	@SuppressWarnings("deprecation")

	
	 class getUser extends AsyncTask<String, String, String> {
		 
	     
		    //      Before starting background thread Show Progress Dialog
		   
		       	@Override
		        protected void onPreExecute() {
		            super.onPreExecute();
		            pDialog = new ProgressDialog(DatabaseActivity.this);
		            pDialog.setMessage("Logging in..");
		            pDialog.setIndeterminate(false);
		            pDialog.setCancelable(true);
		            pDialog.show();
		        }
		 
		   //     Creating User
		      
		         
		          protected String doInBackground(String... args) {
		        	  
		      
		    		String email = mEmail.getText().toString();
		    		String password = mPassword.getText().toString();
		    		
		    		  StringEntity params = null;
		              JSONObject jobj = new JSONObject();
		              try {
						jobj.put("email", email);
						 jobj.put("password", password);
			         
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
//		            List<NameValuePair> params = new ArrayList<NameValuePair>();
//		            params.add(new BasicNameValuePair("email", email));
//		            params.add(new BasicNameValuePair("password", password));
//		            params.add(new BasicNameValuePair("fname", fname));
		           
		 
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
	
