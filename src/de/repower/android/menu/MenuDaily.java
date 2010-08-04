/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.repower.android.menu;

import java.text.DecimalFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MenuDaily extends Activity implements OnClickListener {
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;

    private static final int INSERT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;
    private static final int INSERT_MANY_ID = Menu.FIRST + 1;

    private MenuDatasource mDbHelper;
    private TextView date;
    private TextView[] category = new TextView[3];
    private TextView[] price = new TextView[3];
    private TextView[] body = new TextView[3];

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new MenuDbAdapter(this);
        setContentView(R.layout.menu_daily);
        fillData();
    }

    private void fillData() {
        Date today = new Date();
        Cursor cursor = mDbHelper.fetchMenusFor(today);
        startManagingCursor(cursor);

        date = (TextView) findViewById(R.id.date);
        body[0] = (TextView) findViewById(R.id.body_0);
        category[0] = (TextView) findViewById(R.id.category_0);
        price[0] = (TextView) findViewById(R.id.price_0);
        body[1] = (TextView) findViewById(R.id.body_1);
        category[1] = (TextView) findViewById(R.id.category_1);
        price[1] = (TextView) findViewById(R.id.price_1);
        body[2] = (TextView) findViewById(R.id.body_2);
        category[2] = (TextView) findViewById(R.id.category_2);
        price[2] = (TextView) findViewById(R.id.price_2);

        startManagingCursor(cursor);
        if (!cursor.isClosed() && !cursor.isAfterLast()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MenuDatasource.KEY_DATE);
            date.setText(cursor.getString(columnIndex));
            int index = 0;
            do {
                body[index].setText(cursor.getString(cursor.getColumnIndexOrThrow(MenuDatasource.KEY_BODY)));
                category[index].setText(cursor.getString(cursor.getColumnIndexOrThrow(MenuDatasource.KEY_CATEGORY)));
                price[index].setText(formatPrice(cursor));
                index++;
            } while (cursor.moveToNext() && index < 3);
        }

    }

    private String formatPrice(Cursor cursor) {
        return (new DecimalFormat("#0.00")).format(cursor.getFloat(cursor.getColumnIndexOrThrow(MenuDatasource.KEY_PRICE))) + " €";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID, 0, R.string.menu_insert);
        menu.add(0, INSERT_MANY_ID, 0, R.string.menu_insert_many);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case INSERT_ID:
                createNote();
                return true;
            case INSERT_MANY_ID:
                mDbHelper.createManyItems();
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
                mDbHelper.deleteNote(info.id);
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createNote() {
        Intent i = new Intent(this, MenuEdit.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }

    @Override
    public void onClick(View view) {
        EditText edit = (EditText) view;
        Intent i = new Intent(this, MenuEdit.class);
        i.putExtra(MenuDatasource.KEY_ROWID, edit.getText());
        startActivityForResult(i, ACTIVITY_EDIT);
    }
}
