package com.allnone.app;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;


public class HttpRequest {
    private StringBuilder strResponseFromCall = new StringBuilder();

    public String fnStrGetResponseFromCall() {
        return strResponseFromCall.toString();
    }

    public String fnStrSettingParameters(List<NameValuePair> Params) {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair strItem : Params) {
            if (first)
                first = false;
            else
                result.append("&");
            try {
                result.append(URLEncoder.encode(strItem.getName(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(strItem.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }


    public void fnHttpPost(String function, String params) {
        HttpURLConnection myHttpclient = null;
        try {
            URL url = new URL(function);
            ByteBuffer databuffer = Charset.forName("UTF-8").encode(params);
            byte[] postData = databuffer.array();
            int dataLength = postData.length;
            myHttpclient = (HttpURLConnection) url.openConnection();

            myHttpclient.setDoOutput(true);
            myHttpclient.setInstanceFollowRedirects(false);
            myHttpclient.setRequestMethod("POST");
            myHttpclient.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            myHttpclient.setRequestProperty("charset", "utf-8");
            myHttpclient.setRequestProperty("Content-Length", Integer.toString(dataLength));
            myHttpclient.setUseCaches(false);
            myHttpclient.getOutputStream().write(postData);
            int response = myHttpclient.getResponseCode();

            if (response == HttpURLConnection.HTTP_OK) {
                InputStream ioResponseStream = myHttpclient.getInputStream();
                InputStreamReader ioStreamReader = new InputStreamReader(ioResponseStream);
                BufferedReader bIOReader = new BufferedReader(ioStreamReader);
                String strReaderLine;
                strResponseFromCall.append("<");
                while ((strReaderLine = bIOReader.readLine()) != null) {
                    strResponseFromCall.append(strReaderLine);

                }
                System.out.println(fnStrGetResponseFromCall());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert myHttpclient != null;
            myHttpclient.disconnect();
        }
    }

    public void fn_BXP_GetCall(String function) {
        HttpURLConnection httpClient = null;
        try {
            URL url = new URL(function);
            httpClient = (HttpURLConnection) url.openConnection();
            httpClient.setDoInput(true);

            int intResponseCode = httpClient.getResponseCode();
            if (intResponseCode == HttpURLConnection.HTTP_OK) {
                InputStream ioResponseStream = httpClient.getInputStream();
                InputStreamReader ioStreamReader = new InputStreamReader(ioResponseStream);
                BufferedReader bIOReader = new BufferedReader(ioStreamReader);
                String StrReaderLine;
                while ((StrReaderLine = bIOReader.readLine()) != null) {
                    strResponseFromCall.append(StrReaderLine);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert httpClient != null;
            httpClient.disconnect();
        }

    }
}
