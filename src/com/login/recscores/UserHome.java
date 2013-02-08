package com.login.recscores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author saket
 *
 */
public class UserHome extends Activity implements OnClickListener {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	Button mCreateTeam;
	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loggedin);
		mCreateTeam = (Button)findViewById(R.id.create_team);
		mCreateTeam.setOnClickListener(this);
		
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		Intent i = new Intent(UserHome.this, CreateTeam.class);
		startActivity(i);
		/*finish();*/
		
	}
}