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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MenuCache implements Serializable {
    private Map<Date, List<MenuData>> _map = new HashMap<Date, List<MenuData>>();

    public void put(Date date, List<MenuData> menus) {
        _map.put(date, menus);
    }

    public List<MenuData> get(Date date) {
        return _map.get(date);
    }

    public boolean containsKey(Date date) {
        return _map.containsKey(date);
    }

    public String serialize() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectStream = new ObjectOutputStream(out);
            objectStream.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeLines(out.toByteArray());
    }

    public static MenuCache deSerialize(String input) {
        byte[] serialized = Base64.decodeLines(input);
        if (serialized != null) {
            try {
                ObjectInputStream objectStream = new ObjectInputStream(new ByteArrayInputStream(serialized));
                return (MenuCache) objectStream.readObject();
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new MenuCache();
    }

    void removeOldEntriesFromCache(long maxAgeInCache) {
        for (Iterator<Date> it = _map.keySet().iterator(); it.hasNext();) {
            Date date = it.next();
            if (DateUtil.olderThan(date, maxAgeInCache)) {
                it.remove();
            }
        }
        
    }
}
