package com.login.recscores;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
/*import android.view.ViewGroup;
import android.database.Cursor;
import android.content.Context;*/
import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CreateTeam extends Activity implements OnClickListener {
	
	
	JSONParser jsonParser = new JSONParser();
	
    // Progress Dialog
    private ProgressDialog pDialog;
    
    // url to create new product
    private static String url_create_product = "http://10.0.2.2/api/createteam";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
   
	EditText mteamname;
	EditText mteamcity;
	RadioGroup msport;
	RadioButton msportType;
	Button mCreateRoster ;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mteamname = (EditText)findViewById(R.id.teamname);
		mteamcity = (EditText)findViewById(R.id.teamcity);
		msport=(RadioGroup)findViewById(R.id.pick_sport);
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_team_new);
		mCreateRoster = (Button)findViewById(R.id.createteam);
		mCreateRoster.setOnClickListener(this);
		
		GridView gridView = (GridView) findViewById(R.id.gridviewplayer);
		 
        // Instance of ImageAdapter Class
        gridView.setAdapter(new PlayerAdapter(this));
		
		/*super.onCreate(savedInstanceState);
		setContentView(R.layout.create_team_new);*/
		
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
switch(v.getId()){
		
		case R.id.createteam:
			mteamname = (EditText)findViewById(R.id.teamname);
			mteamcity = (EditText)findViewById(R.id.teamcity);
			msport=(RadioGroup)findViewById(R.id.pick_sport);
			
			    int id= msport.getCheckedRadioButtonId();
			    msportType = (RadioButton) findViewById(id);
		        String selectedsport=  msportType.getText().toString();
			    Toast.makeText(getApplicationContext(), "Custom", Toast.LENGTH_SHORT).show();	
			
			
			String teamname = mteamname.getText().toString();
			String teamcity = mteamcity.getText().toString();
			
			if(teamname.equals("") || teamname == null){
				Toast.makeText(getApplicationContext(), "teamname Empty", Toast.LENGTH_SHORT).show();
			}else if(teamcity.equals("") || teamcity == null){
				Toast.makeText(getApplicationContext(), "teamcity Empty", Toast.LENGTH_SHORT).show();
			}else{
				
					new AddTeam().execute();
			}
			
			break;
		}
	}
	
	 class AddTeam extends AsyncTask<String, String, Integer> {
		 
		    //      Before starting background thread Show Progress Dialog
		   
		        protected void onPreExecute() {
		            super.onPreExecute();
		            pDialog = new ProgressDialog(CreateTeam.this);
		            pDialog.setMessage("Signing Up..");
		            pDialog.setIndeterminate(false);
		            pDialog.setCancelable(true);
		            pDialog.show();
		        }
		 
		   //     Creating User
		      
		         
		          protected Integer doInBackground(String... args) {
		        	  
		        	int id= msport.getCheckedRadioButtonId();
		      		msportType = (RadioButton)findViewById(id);
		      		
		        	String selectedsport=  msportType.getText().toString();
					String teamname = mteamname.getText().toString();
					String teamcity = mteamcity.getText().toString();
		    		
		    		  StringEntity params = null;
		              JSONObject jobj = new JSONObject();
		              try {
						jobj.put("teamname", teamname);
						 jobj.put("selectedsport", selectedsport);
			              jobj.put("teamcity", teamcity);
			              params = new StringEntity(jobj.toString());
			              Log.d("jobj", jobj.toString());
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
		                    "POST", params);
		            
		           // check log cat from response
		            Log.d("Create Response", json.toString());
		 
		            				int success=0;
						
							 try {
								success= json.getInt(TAG_SUCCESS);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							 return success;
		        }
		 
		        // After completing background task Dismiss the progress dialog
		       
		        protected void onPostExecute(Integer success) {
		            // dismiss the dialog once done
		            pDialog.dismiss();
		            if (success == 1) {
	                    // successfully created team
	                	// Toast.makeText(getApplicationContext(), "Team added", Toast.LENGTH_SHORT).show();	
	                    Intent i = new Intent(getApplicationContext(), CreateRoster.class);
	                    startActivity(i);
	 
	                    // closing this screen
	                   // finish();
	                } else {
	                	 Toast.makeText(getApplicationContext(), "Team existed", Toast.LENGTH_SHORT).show();	
		                    
		                    // Toast.makeText(getApplicationContext(), "Team name existed", Toast.LENGTH_SHORT).show();	
	                    // failed to create USER
	                	 // Log.d("No Sign Up.", json.toString());
	                }
		        }
		 
		    }
	
	
}


