package de.repower.android.menu;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

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
        byte[] ser = cache.serialize();
        MenuCache deser = MenuCache.deSerialize(ser);
        assertNotNull(deser.get(date));
    }
    
    public static void main(String[] args) {
    }
}
