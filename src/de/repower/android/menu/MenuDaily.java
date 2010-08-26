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
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MenuDaily extends Activity implements OnClickListener {
    private MenuDatasource _dataSource;
    private Date _date;
    private GestureDetector _gestureScanner;
    private View.OnTouchListener _touchListener;
    private SlideView _slider;
    private Components _components0 = new Components();
    private Components _components1 = new Components();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _date = new Date();
        _dataSource = new MenuWebserviceAdapter();
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
        View view1 = findViewById(R.id.first);
        view1.setOnTouchListener(_touchListener);
        view1.setOnClickListener(this);
        View view2 = findViewById(R.id.second);
        view2.setOnTouchListener(_touchListener);
        view2.setOnClickListener(this);
        _slider = (SlideView) findViewById(R.id.flipper);
        _slider.setContentObjects(_components0, _components1);
        initComponents();
    }

    private void initComponents() {
        _components0.date = (TextView) findViewById(R.id.date_0);
        _components0.body[0] = (TextView) findViewById(R.id.body_00);
        _components0.category[0] = (TextView) findViewById(R.id.category_00);
        _components0.price[0] = (TextView) findViewById(R.id.price_00);
        _components0.body[1] = (TextView) findViewById(R.id.body_01);
        _components0.category[1] = (TextView) findViewById(R.id.category_01);
        _components0.price[1] = (TextView) findViewById(R.id.price_01);
        _components0.body[2] = (TextView) findViewById(R.id.body_02);
        _components0.category[2] = (TextView) findViewById(R.id.category_02);
        _components0.price[2] = (TextView) findViewById(R.id.price_02);
        
        _components1.date = (TextView) findViewById(R.id.date_1);
        _components1.body[0] = (TextView) findViewById(R.id.body_10);
        _components1.category[0] = (TextView) findViewById(R.id.category_10);
        _components1.price[0] = (TextView) findViewById(R.id.price_10);
        _components1.body[1] = (TextView) findViewById(R.id.body_11);
        _components1.category[1] = (TextView) findViewById(R.id.category_11);
        _components1.price[1] = (TextView) findViewById(R.id.price_11);
        _components1.body[2] = (TextView) findViewById(R.id.body_12);
        _components1.category[2] = (TextView) findViewById(R.id.category_12);
        _components1.price[2] = (TextView) findViewById(R.id.price_12);
    }

    @Override
    public void onStart() {
        super.onStart();
        fillData(_components0);
    }

    private void fillData(Components c) {
        List<de.repower.android.menu.MenuData> menus = _dataSource.fetchMenusFor(_date);
        if (menus != null && !menus.isEmpty()) {
            int index = 0;
            for (MenuData menu : menus) {
                c.date.setText(DateUtil.beautifyDate(menu.getDate()));
                c.body[index].setText(menu.getDescription());
                c.category[index].setText(menu.getCategory());
                c.price[index].setText(StringUtil.formatPrice(menu.getPrice()));
                index++;
            }
        }
    }

    private void switchDate(boolean forward) {
        if (forward) {
            _date = DateUtil.nextDate(_date);
        } else {
            _date = DateUtil.previousDate(_date);
        }
        fillData((Components) _slider.getCurrentContentObjects());
        if (forward) {
            _slider.showNext();
        } else {
            _slider.showPrevious();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData(_components0);
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
                    switchDate(FORWARD);
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    switchDate(BACKWARD);
                }
            }
            return false;
        }
    }
}
