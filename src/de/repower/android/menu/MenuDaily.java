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

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MenuDaily extends Activity implements OnClickListener {
    private static final String CACHE_KEY = "MENUS";
    private static final long SWITCH_TO_TODAY_OFFSET = DateUtil.ONE_HOUR * 6;
    private static final String DATE = "DATE";
    private static final long MAX_AGE_IN_CACHE = 7 * DateUtil.ONE_DAY;
    private MenuDatasource _dataSource;
    private Date _date;
    private long _lastAccess;
    private GestureDetector _gestureScanner;
    private View.OnTouchListener _touchListener;
    private SlideView _slider;
    private Components _components0 = new Components();
    private Components _components1 = new Components();
    private MenuView[] _menuViews = new MenuView[6];
    private ProgressDialog _progressDialog;
    private MenuCache _menuCache = new MenuCache();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(DATE)) {
            _date = DateUtil.parseDBDate(savedInstanceState.getString(DATE));
        } else {
            setCurrentDate();
        }
        setLastAccess();
        _dataSource = new MenuWebserviceAdapter();
        setContentView(R.layout.menu_daily);
        _gestureScanner = new GestureDetector(new GestureListener());
        _touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (_gestureScanner.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        };
        ViewGroup view1 = (ViewGroup) findViewById(R.id.first);
        view1.setOnTouchListener(_touchListener);
        view1.setOnClickListener(this);
        View view2 = findViewById(R.id.second);
        view2.setOnTouchListener(_touchListener);
        view2.setOnClickListener(this);
        _slider = (SlideView) findViewById(R.id.flipper);
        _slider.setContentObjects(_components0, _components1);
        initComponents();
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        state.putString(DATE, DateUtil.formatDateForDB(_date));
        super.onSaveInstanceState(state);
    }

    @Override
    public void onStop() {
        super.onStop();
        saveCache();
    }

    private void saveCache() {
        _menuCache.removeOldEntriesFromCache(MenuDaily.MAX_AGE_IN_CACHE);
        SharedPreferences.Editor pref = getSharedPreferences(CACHE_KEY, MODE_PRIVATE).edit();
        if (pref != null) {
            pref.putString(CACHE_KEY, _menuCache.serialize());
            pref.commit();
        }
    }

    private void readCache() {
        SharedPreferences pref = getSharedPreferences(CACHE_KEY, MODE_PRIVATE);
        if (pref != null && pref.contains(CACHE_KEY)) {
            _menuCache = MenuCache.deSerialize(pref.getString(CACHE_KEY, null));
        }
    }

    private void setCurrentDate() {
        _date = DateUtil.today();
    }

    private void setLastAccess() {
        _lastAccess = System.currentTimeMillis();
    }

    private void initComponents() {
        _components0.date = (TextView) findViewById(R.id.date_0);
        _components1.date = (TextView) findViewById(R.id.date_1);
        _menuViews[0] = (MenuView) findViewById(R.id.tages_0);
        _menuViews[1] = (MenuView) findViewById(R.id.vege_0);
        _menuViews[2] = (MenuView) findViewById(R.id.wok_0);
        _menuViews[3] = (MenuView) findViewById(R.id.tages_1);
        _menuViews[4] = (MenuView) findViewById(R.id.vege_1);
        _menuViews[5] = (MenuView) findViewById(R.id.wok_1);
    }

    @Override
    public void onStart() {
        super.onStart();
        readCache();
        setLastAccess();
        fillData(_components0);
    }

    private void fillData(Components c) {
        if (System.currentTimeMillis() - _lastAccess > SWITCH_TO_TODAY_OFFSET) {
            setCurrentDate();
        }
        if (isInCache(_date)) {
            showData(c, retrieveFromCache(_date));
        }
        DataReaderTask task = new DataReaderTask();
        task.execute(c);
        if (!isInCache(_date)) {
            startProgressIndicator();
        }
    }

    private void startProgressIndicator() {
        _progressDialog = ProgressDialog.show(this, " ", " Loading. Please wait ... ", true);
        _progressDialog.show();
    }

    private void showData(Components c, List<de.repower.android.menu.MenuData> menus) {
        stopProgressIndicator();
        if (menus != null && !menus.isEmpty()) {
            c.date.setText(DateUtil.beautifyDate(menus.get(0).getDate()));
            int index = 0;
            if (c == _components1) {
                index = 3;
            }
            for (MenuData menu : menus) {
                _menuViews[index].setContent(menu.getDescription(), menu.getCategory(), StringUtil.formatPrice(menu
                        .getPrice()));
                index++;
            }
        }
    }

    private List<de.repower.android.menu.MenuData> downloadData() {
        List<de.repower.android.menu.MenuData> menus = _dataSource.fetchMenusFor(_date);
        return menus;
    }

    private void stopProgressIndicator() {
        if (_progressDialog != null) {
            _progressDialog.dismiss();
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

    class GestureListener extends SimpleOnGestureListener {
        private static final boolean FORWARD = true;
        private static final boolean BACKWARD = false;
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

    private void storeInCache(List<MenuData> menus) {
        _menuCache.put(menus.get(0).getDate(), menus);
    }

    private List<MenuData> retrieveFromCache(Date date) {
        return _menuCache.get(date);
    }

    private boolean isInCache(Date date) {
        return _menuCache.containsKey(date);
    }

    class DataReaderTask extends AsyncTask<Components, Void, List<MenuData>> {
        Components _components;

        @Override
        protected List<de.repower.android.menu.MenuData> doInBackground(Components... comps) {
            _components = comps[0];
            return downloadData();
        }

        @Override
        protected void onPostExecute(List<MenuData> menus) {
            if (!isCancelled()) {
                storeInCache(menus);
                showData(_components, menus);
            }
        }
    }
}
