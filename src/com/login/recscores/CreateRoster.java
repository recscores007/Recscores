package com.login.recscores;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.ArrayList;  
import java.util.HashMap;
import java.util.Map;


public class CreateRoster extends ListActivity {
	
	/*EditText mTxtPhoneNo;*/
	final Context context = this;
	 protected Object mActionMode;
	  public int selectedItem = -1;
	  
	  private ArrayList<Map<String, String>> mPeopleList;
		private SimpleAdapter mAdapter;
		private AutoCompleteTextView mTxtPhoneNo;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
    ArrayAdapter<String> adapter;

    //RECORDING HOW MUCH TIMES BUTTON WAS CLICKED
    int clickCounter=0;
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.createroster);
        
        mTxtPhoneNo = (AutoCompleteTextView) findViewById(R.id.fln);
        mPeopleList = new ArrayList<Map<String, String>>();
	    PopulatePeopleList();
	   
	    mAdapter = new SimpleAdapter(this, mPeopleList, R.layout.create_roster_autocomplete,
	            new String[] { "Name", "Phone", "Type"/*, "Email"*/ }, new int[] {
	                    R.id.ccontName, R.id.ccontNo, R.id.ccontType/*, R.id.ccontEmail */});
	    mTxtPhoneNo.setAdapter(mAdapter);
	    mTxtPhoneNo.setOnItemClickListener(new OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> av, View arg1, int index,
	                long arg3) {
	            @SuppressWarnings("unchecked")
				Map<String, String> map = (Map<String, String>) av.getItemAtPosition(index);

	            String name  = map.get("Name");
	            String number = map.get("Phone");
	            String type = map.get("Type");
	            mTxtPhoneNo.setText(""+name+"\n"+number+"\n"+type);

	        }



	    });
        
        adapter=new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1,
            listItems);
        setListAdapter(adapter);
        getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                int position, long id) {

              if (mActionMode != null) {
                return false;
              }
              selectedItem = position;

              // Start the CAB using the ActionMode.Callback defined above
              mActionMode = CreateRoster.this
                  .startActionMode(mActionModeCallback);
              view.setSelected(true);
              return true;
            }
          });
        
    }
    
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
          // Inflate a menu resource providing context menu items
          MenuInflater inflater = mode.getMenuInflater();
          // Assumes that you have "contexual.xml" menu resources
          inflater.inflate(R.menu.rowselection, menu);
          return true;
        }

        // Called each time the action mode is shown. Always called after
        // onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
          return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
          switch (item.getItemId()) {
          case R.id.Edit:
        	  
        	  editplayer(selectedItem);
           
        	  show();
            // Action picked, so close the CAB
            mode.finish();
            return true;
          
          case R.id.delete:
        	  listItems.remove(selectedItem);
        	  adapter.notifyDataSetChanged();
              // Action picked, so close the CAB
              mode.finish();
              return true;
              
            default:
              return false;
          }
        }

        // Called when the user exits the action mode
        public void onDestroyActionMode(ActionMode mode) {
          mActionMode = null;
          selectedItem = -1;
        }
      };
      
      private void show() {
    	    Toast.makeText(CreateRoster.this,
    	        String.valueOf(selectedItem), Toast.LENGTH_LONG).show();
    	  }
      
      private void editplayer(final int selectedItem){

    	  
    	  Object o = adapter.getItem(selectedItem);
			  String keyword = o.toString();
    	 
			  AlertDialog.Builder alert = new AlertDialog.Builder(this);

			  alert.setTitle("Edit Player Info");
			  
			  // Set an EditText view to get user input 
			  final EditText input = new EditText(this);
			  input.setText(keyword);
			  alert.setView(input);

			  alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int whichButton) {
				  
				  String value = input.getText().toString();
				  listItems.remove(selectedItem);
				  listItems.add(selectedItem,value);
			        adapter.notifyDataSetChanged();
			    // Do something with value!
			    }
			  });

			  alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int whichButton) {
			      // Canceled.
			    }
			  });

			  alert.show();
      }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) {
    	if(mTxtPhoneNo.getText().toString().equals(""))
    	{
    		Toast.makeText(this, "Enter Player Name", Toast.LENGTH_LONG).show();
       
    	}
    	else
    	{
    	listItems.add(0,mTxtPhoneNo.getText().toString());
        adapter.notifyDataSetChanged();
        mTxtPhoneNo.setText("");
        
        }
        
    }
    
    //**********AUTOCOMPLETE
	@SuppressWarnings("deprecation")
	public void PopulatePeopleList() {
	    mPeopleList.clear();
	    
	   
	   /* startManagingCursor(emailCursor);*/
	    /*mTxtPhoneNo.setAdapter(new SimpleCursorAdapter(this, android.R.layout.simple_dropdown_item_1line, emailCursor, new String[] {Email.DATA1}, new int[] {android.R.id.text1}));
	    mTxtPhoneNo.setThreshold(0);*/
	/*    emailCursor.moveToPosition();*/
//	    Cursor emailCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, null, null, null);
	    Cursor people = getContentResolver().query(
	            ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
	    while (people.moveToNext()/*&& emailCursor.moveToNext()*/) {
	       
	    		    
	    /*	String contactEmail = emailCursor.getString(emailCursor
	                .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA1)); */
	    	
	    	
	    	String contactName = people.getString(people
	                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	      
	        String contactId = people.getString(people
	                .getColumnIndex(ContactsContract.Contacts._ID));
	        String hasPhone = people
	                .getString(people
	                        .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

	        if ((Integer.parseInt(hasPhone) > 0)){
	            // You know have the number so now query it like this
	            Cursor phones = getContentResolver().query(
	            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
	            null,
	            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,
	            null, null);
	            while (phones.moveToNext()){
	                //store numbers and display a dialog letting the user select which.
	                String phoneNumber = phones.getString(
	                phones.getColumnIndex(
	                ContactsContract.CommonDataKinds.Phone.NUMBER));
	                String numberType = phones.getString(phones.getColumnIndex(
	                ContactsContract.CommonDataKinds.Phone.TYPE));
	                Map<String, String> NamePhoneType = new HashMap<String, String>();
	                NamePhoneType.put("Name", contactName);
	               /* NamePhoneType.put("Email", contactEmail);*/
	                NamePhoneType.put("Phone", phoneNumber);
	                if(numberType.equals("0"))
	                    NamePhoneType.put("Type", "Work");
	                    else
	                    if(numberType.equals("1"))
	                    NamePhoneType.put("Type", "Home");
	                    else if(numberType.equals("2"))
	                    NamePhoneType.put("Type",  "Mobile");
	                    else
	                    NamePhoneType.put("Type", "Other");
	                    //Then add this map to the list.
	                    mPeopleList.add(NamePhoneType);
	            }
	            phones.close();
	        }
	    }
	    people.close();
	    startManagingCursor(people);
	}

//	public void onItemClick(AdapterView<?> av, View v, int index, long arg){
//	    Map<String, String> map = (Map<String, String>) av.getItemAtPosition(index);
//	    Iterator<String> myVeryOwnIterator = map.keySet().iterator();
//	    while(myVeryOwnIterator.hasNext()) {
//	        String key=(String)myVeryOwnIterator.next();
//	        String value=(String)map.get(key);
//	        mTxtPhoneNo.setText(value);
//	    }
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.createroster, menu);
	    return true;
	}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
//	**************************************************************************************
	
    /*
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.createroster);
	    mPeopleList = new ArrayList<Map<String, String>>();
	    PopulatePeopleList();
	    mTxtPhoneNo = (AutoCompleteTextView) findViewById(R.id.fln);
	    mAdapter = new SimpleAdapter(this, mPeopleList, R.layout.create_roster_autocomplete,
	            new String[] { "Name", "Phone", "Type" }, new int[] {
	                    R.id.ccontName, R.id.ccontNo, R.id.ccontType });
	    mTxtPhoneNo.setAdapter(mAdapter);

	}

	@SuppressWarnings("deprecation")
	public void PopulatePeopleList() {
	    mPeopleList.clear();
	    Cursor people = getContentResolver().query(
	            ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
	    while (people.moveToNext()) {
	        String contactName = people.getString(people
	                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	        String contactId = people.getString(people
	                .getColumnIndex(ContactsContract.Contacts._ID));
	        String hasPhone = people
	                .getString(people
	                        .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

	        if ((Integer.parseInt(hasPhone) > 0)){
	            // You know have the number so now query it like this
	            Cursor phones = getContentResolver().query(
	            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
	            null,
	            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,
	            null, null);
	            while (phones.moveToNext()){
	                //store numbers and display a dialog letting the user select which.
	                String phoneNumber = phones.getString(
	                phones.getColumnIndex(
	                ContactsContract.CommonDataKinds.Phone.NUMBER));
	                String numberType = phones.getString(phones.getColumnIndex(
	                ContactsContract.CommonDataKinds.Phone.TYPE));
	                Map<String, String> NamePhoneType = new HashMap<String, String>();
	                NamePhoneType.put("Name", contactName);
	                NamePhoneType.put("Phone", phoneNumber);
	                if(numberType.equals("0"))
	                    NamePhoneType.put("Type", "Work");
	                    else
	                    if(numberType.equals("1"))
	                    NamePhoneType.put("Type", "Home");
	                    else if(numberType.equals("2"))
	                    NamePhoneType.put("Type",  "Mobile");
	                    else
	                    NamePhoneType.put("Type", "Other");
	                    //Then add this map to the list.
	                    mPeopleList.add(NamePhoneType);
	            }
	            phones.close();
	        }
	    }
	    people.close();
	    startManagingCursor(people);
	}

	public void onItemClick(AdapterView<?> av, View v, int index, long arg){
	    Map<String, String> map = (Map<String, String>) av.getItemAtPosition(index);
	    Iterator<String> myVeryOwnIterator = map.keySet().iterator();
	    while(myVeryOwnIterator.hasNext()) {
	        String key=(String)myVeryOwnIterator.next();
	        String value=(String)map.get(key);
	        mTxtPhoneNo.setText(value);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.createroster, menu);
	    return true;
	}
	}*/



	
	
