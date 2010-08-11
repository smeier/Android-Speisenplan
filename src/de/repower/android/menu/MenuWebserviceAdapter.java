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

    @Override
    public void createManyItems() {
        notImplemented();
    }

    @Override
    public long createNote(String title, String body) {
        notImplemented();
        return 0;
    }

    private void notImplemented() {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public boolean deleteNote(long rowId) {
        notImplemented();
        return false;
    }

    @Override
    public Cursor fetchAllMenus() {
        notImplemented();
        return null;
    }

    @Override
    public List<Menu> fetchMenusFor(Date date) {
        String content = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String url = "http://recanteen.appspot.com/"; // + DateUtil.formatDate(date);
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            System.out.println(response.getEntity().getContentLength());
            content = getStreamContent(response.getEntity().getContent());
            // System.out.println(content);
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

    @Override
    public Cursor fetchNote(long rowId) throws SQLException {
        notImplemented();
        return null;
    }

    @Override
    public boolean updateNote(long rowId, String title, String body) {
        notImplemented();
        return false;
    }

}
