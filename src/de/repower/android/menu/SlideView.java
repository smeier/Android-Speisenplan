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

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ViewFlipper;

public class SlideView extends ViewFlipper {
    private Animation _inFromRightAnimation;
    private Animation _inFromLeftAnimation;
    private Animation _outToRightAnimation;
    private Animation _outToLeftAnimation;
    private boolean _useFirstView = false;
    private Object _second = new ArrayList<View>();
    private Object _first = new ArrayList<View>();
    
    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAnimations();
    }
    
    public SlideView(Context context) {
        super(context);
        initAnimations();
    }
    
    public void setContentObjects(Object first, Object second) {
        _first = first;
        _second = second;
    }
    
    public Object getCurrentContentObjects() {
        if (_useFirstView) {
            return _first;
        } else {
            return _second;
        }
    }
    
    @Override
    public void showNext() {
        setInAnimation(_inFromRightAnimation);
        setOutAnimation(_outToLeftAnimation);
        _useFirstView = !_useFirstView;
        super.showNext();
    }
    
    @Override
    public void showPrevious() {
        setInAnimation(_inFromLeftAnimation);
        setOutAnimation(_outToRightAnimation);
        _useFirstView = !_useFirstView;
        super.showPrevious();
    }
    
    private  void initAnimations() {
        _inFromRightAnimation = Animations.inFromRightAnimation();
        _outToLeftAnimation = Animations.outToLeftAnimation();
        _inFromLeftAnimation = Animations.inFromLeftAnimation();
        _outToRightAnimation = Animations.outToRightAnimation();

    }
    
    /**
     * This is a workaraound for a bug in android 2.1
     * see http://daniel-codes.blogspot.com/2010/05/viewflipper-receiver-not-registered.html
     */
    @Override
    protected void onDetachedFromWindow() {
        try {
            super.onDetachedFromWindow();
        }
        catch (IllegalArgumentException e) {
            stopFlipping();
        }
    }
}
