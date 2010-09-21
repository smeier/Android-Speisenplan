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

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class MenuCacheTest {
    @Test
    public void testSerialize() {
        Date date = new Date();
        List<MenuData> menus = new ArrayList<MenuData>();
        menus.add(new MenuData("sdf", "sdlfj", 3.5, date));
        MenuCache cache = new MenuCache();
        cache.put(date, menus);
        String ser = cache.serialize();
        MenuCache deser = MenuCache.deSerialize(ser);
        assertNotNull(deser.get(date));
    }
    
    public static void main(String[] args) {
    }
}
