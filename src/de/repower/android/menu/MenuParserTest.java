package de.repower.android.menu;

import java.util.Date;

import org.json.JSONException;

import android.test.AndroidTestCase;

public class MenuParserTest extends AndroidTestCase {

    public void testParse() throws JSONException {
        MenuData actual = MenuParser
                .parse("{ \"menu\": { \"category\": \"Wok\", \"price\": \"3.8\", \"date\": \"2010-08-10\", \"description\": \"Ein leckeres Essen\" } }.");
        MenuData expected = new MenuData("Wok", "Ein leckeres Essen", 3.8, DateUtil.parseDBDate("2010-08-10"));
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getDate(), actual.getDate());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getCategory(), actual.getCategory());
        assertEquals(expected, actual);
    }

    public void testParseArray() throws JSONException {
        MenuData actual = MenuParser
                .parseMenuArray("{ \"menus\": [{ \"category\": \"Wok\", \"price\": \"3.8\", \"date\": \"2010-08-10\", \"description\": \"Ein leckeres Essen\" }, "
                        + "{ \"category\": \"Wok\", \"price\": \"3.8\", \"date\": \"2010-08-10\", \"description\": \"Ein leckeres Essen\" }] }.").get(0);
        MenuData expected = new MenuData("Wok", "Ein leckeres Essen", 3.8, DateUtil.parseDBDate("2010-08-10"));
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getDate(), actual.getDate());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getCategory(), actual.getCategory());
        assertEquals(expected, actual);
    }

}
