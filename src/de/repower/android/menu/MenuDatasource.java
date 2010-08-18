package de.repower.android.menu;

import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public interface MenuDatasource {

    public static final String KEY_DATE = "menu_date";
    public static final String KEY_BODY = "body";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_PRICE = "price";
    public static final String KEY_ROWID = "_id";

    /**
     * Return a Cursor over the menus of date
     * 
     * @throws SQLException if note could not be found/retrieved
     */
    public abstract List<MenuData> fetchMenusFor(Date date);

}