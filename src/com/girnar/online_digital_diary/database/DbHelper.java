package com.girnar.online_digital_diary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author
 * 
 */
public class DbHelper

{
	private static String database_name = "DatabaseOnline";
	private static int database_version = 13;

	/**
	 * Reference of Context
	 */
	public static Context context;
	/**
	 * Reference of Helper class
	 */
	public static Helper helper;
	/**
	 * table name LOGIN
	 */
	public static String login = "LOGIN";
	/**
	 * table id
	 */
	public static String login_id = "login_id";
	/**
	 * first name
	 */
	public static String login_fname = "login_fname";
	/**
	 * last name
	 */
	public static String login_lname = "login_lname";
	/**
	 * email id
	 */
	public static String login_emailid = "login_emailid";
	/**
	 * password
	 */
	public static String login_password = "login_password";
	/**
	 * gender
	 */
	public static String login_gender = "login_gender";
	/**
	 * user name
	 */
	public static String login_username = "login_username";
	public static String login_question = "question";
	public static String login_answer = "answer";
	/**
	 * date of birth
	 */
	public static String login_dob = "login_dob";
	/**
	 * Table name of PERSONALINFORMATION
	 */
	public static String person = "PERSONALINFORMATION";
	/**
	 * person image
	 */
	public static String person_image = "person_image";
	/**
	 * person id
	 */
	public static String person_id = "person_id";
	/**
	 * person name
	 */
	public static String person_name = "person_name";
	/**
	 * person address
	 */
	public static String person_address = "person_address";
	/**
	 * person birthday
	 */
	public static String person_birthday = "person_birthday";
	/**
	 * person mobile_no
	 */
	public static String person_mobile_no = "person_mobile_no";
	/**
	 * person anniversary
	 */
	public static String person_aniversary = "person_aniversary";
	/**
	 * person email
	 */
	public static String person_email = "person_email";
	/**
	 * person age
	 */
	public static String person_age = "person_age";
	/**
	 * Table name of OTHERINFO
	 */
	public static String otherInfo = "OTHERINFO";

	/**
	 * account_no id
	 */
	public static String account_no_id = "account_no_id";
	/**
	 * holder's name
	 */
	public static String holers_name = "holders_name";
	/**
	 * account no
	 */
	public static String account_no = "account_no";

	/**
	 * bank's name
	 */
	public static String banks_name = "banks_name";
	/**
	 * location
	 */
	public static String location = "location";
	/**
	 * Table name of REMINDER
	 */
	public static String person_reminder = "REMINDER";
	/**
	 * reminder id
	 */
	public static String reminder_id = "reminder_id";
	/**
	 * person_reminder_title
	 */
	public static String person_reminder_title = "person_reminder_title";
	/**
	 * person_reminder_description
	 */
	public static String person_reminder_description = "person_reminder_description";
	/**
	 * person_reminder_set_date
	 */
	public static String person_reminder_set_date = "person_reminder_set_date";
	/**
	 * person_reminder_set_time
	 */
	public static String person_reminder_set_time = "person_reminder_set_time";
	/**
	 * status
	 */
	public static String status = "status";
	/**
	 * Table name of BOOKMARK
	 */
	public static String person_bookmark = "BOOKMARK";
	/**
	 * bookmark_id
	 */
	public static String bookmark_id = "bookmark_id";
	/**
	 * person_title
	 */
	public static String person_title = "person_title";
	/**
	 * person_description
	 */
	public static String person_description = "person_description";

