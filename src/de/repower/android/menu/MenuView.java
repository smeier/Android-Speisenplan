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
        init(emptyAttributeSet());
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
        LayoutInflater.from(this.getContext()).inflate(R.layout.menu_view, this);
        _body = (TextView) findViewWithTag("body");
        _category = (TextView) findViewWithTag("category");
        _price = (TextView) findViewWithTag("price");
    }
    
    public void setContent(String body, String category, String price) {
        _body.setText(body);
        _category.setText(category);
        _price.setText(price);
    }

    private AttributeSet emptyAttributeSet() {
        return new AttributeSet() {
            @Override
            public int getStyleAttribute() {
                return 0;
            }
            
            @Override
            public String getPositionDescription() {
                return null;
            }
            
            @Override
            public int getIdAttributeResourceValue(int defaultValue) {
                return 0;
            }
            
            @Override
            public String getIdAttribute() {
                return null;
            }
            
            @Override
            public String getClassAttribute() {
                return null;
            }
            
            @Override
            public String getAttributeValue(String namespace, String name) {
                return null;
            }
            
            @Override
            public String getAttributeValue(int index) {
                return null;
            }
            
            @Override
            public int getAttributeUnsignedIntValue(String namespace, String attribute, int defaultValue) {
                return 0;
            }
            
            @Override
            public int getAttributeUnsignedIntValue(int index, int defaultValue) {
                return 0;
            }
            
            @Override
            public int getAttributeResourceValue(String namespace, String attribute, int defaultValue) {
                return 0;
            }
            
            @Override
            public int getAttributeResourceValue(int index, int defaultValue) {
                return 0;
            }
            
            @Override
            public int getAttributeNameResource(int index) {
                return 0;
            }
            
            @Override
            public String getAttributeName(int index) {
                return null;
            }
            
            @Override
            public int getAttributeListValue(String namespace, String attribute, String[] options, int defaultValue) {
                return 0;
            }
            
            @Override
            public int getAttributeListValue(int index, String[] options, int defaultValue) {
                return 0;
            }
            
            @Override
            public int getAttributeIntValue(String namespace, String attribute, int defaultValue) {
                return 0;
            }
            
            @Override
            public int getAttributeIntValue(int index, int defaultValue) {
                return 0;
            }
            
            @Override
            public float getAttributeFloatValue(String namespace, String attribute, float defaultValue) {
                return 0;
            }
            
            @Override
            public float getAttributeFloatValue(int index, float defaultValue) {
                return 0;
            }
            
            @Override
            public int getAttributeCount() {
                return 0;
            }
            
            @Override
            public boolean getAttributeBooleanValue(String namespace, String attribute, boolean defaultValue) {
                return false;
            }
            
            @Override
            public boolean getAttributeBooleanValue(int index, boolean defaultValue) {
                return false;
            }
        };
    }
}
