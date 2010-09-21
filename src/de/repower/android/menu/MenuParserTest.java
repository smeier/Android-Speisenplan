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
