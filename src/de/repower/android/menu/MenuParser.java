package de.repower.android.menu;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MenuParser {

    public static Menu parse(String data) throws JSONException {
        JSONTokener parser = new JSONTokener(data);
        JSONObject object = (JSONObject) parser.nextValue();
        JSONObject menuObject = object.getJSONObject("menu");
        Date date = DateUtil.parseDate(menuObject.getString("date"));
        String category = menuObject.getString("category");
        double price = menuObject.getDouble("price");
        String description = menuObject.getString("description");
        return new Menu(category, description, price, date);
    }

    public static Menu parseMenuArray(String data) throws JSONException {
        JSONTokener parser = new JSONTokener(data);
        JSONObject object = (JSONObject) parser.nextValue();
        JSONArray menus = object.getJSONArray("menus");
        JSONObject menuObject = menus.getJSONObject(0);
        Date date = DateUtil.parseDate(menuObject.getString("date"));
        String category = menuObject.getString("category");
        double price = menuObject.getDouble("price");
        String description = menuObject.getString("description");
        return new Menu(category, description, price, date);
    }
}
