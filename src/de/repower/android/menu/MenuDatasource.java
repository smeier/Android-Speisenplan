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

import android.database.SQLException;

public interface MenuDatasource {

    public static final String KEY_DATE = "menu_date";
    public static final String KEY_BODY = "body";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_PRICE = "price";
    public static final String KEY_ROWID = "_id";

    /**
     * Return a Cursor over the menus of date
     * 
     * @throws SQLException if note could not be found/retrieved
     */
    public abstract List<MenuData> fetchMenusFor(Date date);

}