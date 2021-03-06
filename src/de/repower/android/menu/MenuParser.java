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
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MenuParser {

    public static MenuData parse(String data) throws JSONException {
        JSONTokener parser = new JSONTokener(data);
        JSONObject object = (JSONObject) parser.nextValue();
        JSONObject menuObject = object.getJSONObject("menu");
        Date date = DateUtil.parseDBDate(menuObject.getString("date"));
        String category = menuObject.getString("category");
        double price = menuObject.getDouble("price");
        String description = menuObject.getString("description");
        return new MenuData(category, description, price, date);
    }

    public static List<MenuData> parseMenuArray(String data) throws JSONException {
        List<MenuData> result = new ArrayList<MenuData>();
        JSONTokener parser = new JSONTokener(data);
        JSONObject object = (JSONObject) parser.nextValue();
        JSONArray menus = object.getJSONArray("menus");
        for (int i = 0; i < menus.length(); i++) {
            JSONObject menuObject = menus.getJSONObject(i);
            Date date = DateUtil.parseDBDate(menuObject.getString("date"));
            String category = menuObject.getString("category");
            double price = menuObject.getDouble("price");
            String description = menuObject.getString("description");
            result.add(new MenuData(category, description, price, date));
        }
        return result;
    }
}
