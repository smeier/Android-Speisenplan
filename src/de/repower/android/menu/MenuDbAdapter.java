// Copyright 2010 Stefan Meier
//
// This module is multi-licensed and may be used under the terms
// of any of the following licenses:
//
//  EPL, Eclipse Public License, http://www.eclipse.org/legal
//  LGPL, GNU Lesser General Public License, http://www.gnu.org/licenses/lgpl.html
//  AL, Apache License, http://www.apache.org/licenses
//  BSD, BSD License, http://www.opensource.org/licenses/bsd-license.php
//
// Please contact the author if you need another license.
// This module is provided "as is", without warranties of any kind.
package de.repower.android.menu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        return DateUtil.formatDateForDB(day.getTime());
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

    public Cursor fetchAllMenus() {

        return _db.query(DATABASE_TABLE, new String[] { MenuDatasource.KEY_ROWID, MenuDatasource.KEY_DATE,
                MenuDatasource.KEY_BODY, MenuDatasource.KEY_CATEGORY, MenuDatasource.KEY_PRICE }, null, null, null,
                null, null);
    }

    public boolean updateNote(long rowId, String title, String body) {
        ContentValues args = new ContentValues();
        args.put(MenuDatasource.KEY_DATE, title);
        args.put(MenuDatasource.KEY_BODY, body);

        return _db.update(DATABASE_TABLE, args, MenuDatasource.KEY_ROWID + "=" + rowId, null) > 0;
    }

    private Cursor cursorFor(Date date) {
        String dateStr = DateUtil.formatDateForDB(date);
        Cursor mCursor = _db.query(true, DATABASE_TABLE,
                new String[] { MenuDatasource.KEY_ROWID, MenuDatasource.KEY_DATE, MenuDatasource.KEY_BODY,
                        MenuDatasource.KEY_CATEGORY, MenuDatasource.KEY_PRICE }, MenuDatasource.KEY_DATE + "='"
                        + dateStr + "'", null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public List<MenuData> fetchMenusFor(Date date) {
        Cursor cursor = cursorFor(date);
        List<MenuData> result = new ArrayList<MenuData>();
        if (!cursor.isClosed() && !cursor.isAfterLast()) {
            do {
                String dateStr = cursor.getString(cursor.getColumnIndexOrThrow(MenuDatasource.KEY_DATE));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(MenuDatasource.KEY_CATEGORY));
                Double price = cursor.getDouble(cursor.getColumnIndexOrThrow(MenuDatasource.KEY_PRICE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(MenuDatasource.KEY_BODY));
                MenuData menu = new MenuData(category, description, price, DateUtil.parseDBDate(dateStr));
                result.add(menu);
            } while (cursor.moveToNext());
        }
        return result;
    }

    public void createManyItems() {
        _dbHelper.createManyItems(_db);
    }
}