	/**
	 * To create table LOGIN
	 */
	public static String create_table = "CREATE TABLE LOGIN(login_id integer primary key autoincrement,"
			+ "login_fname text not null,login_lname text not null,"
			+ "login_username string not null,login_password string not null,"
			+ "question string not null,answer string not null,old_user string not null,"
			+ "login_gender string not null,login_dob string not null);";
	/**
	 * To create table PERSONALINFORMATION
	 */
	public static String create_table1 = "CREATE TABLE PERSONALINFORMATION(person_id integer primary key autoincrement,"
			+ "person_name text not null,person_address text not null,"
			+ "person_birthday string not null,person_mobile_no string not null,person_aniversary string not null,"
			+ "person_email string not null,person_age string not null,person_image BLOB,user_id integer,gender string not null);";
	/**
	 * To create table OTHERINFO
	 */
	public static String create_table2 = "CREATE TABLE OTHERINFO(account_no_id integer primary key autoincrement,account_no text not null,holders_name text not null,banks_name text not null,location text not null,user_id integer);";
	/**
	 * To create table BOOKMARK
	 */
	public static String create_table3 = "CREATE TABLE BOOKMARK(bookmark_id integer primary key autoincrement,person_title text not null,person_description text not null,user_id integer);";
	/**
	 * To create table REMINDER
	 */
	public static String create_table4 = "CREATE TABLE REMINDER(reminder_id integer primary key autoincrement,person_reminder_title text not null,person_reminder_description text not null,person_reminder_set_date text not null,person_reminder_set_time text not null,status text not null,user_id integer );";
	/**
	 * reference of SQLiteDatabase
	 */
	public SQLiteDatabase db;
	/**
	 * reference of CONTEXT
	 */
	public Context context1;

	/**
	 * @param ctx
	 */
	public DbHelper(Context ctx) {
		// TODO Auto-generated constructor stub
		context = ctx;
		helper = new Helper(context);
	}

	/**
	 * @author
	 * 
	 */
	public static class Helper extends SQLiteOpenHelper {

		/**
		 * @param context
		 */
		public Helper(Context context) {
			super(context, database_name, null, database_version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated constructor stub
			db.execSQL(create_table);
			db.execSQL(create_table1);
			db.execSQL(create_table2);
			db.execSQL(create_table3);
			db.execSQL(create_table4);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

			db.execSQL("ALTER TABLE LOGIN ADD COLUMN question string not null DEFAULT test");
			db.execSQL("ALTER TABLE LOGIN ADD COLUMN answer string not null DEFAULT test");
			db.execSQL("ALTER TABLE LOGIN ADD COLUMN old_user string not null DEFAULT old");
			db.execSQL("ALTER TABLE PERSONALINFORMATION ADD COLUMN gender string not null DEFAULT 'not mensioned'");

		}

	}

	/**
	 * To open the database
	 * 
	 * @return this;
	 * @throws SQLException
	 */
	public DbHelper open() throws SQLException {
		if (helper == null) {

		}
		db = helper.getWritableDatabase();
		return this;

	}

	/**
	 * To close the database
	 */

	public void close() {
		helper.close();

	}

	/**
	 * To insert the value of Login page
	 * 
	 * @param fname
	 * @param lname
	 * @param username
	 * @param password
	 * @param gender
	 * @param dob
	 * @param answer
	 * @param question
	 * @return db.insert(login, null, cv);
	 */
	public long insertinfo(String fname, String username, String password,
			String question, String answer) {
		// TODO Auto-generated constructor stub
		ContentValues cv = new ContentValues();
		cv.put(login_fname, fname);
		cv.put(login_lname, "");
		cv.put(login_username, username);
		cv.put(login_password, password);
		cv.put(login_gender, "");
		cv.put(login_dob, "");
		cv.put(login_question, question);
		cv.put(login_answer, answer);
		cv.put("old_user", "new");

		return db.insert(login, null, cv);
	}

