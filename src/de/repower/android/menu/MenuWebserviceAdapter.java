package de.repower.android.menu;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

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
    public Cursor fetchMenusFor(Date date) {
        String content = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpGet httpGet = new HttpGet("http://recanteen.appspot.com/repower-or/" + DateUtil.formatDate(date));
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            System.out.println(response.getEntity().getContentLength());
            content = getStreamContent(response.getEntity().getContent());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getStreamContent(InputStream content) {
        String result = "";
        try {
            byte[] buffer = new byte[1024];
            while (content.read(buffer) > 0) {
                result += buffer.toString();
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
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
