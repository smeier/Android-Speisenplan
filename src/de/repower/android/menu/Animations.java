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

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class Animations {

    private static final int DURATION = 300;

    protected static Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                +1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(DURATION);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }

    protected static Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                0.0f);
        inFromLeft.setDuration(DURATION);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }

    protected static Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(DURATION);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }

    protected static Animation inFromRightAnimation() {
        Animation inFromRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                0.0f);
        inFromRight.setDuration(DURATION);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }
}
