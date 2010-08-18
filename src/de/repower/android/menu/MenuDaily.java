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

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MenuDaily extends Activity implements OnClickListener {
    private MenuDatasource mDbHelper;
    private TextView date;
    private TextView[] category = new TextView[3];
    private TextView[] price = new TextView[3];
    private TextView[] body = new TextView[3];

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new MenuWebserviceAdapter();
        setContentView(R.layout.menu_daily);
    }

    @Override
    public void onStart() {
        super.onStart();
        fillData();
    }

    private void fillData() {
        Date today = new Date();
        List<de.repower.android.menu.MenuData> menus = mDbHelper.fetchMenusFor(today);

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

        if (menus != null && !menus.isEmpty()) {
            int index = 0;
            for (MenuData menu : menus) {
                date.setText(DateUtil.beautifyDate(menu.getDate()));
                body[index].setText(menu.getDescription());
                category[index].setText(menu.getCategory());
                price[index].setText(StringUtil.formatPrice(menu.getPrice()));
                index++;
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }

    @Override
    public void onClick(View view) {
        // nothing yet
    }
}
