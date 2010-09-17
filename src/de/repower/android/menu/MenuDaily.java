package de.repower.android.menu;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
    private static final long SWITCH_TO_TODAY_OFFSET = 1000 * 3600 * 6;
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

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLastAccess();
        setCurrentDate();
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

    private void setCurrentDate() {
        _date = new Date();
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
        setLastAccess();
        fillData(_components0);
    }

    private void fillData(Components c) {
        if (System.currentTimeMillis() - _lastAccess > SWITCH_TO_TODAY_OFFSET) {
            setCurrentDate();
        }
        DataReaderTask task = new DataReaderTask();
        task.execute(c);
        _progressDialog = ProgressDialog.show(this, " " , " Loading. Please wait ... ", true);
        _progressDialog.show();
    }

    private List<de.repower.android.menu.MenuData> downloadData() {
        List<de.repower.android.menu.MenuData> menus = _dataSource.fetchMenusFor(_date);
        return menus;
    }

    private void showData(Components c, List<de.repower.android.menu.MenuData> menus) {
        _progressDialog.dismiss();
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
                showData(_components, menus);
            }
        }
    }
}
