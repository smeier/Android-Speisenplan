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
import java.util.Arrays;
import java.util.List;

public class CollectionsUtil {

    public static <T> List<T> list(T... args) {
        List<T> result = new ArrayList<T>();
        result.addAll(Arrays.asList(args));
        return result;
    }
}
