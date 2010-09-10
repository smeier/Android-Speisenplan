package de.repower.android.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuView extends LinearLayout {
    TextView _body;
    TextView _category;
    TextView _price;
    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        Log.i("menu", "constructor with attrs");
    }

    public MenuView(Context context) {
        // UNUSED?
        super(context);
        init(new AttributeSet() {
            @Override
            public int getStyleAttribute() {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public String getPositionDescription() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public int getIdAttributeResourceValue(int defaultValue) {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public String getIdAttribute() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public String getClassAttribute() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public String getAttributeValue(String namespace, String name) {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public String getAttributeValue(int index) {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public int getAttributeUnsignedIntValue(String namespace, String attribute, int defaultValue) {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int getAttributeUnsignedIntValue(int index, int defaultValue) {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int getAttributeResourceValue(String namespace, String attribute, int defaultValue) {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int getAttributeResourceValue(int index, int defaultValue) {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int getAttributeNameResource(int index) {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public String getAttributeName(int index) {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public int getAttributeListValue(String namespace, String attribute, String[] options, int defaultValue) {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int getAttributeListValue(int index, String[] options, int defaultValue) {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int getAttributeIntValue(String namespace, String attribute, int defaultValue) {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int getAttributeIntValue(int index, int defaultValue) {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public float getAttributeFloatValue(String namespace, String attribute, float defaultValue) {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public float getAttributeFloatValue(int index, float defaultValue) {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int getAttributeCount() {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public boolean getAttributeBooleanValue(String namespace, String attribute, boolean defaultValue) {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public boolean getAttributeBooleanValue(int index, boolean defaultValue) {
                // TODO Auto-generated method stub
                return false;
            }
        });
        Log.i("menu", "constructor without attrs");
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MenuView);
        // Use a
        if (a.getString(R.styleable.MenuView_category) != null) {
            Log.i("menu", a.getString(R.styleable.MenuView_category));
        }
        // Don't forget this
        a.recycle();
        LinearLayout view = (LinearLayout) LayoutInflater.from(this.getContext()).inflate(R.layout.menu_view, this);
        _body = (TextView) findViewWithTag("body");
        _category = (TextView) findViewWithTag("category");
        _price = (TextView) findViewWithTag("price");
    }
    
    public void setContent(String body, String category, String price) {
        _body.setText(body);
        _category.setText(category);
        _price.setText(price);
    }
}
