package com.login.recscores;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*public class DbHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "recscores.db";
	private static final int DATABASE_VERSION = 1;
    public static final String RECSCORES_TABLE_NAME = "users";
	private static final String RECSCORES_TABLE_CREATE =
	                "CREATE TABLE " + RECSCORES_TABLE_NAME + "(" +
	                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
	                "username VARCHAR NOT NULL, password VARCHAR NOT NULL, email VARCHAR NOT NULL);";
	private static final String RECSCORES_DB_USER = "INSERT INTO "+RECSCORES_TABLE_NAME+"values('1', 'vikas', 'asd', 'v@v.com');";


	public DbHelper(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		System.out.println("In constructor");

	}

	 (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 
	@Override
	public void onCreate(SQLiteDatabase db) {

		try{
			//Create Database
			db.execSQL(RECSCORES_TABLE_CREATE);

			//create admin account
			//db.execSQL(RECSCORES_DB_USER);
			db.execSQL(RECSCORES_DB_USER);
			System.out.println("In onCreate");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	 (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {


	}

}
*/

public class DbHelper {

	private static final String DATABASE_NAME = "recscores.db";
	private static final String TAG = "DbHelper";
	private static final int DATABASE_VERSION = 1;
	
	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	START USER TABLE
	++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
	public static final String KEY_EMAIL = "email";
	public static final String KEY_USERS_ROWID = "users_id";
	public static final String KEY_USERNAME = "username";
	public static final String KEY_PASSWORD = "password";	
    public static final String RECSCORES_TABLE_NAME = "users";
    
	private static final String RECSCORES_TABLE_CREATE =
	                "CREATE TABLE " + RECSCORES_TABLE_NAME + "(" +
	                "users_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
	                "username varchar NOT NULL, password varchar NOT NULL, email varchar NOT NULL);";
	
	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	END USER TABLE
	++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
	
	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	START TEAMS TABLE
	++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
	
	public static final String KEY_TEAMS_ROWID = "teams_id";
	public static final String KEY_TEAMNAME= "teamname";
	public static final String KEY_TEAMCITY = "teamcity";
	public static final String KEY_SPORT = "sport";	
    public static final String RECSCORES_TEAM_TABLE_NAME = "teams";
    
	private static final String RECSCORES_TEAM_TABLE_CREATE =
	                "CREATE TABLE " + RECSCORES_TEAM_TABLE_NAME + "(" +
	                "teams_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
	                "teamname varchar NOT NULL, teamcity varchar NOT NULL, sport varchar NOT NULL);";
	
	
	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	END TEAMS TABLE
	++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
	
	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	START ROSTER TABLE
	++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
	
	public static final String KEY_ROSTER_ROWID = "roster_id";
	public static final String KEY_TEAMSID_FK= "teamsidfk";
	public static final String KEY_USERSID_FK = "usersidfk";
	public static final String RECSCORES_ROSTER_TABLE_NAME = "roster";
    
	private static final String RECSCORES_ROSTER_TABLE_CREATE =
	                "CREATE TABLE " + RECSCORES_ROSTER_TABLE_NAME + "(" +
	                "roster_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
	                "teamsidfk INTEGER NOT NULL, usersidfk INTEGER NOT NULL);";
	
	
	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	END ROSTER TABLE
	++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
	
	
private final Context context;
public DatabaseHelper DBHelper;
private SQLiteDatabase db;

public DbHelper(Context ctx)
{
this.context = ctx;
DBHelper = new DatabaseHelper(context);
}

static class DatabaseHelper extends SQLiteOpenHelper
{
DatabaseHelper(Context context)
{
super(context, DATABASE_NAME, null, DATABASE_VERSION);
}

@Override
public void onCreate(SQLiteDatabase db)
{
db.execSQL(RECSCORES_TABLE_CREATE);
db.execSQL(RECSCORES_TEAM_TABLE_CREATE);
db.execSQL(RECSCORES_ROSTER_TABLE_CREATE);
}

@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion,
int newVersion)
{
Log.w(TAG, "Upgrading database from version " + oldVersion
+ " to "
+ newVersion + ", which will destroy all old data");
db.execSQL("DROP TABLE IF EXISTS titles");
onCreate(db);
}
}

//---opens the database---
public DbHelper open() throws SQLException
{
db = DBHelper.getWritableDatabase();
return this;
}

//---closes the database---
public void close()
{
DBHelper.close();
}

/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
START-                      CRUD-> USER TABLE
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/



//---insert a User into the database---
public long addUser( String username, String email, String password)
{
ContentValues initialValues = new ContentValues();

initialValues.put(KEY_USERNAME, username);
initialValues.put(KEY_PASSWORD, password);
initialValues.put(KEY_EMAIL, email);
return db.insert(RECSCORES_TABLE_NAME, null, initialValues);
}

//---deletes a particular User---
public boolean deleteUser(long rowId)
{
return db.delete(RECSCORES_TABLE_NAME, KEY_USERS_ROWID +
"=" + rowId, null) > 0;
}

//---retrieves all the Users---
public Cursor getAllUsers()
{
return db.query(RECSCORES_TABLE_NAME, new String[] {
KEY_USERS_ROWID,
KEY_USERNAME,
KEY_PASSWORD,
KEY_EMAIL},
null,null,null,null,null);
}

//---retrieves a particular User---
public Cursor getUser(String email, String password) throws SQLException
{
Cursor mCursor =db.query(RECSCORES_TABLE_NAME, new String[] {
		KEY_EMAIL,
		KEY_PASSWORD
		}, KEY_EMAIL +"=?" +" AND " + KEY_PASSWORD +"=?",
        new String[] { email, password },
        null,
        null,
        null); 

if (mCursor != null) {
mCursor.moveToFirst();
}
return mCursor;
}

//---updates a User---
public boolean updateUser(long rowId, String isbn,
String User, String publisher)
{
ContentValues args = new ContentValues();
args.put(KEY_USERNAME, isbn);
args.put(KEY_PASSWORD, User);
args.put(KEY_EMAIL, publisher);
return db.update(RECSCORES_TABLE_NAME, args,
KEY_USERS_ROWID + "=" + rowId, null) > 0;
}


/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
END                     CRUD-> USER TABLE
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/


/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
START                      CRUD-> TEAMS TABLE
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

public long addTeam( String teamname, String teamcity, String selectedsport)
{
ContentValues initialValues = new ContentValues();

initialValues.put(KEY_TEAMNAME, teamname);
initialValues.put(KEY_TEAMCITY, teamcity);
initialValues.put(KEY_SPORT, selectedsport);
return db.insert(RECSCORES_TEAM_TABLE_NAME, null, initialValues);
}



/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
END                       CRUD-> USER TABLE
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
}