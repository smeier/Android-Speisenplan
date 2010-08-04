/*
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package de.repower.android.menu;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

/**
 * Simple notes database access helper class. Defines the basic CRUD operations
 * for the notepad example, and gives the ability to list all notes as well as
 * retrieve or modify a specific note.
 * 
 * This has been improved from the first version of this tutorial through the
 * addition of better error handling and also using returning a Cursor instead
 * of using a collection of inner classes (which is less scalable and not
 * recommended).
 */
public class MenuDbAdapter implements MenuDatasource {

    private static final String TAG = "MenuDbAdapter";
    private DatabaseHelper _dbHelper;
    private SQLiteDatabase _db;

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE = "create table menu (_id integer primary key autoincrement, "
            + "menu_date date not null, body text not null, price float not null default 1.0, category int not null default 1);";

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "menu";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
            createManyItems(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS menu");
            onCreate(db);
        }

        public void createManyItems(SQLiteDatabase db) {
            deleteRecords(db);
            for (int i = 0; i < 9; i++) {
                insertRecordIntoDatabase(db, i);
            }
        }

        private void deleteRecords(SQLiteDatabase db) {
            String sql = "delete from menu where menu_date >= '" + makeDate(0) + "'";
            db.execSQL(sql);
        }

        private void insertRecordIntoDatabase(SQLiteDatabase db, int i) {
            String sql = "insert into menu (menu_date, body, price, category) values ('" + makeDate(i) + "', '"
                    + makeDescription(i) + "', " + makePrice(i) + ", '" + makeCategory(i) + "')";
            db.execSQL(sql);
        }

        private String makeDescription(int i) {
            switch (i % 3) {
                case 0:
                    return "Gebackenes Seehechtfilet mit Remouladensoße und Bratkartoffeln";
                case 1:
                    return "Wrap gefüllt mit Rinderhack Salat und Kräuterdip";
                default:
                    return "Gemüse Frikadelle an Salzkartoffeln Petersieliensauce und Salatbeilage";
            }
        }

    }

    private static String makeCategory(int i) {
        switch (i % 3) {
            case 0:
                return "Stamm";
            case 1:
                return "Wok";
            default:
                return "Veggie";
        }
    }

    private static double makePrice(int i) {
        switch (i % 3) {
            case 0:
                return 3.8;
            case 1:
                return 5.2;
            default:
                return 4.5;
        }
    }

    private static String makeDate(int i) {
        Calendar day = new GregorianCalendar();
        day.add(Calendar.DAY_OF_MONTH, i / 3);
        return formatDate(day.getTime());
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx
     *            the Context within which to work
     */
    public MenuDbAdapter(Context ctx) {
        this.mCtx = ctx;
        open();
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException
     *             if the database could be neither opened or created
     */
    private MenuDatasource open() throws SQLException {
        _dbHelper = new DatabaseHelper(mCtx);
        _db = _dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        _dbHelper.close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.android.demo.notepad3.StatuscodeDatasource#createNote(java.lang.String
     * , java.lang.String)
     */
    public long createNote(String title, String body) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(MenuDatasource.KEY_DATE, title);
        initialValues.put(MenuDatasource.KEY_BODY, body);

        return _db.insert(DATABASE_TABLE, null, initialValues);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.android.demo.notepad3.StatuscodeDatasource#deleteNote(long)
     */
    public boolean deleteNote(long rowId) {

        return _db.delete(DATABASE_TABLE, MenuDatasource.KEY_ROWID + "=" + rowId, null) > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.android.demo.notepad3.StatuscodeDatasource#fetchAllNotes()
     */
    public Cursor fetchAllMenus() {

        return _db.query(DATABASE_TABLE, new String[] { MenuDatasource.KEY_ROWID, MenuDatasource.KEY_DATE,
                MenuDatasource.KEY_BODY, MenuDatasource.KEY_CATEGORY, MenuDatasource.KEY_PRICE }, null, null, null,
                null, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.android.demo.notepad3.StatuscodeDatasource#fetchNote(long)
     */
    public Cursor fetchNote(long rowId) throws SQLException {

        Cursor mCursor =

        _db.query(true, DATABASE_TABLE, new String[] { MenuDatasource.KEY_ROWID, MenuDatasource.KEY_DATE,
                MenuDatasource.KEY_BODY, MenuDatasource.KEY_CATEGORY, MenuDatasource.KEY_PRICE },
                MenuDatasource.KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.android.demo.notepad3.StatuscodeDatasource#updateNote(long,
     * java.lang.String, java.lang.String)
     */
    public boolean updateNote(long rowId, String title, String body) {
        ContentValues args = new ContentValues();
        args.put(MenuDatasource.KEY_DATE, title);
        args.put(MenuDatasource.KEY_BODY, body);

        return _db.update(DATABASE_TABLE, args, MenuDatasource.KEY_ROWID + "=" + rowId, null) > 0;
    }

    @Override
    public Cursor fetchMenusFor(Date date) {
        String dateStr = formatDate(date);
        Cursor mCursor = _db.query(true, DATABASE_TABLE,
                new String[] { MenuDatasource.KEY_ROWID, MenuDatasource.KEY_DATE, MenuDatasource.KEY_BODY,
                        MenuDatasource.KEY_CATEGORY, MenuDatasource.KEY_PRICE }, MenuDatasource.KEY_DATE + "='"
                        + dateStr + "'", null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    private static String formatDate(Date date) {
        return DateFormat.format("yyyy-MM-dd", date).toString();
    }

    @Override
    public void createManyItems() {
        _dbHelper.createManyItems(_db);
    }
}
