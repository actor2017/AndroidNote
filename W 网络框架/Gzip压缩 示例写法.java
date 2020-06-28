package cn.itcast.gzip12;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

//> 检测网站是否经过gzip压缩 http://tool.chinaz.com/Gzips/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //开始请求网络
    public void startConn(View view) {
        new Thread() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    //Accept-Encoding:gzip, deflate, sdch
                    conn = (HttpURLConnection) new URL("http://www.itcast" +
                            ".cn").openConnection();

                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(10000);

                    //请求头, 表示客户端支持gzip压缩
                    conn.addRequestProperty("Accept-Encoding", "gzip");

                    conn.connect();

                    int responseCode = conn.getResponseCode();

                    if (responseCode == 200) {
                        //先从响应头中获取当前的数据类型,判断是否是gzip
                        //Content-Encoding:gzip
                        String encoding = conn.getContentEncoding();

                        if ("gzip".equalsIgnoreCase(encoding)) {
                            //当前返回结果为gzip

                            InputStream in = conn.getInputStream();
                            GZIPInputStream gzip = new GZIPInputStream(in);

                            String result = streamToString(gzip);

                            System.out.println("gzip压缩:" + result);

                        } else {

                            String result = streamToString(conn.getInputStream());

                            System.out.println("没有gzip压缩:" + result);
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }

            }
        }.start();
    }

    private String streamToString(InputStream in) throws IOException {
        int len = 0;
        byte[] buffer = new byte[1024 * 8];

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }

        String result = out.toString();

        out.close();
        in.close();

        return result;
    }
}
