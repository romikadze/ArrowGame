package DB;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseCursor;
import com.badlogic.gdx.sql.DatabaseFactory;
import com.badlogic.gdx.sql.SQLiteGdxException;

public class DatabaseTest {

    Database dbHandler;

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_PASSWORD = "password";

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE_NAME + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_LOGIN
            + " text not null, " + COLUMN_PASSWORD + " text not null);";

    public DatabaseTest() {
        Gdx.app.log("TAG", "creation started");
        dbHandler = DatabaseFactory.getNewDatabase(DATABASE_NAME,
                DATABASE_VERSION, DATABASE_CREATE, null);

        dbHandler.setupDatabase();
        try {
            dbHandler.openOrCreateDatabase();
            dbHandler.execSQL(DATABASE_CREATE);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        Gdx.app.log("TAG", "created successfully");

    }

    public String readFromDB(String login){
        DatabaseCursor cursor = null;

        try {
            cursor = dbHandler.rawQuery("SELECT " + COLUMN_LOGIN + ", " + COLUMN_PASSWORD + " FROM " + TABLE_NAME);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        while (cursor.next()) {
            if (String.valueOf(cursor.getString(0)).equals(login)){
                Gdx.app.log("TAG", cursor.getString(0) + ":" + cursor.getString(1));
                return cursor.getString(1);
            }

            Gdx.app.log("TAG", "Login - " + cursor.getString(0));
            Gdx.app.log("TAG", "Password - " + cursor.getString(1));
        }
        Gdx.app.log("TAG", "Invalid login");
        return "LoginNotFound";
    }

    public void writeToDB(String login, String password){
        try {
            dbHandler
                    .execSQL("INSERT INTO " + TABLE_NAME + " ('" + COLUMN_LOGIN + "', " + COLUMN_PASSWORD + ") " +
                            "VALUES ('" + login + "', '" + password + "')");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    public void clearTable(){
        try {
            Gdx.app.log("TAG", "clear//");
            dbHandler
                    .execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " >0");
            //readFromDB();
            Gdx.app.log("TAG", "clear//");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    public void dispose(){
        try {
            dbHandler.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        dbHandler = null;
        Gdx.app.log("TAG", "dispose");
    }
}