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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MenuDaily extends Activity implements OnClickListener {
    private MenuDatasource mDbHelper;
    private TextView date;
    private TextView[] category = new TextView[3];
    private TextView[] price = new TextView[3];
    private TextView[] body = new TextView[3];
    private Date _date;

    private GestureDetector _gestureScanner;
    private View.OnTouchListener _touchListener;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _date = new Date();
        mDbHelper = new MenuWebserviceAdapter();
        // SlideView view = new SlideView(this);
        // view.setLayoutParams(new
        // ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
        // ViewGroup.LayoutParams.FILL_PARENT));
        // setContentView(view);
        setContentView(R.layout.menu_daily);

        _gestureScanner = new GestureDetector(new MyGestureDetector());
        _touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (_gestureScanner.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        };
        View view = findViewById(R.id.menu_daily); // getViewInflate(R.layout.menu_daily);
        view.setOnTouchListener(_touchListener);
        view.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        fillData();
    }

    private void fillData() {
        List<de.repower.android.menu.MenuData> menus = mDbHelper.fetchMenusFor(_date);

        date = (TextView) findViewById(R.id.date);
        if (date != null) {
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

    }

    private void switchDate(boolean forward) {
        if (forward) {
            _date = DateUtil.nextDate(_date);
        } else {
            _date = DateUtil.previousDate(_date);
        }
        fillData();
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

    class MyGestureDetector extends SimpleOnGestureListener {
        private static final boolean FORWARD = true;
        private static final boolean BACKWARD = false;
        private static final float VELO_X_Y_THRESHOLD = 5;
        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_MAX_OFF_PATH = 250;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(e1.getY() - e2.getY()) < SWIPE_MAX_OFF_PATH) {
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    switchDate(BACKWARD);
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    switchDate(FORWARD);
                }
            }
            return false;
        }
    }

}