	public Cursor getQuestion(String userName) {
		Cursor cursor = db.query(true, login, new String[] { login_question,
				login_answer, login_id }, login_username + " = '" + userName
				+ "'", null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;

	}

	public Cursor checkOldUser(String userName) {
		Cursor cursor = db.query(true, login, new String[] { "old_user" },
				login_username + " = '" + userName + "'", null, null, null,
				null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;

	}

	public void UpdatePassword(int id, String password) {

		ContentValues cv = new ContentValues();
		cv.put(login_password, password);
		db.update(login, cv, login_id + "=" + id, null);

	}

	/**
	 * To insert the value of the person info in the database
	 * 
	 * @param name
	 * @param address
	 * @param birthday
	 * @param mobile_no
	 * @param aniversary
	 * @param email
	 * @param age
	 * @param image
	 * @param id
	 * @return db.insert(person, null, cv);
	 */

	public long insertinfoperson(String name, String address, String birthday,
			String mobile_no, String aniversary, String email, String age,
			byte[] image, int id, String gender) {
		// TODO Auto-generated constructor stub
		ContentValues cv = new ContentValues();
		cv.put(person_name, name);
		cv.put(person_address, address);
		cv.put(person_birthday, birthday);
		cv.put(person_mobile_no, mobile_no);
		cv.put(person_aniversary, aniversary);
		cv.put(person_email, email);
		cv.put(person_age, age);
		cv.put(person_image, image);

		cv.put("user_id", id);
		cv.put("gender", gender);
		return db.insert(person, null, cv);
	}

	/**
	 * To get the value of the person info and show in the ListView
	 * 
	 * @param user_id
	 * 
	 * @return cursor;
	 */
	public Cursor getInfoperson(int user_id) {
		Cursor cursor = db.query(true, person, new String[] { person_id,
				person_name, person_address, person_birthday, person_mobile_no,
				person_aniversary, person_email, person_age, person_image,
				"gender" }, "user_id = " + user_id, null, null, null, null,
				null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;

	}

	/**
	 * To get the value of the person info and show in the dialog Activity
	 * 
	 * @param id
	 * @return cursor;
	 */
	public Cursor getInfopersonMethod(int id) {
		Cursor cursor = db.query(true, person, new String[] { person_name,
				person_address, person_birthday, person_mobile_no,
				person_aniversary, person_email, person_age, person_image,
				"gender" }, person_id + "=" + id, null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;

	}

	public Cursor getDateOfPerson() {
		Cursor cursor = db.query(true, person, new String[] { person_id,
				person_birthday, person_aniversary }, null, null, null, null,
				null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	public Cursor searchInfopersonMethod(String name, int user_id) {
		Cursor cursor = db.query(true, person, new String[] { person_id,
				person_name, person_address, person_birthday, person_mobile_no,
				person_aniversary, person_email, person_age, person_image,
				"gender" }, person_name + " LIKE '%" + name + "%' AND "
				+ "user_id = " + user_id, null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	/**
	 * Update the value of the person info in the database
	 * 
	 * @param id
	 * @param name
	 * @param address
	 * @param birthday
	 * @param mobile_no
	 * @param aniversary
	 * @param email
	 * @param age
	 * @param image
	 */
	public long UpdateMethod(int id, String name, String address,
			String birthday, String mobile_no, String aniversary, String email,
			String age, byte[] image, String gender) {

		ContentValues cv = new ContentValues();
		cv.put(person_name, name);
		cv.put(person_address, address);
		cv.put(person_birthday, birthday);
		cv.put(person_mobile_no, mobile_no);
		cv.put(person_aniversary, aniversary);
		cv.put(person_email, email);
		cv.put(person_age, age);
		cv.put(person_image, image);
		cv.put("gender", gender);

		return db.update(person, cv, person_id + "=" + id, null);

	}

	public void UpdateBankAccountInfo(int change_id, String name,
			String account, String bname, String loc, int id) {

		ContentValues cv = new ContentValues();
		cv.put(holers_name, name);
		cv.put(account_no, account);
		cv.put(banks_name, bname);
		cv.put(location, loc);
		cv.put("user_id", id);
		db.update(otherInfo, cv, account_no_id + "=" + change_id, null);

	}

	/**
	 * Delete the data from the database and also in the ListView
	 * 
	 * @param id
	 * @param table_name
	 * @param table_id
	 */
	public void deleteMethod(int id, String table_name, String table_id) {

		db.delete(table_name, table_id + "=" + id, null);
	}

	/**
	 * To insert the value in the database
	 * 
	 * @param name
	 * 
	 * @param account
	 * @param bname
	 * @param loc
	 * @param id
	 * @return db.insert(otherInfo, null, cv);
	 */
	public long insertinfoOther(String name, String account, String bname,
			String loc, int id) {
		// TODO Auto-generated constructor stub

		ContentValues cv = new ContentValues();
		cv.put(holers_name, name);
		cv.put(account_no, account);
		cv.put(banks_name, bname);
		cv.put(location, loc);
		cv.put("user_id", id);

		return db.insert(otherInfo, null, cv);
	}

	/**
	 * In this method we get the value from the database
	 * 
	 * @param user_id
	 * 
	 * @return cursor;
	 */
	public Cursor getInfoOther(int user_id) {
		Cursor cursor = db.query(true, otherInfo, new String[] { account_no_id,
				holers_name, account_no, banks_name, location }, "user_id = "
				+ user_id, null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;

	}

	/**
	 * In this method we get the value from the database
	 * 
	 * @param user_id
	 * 
	 * @return cursor;
	 */
	public Cursor searchInfoOther(String name, int user_id) {

		Cursor cursor = db.query(true, otherInfo, new String[] { account_no_id,
				holers_name, account_no, banks_name, location }, holers_name
				+ " LIKE '%" + name + "%' AND " + "user_id = " + user_id, null,
				null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;

	}

	/**
	 * To get the value of the other info and show in the dialog Activity
	 * 
	 * @param id
	 * @return cursor;
	 */
	public Cursor getInfoOtherMethod(int id) {
		Cursor cursor = db.query(true, otherInfo, new String[] { holers_name,
				account_no, banks_name, location }, account_no_id + "=" + id,
				null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;

	}

	/**
	 * To insert the value in the database
	 * 
	 * @param title
	 * @param description
	 * @param id
	 * @return db.insert(person_bookmark, null, cv);
	 */
	public long insertinfoBookmark(String title, String description, int id) {
		// TODO Auto-generated constructor stub

		ContentValues cv = new ContentValues();
		cv.put(person_title, title);
		cv.put(person_description, description);
		cv.put("user_id", id);

		return db.insert(person_bookmark, null, cv);
	}

	public void UpdateOtherInfo(int change_id, String title,
			String description, int id) {

		ContentValues cv = new ContentValues();
		cv.put(person_title, title);
		cv.put(person_description, description);
		cv.put("user_id", id);
		db.update(person_bookmark, cv, bookmark_id + "=" + change_id, null);

	}

	/**
	 * To get the value of Book mark from the database
	 * 
	 * @param user_id
	 * 
	 * @return cursor;
	 */
	public Cursor getInfoBookmark(int user_id) {
		Cursor cursor = db.query(true, person_bookmark, new String[] {
				bookmark_id, person_title, person_description }, "user_id = "
				+ user_id, null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;

	}

	public Cursor searchInfoBookmark(String name, int user_id) {
		Cursor cursor = db.query(true, person_bookmark, new String[] {
				bookmark_id, person_title, person_description }, person_title
				+ " LIKE '%" + name + "%' AND " + "user_id = " + user_id, null,
				null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;

	}

	/**
	 * To get the value of the bookmark and show in the dialog Activity
	 * 
	 * @param id
	 * @return cursor;
	 */
	public Cursor getInfoBookmarkMethod(int id) {
		Cursor cursor = db.query(true, person_bookmark, new String[] {
				person_title, person_description }, bookmark_id + "=" + id,
				null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;

	}

	/**
	 * To insert the value of reminder in the database
	 * 
	 * @param title
	 * @param description
	 * @param set_date
	 * @param set_time
	 * @param id
	 * @return db.insert(person_reminder, null, cv);
	 */
	public long insertinfoReminder(String title, String description,
			String set_date, String set_time, int id) {
		// TODO Auto-generated constructor stub
		ContentValues cv = new ContentValues();
		cv.put(person_reminder_title, title);
		cv.put(person_reminder_description, description);
		cv.put(person_reminder_set_date, set_date);
		cv.put(person_reminder_set_time, set_time);
		cv.put(status, "pending...");
		cv.put("user_id", id);
		return db.insert(person_reminder, null, cv);
	}

	public long updateinfoReminder(int user_id, String title,
			String description, String set_date, String set_time, int id) {
		// TODO Auto-generated constructor stub
		ContentValues cv = new ContentValues();
		cv.put(person_reminder_title, title);
		cv.put(person_reminder_description, description);
		cv.put(person_reminder_set_date, set_date);
		cv.put(person_reminder_set_time, set_time);
		cv.put(status, "pending...");
		cv.put("user_id", user_id);

		return db.update(person_reminder, cv, reminder_id + " = " + id, null);
	}

	/**
	 * To get the value of reminder from the database
	 * 
	 * @param user_id
	 * 
	 * @return cursor;
	 */
	public Cursor getInfoReminder(int user_id) {
		Cursor cursor = db.query(true, person_reminder, new String[] {
				reminder_id, person_reminder_title, person_reminder_set_date,
				person_reminder_set_time, person_reminder_description },
				"user_id = " + user_id, null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;

	}

	public Cursor searchInfoReminder(String name) {
		Cursor cursor = db.query(true, person_reminder, new String[] {
				reminder_id, person_reminder_title, person_reminder_set_date,
				person_reminder_set_time, person_reminder_description }, person_reminder_title
				+ " LIKE '%" + name + "%'", null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;

	}

	/**
	 * To get the value of the reminder and show in the dialog Activity
	 * 
	 * @param id
	 * @return cursor;
	 */
	public Cursor getInfoReminderMethod(int id) {
		Cursor cursor = db.query(true, person_reminder, new String[] {
				person_reminder_title, person_reminder_description,
				person_reminder_set_date, person_reminder_set_time },
				reminder_id + "=" + id, null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;

	}

	/**
	 * @param id
	 * @return cursor;
	 */
	public Cursor getReminderMethodInfo(int id) {
		Cursor cursor = db.query(true, person_reminder,
				new String[] { reminder_id, person_reminder_set_date,
						person_reminder_set_time }, null, null, null, null,
				null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;

	}

	/**
	 * To check the value of the Login page which are store in the database or
	 * not.
	 * 
	 * @param uName
	 * @param password
	 * @return cursor;
	 */
	public Cursor checkLogin(String uName, String password) {
		// Cursor cursor=db.query( login, new String[]{login_id},
		// login_username+" = '"+uName+"' AND "+login_password+" = '"+password+"'",
		// null, null, null, null, null);
		Cursor cursor = db.query(true, login, new String[] { login_id,
				login_fname }, login_username + " = '" + uName + "' AND "
				+ login_password + " = '" + password + "'", null, null, null,
				null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	public Cursor getName(int id) {
		// Cursor cursor=db.query( login, new String[]{login_id},
		// login_username+" = '"+uName+"' AND "+login_password+" = '"+password+"'",
		// null, null, null, null, null);
		Cursor cursor = db.query(true, login, new String[] { login_fname },
				login_id + " = " + id, null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	public Cursor checkUserName(String user_name) {
		Cursor cursor = db.query(true, login, new String[] { login_id },
				login_username + " = '" + user_name + "'", null, null, null,
				null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();

		}
		return cursor;

	}

	public Cursor checkTest(String user_name) {
		Cursor cursor = db.query(true, login, new String[] { "test" }, null,
				null, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();

		}
		return cursor;

	}

	public void UpdateSecurityQuestion(String question, String answer, int id) {
		ContentValues cv = new ContentValues();
		cv.put(login_question, question);
		cv.put(login_answer, answer);
		cv.put("old_user", "changed");
		db.update(login, cv, login_id + "=" + id, null);

	}
}
