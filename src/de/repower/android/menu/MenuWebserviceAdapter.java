package de.repower.android.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;

import android.database.Cursor;
import android.database.SQLException;

public class MenuWebserviceAdapter implements MenuDatasource {

    private static final String CANTEEN = "re_de_or";
    private static final String SERVER = "http://recanteen.appspot.com";

    @Override
    public List<MenuData> fetchMenusFor(Date date) {
        String content = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String url = SERVER + "/" + CANTEEN + "/" + DateUtil.formatDateForDB(date);
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            content = getStreamContent(response.getEntity().getContent());
            return MenuParser.parseMenuArray(content);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getStreamContent(InputStream content) {
        StringBuffer result = new StringBuffer(8192);
        BufferedReader in = new BufferedReader(new InputStreamReader(content));
        try {
            for (String line; (line = in.readLine()) != null;) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

 }
