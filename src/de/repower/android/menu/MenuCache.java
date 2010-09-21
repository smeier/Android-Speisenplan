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
        for (Date date : _map.keySet()) {
            if (DateUtil.olderThan(date, maxAgeInCache)) {
                _map.remove(date);
            }
        }
        
    }
}
