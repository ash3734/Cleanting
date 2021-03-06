package com.example.ash.cleantind_dgu_cse_se;

import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

/**
 * 데이터 베이스 서버에 연동할 클래스
 * Json형태로 받아온다.
 * php파일과 연결
 */

public class DBManager {

    private Vector<NameValuePair> v_para_arr;    //php에 보낼 변수
    private int n_parser_cnt;   //현재 파싱중인 데이터 순번

    private JSONObject jsonobject;
    private JSONArray Json_item;
    private String s_result;    //PHP로 부터 받은 결과

    public DBManager() {
        v_para_arr = new Vector<NameValuePair>();
        n_parser_cnt = 0;
        jsonobject = null;

    }
    //URL을 받아 디비에 연결한다.
    public void db_connect(String s_url) {
        DBConnecter dbConnecter = new DBConnecter();
        dbConnecter.execute(s_url);
        try {
            s_result = dbConnecter.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        dbConnecter.cancel(true);
    }

    //php에 보낼 변수를 설정할때 사용
    public void setAParameter(String s_name, String s_value) {
        int i;

        //동일한 이름의 s_name이 있다면 제거 (중복 방지)
        for (i = 0; i < v_para_arr.size(); i++) {
            if (s_name.equals(v_para_arr.get(i).getName())) {
                v_para_arr.remove(i);
            }
        }

        v_para_arr.add(new BasicNameValuePair(s_name, s_value));
    }


    //사용자 지정명의 데이터를 파싱하고자 할때,
    public String getResult(String s_name) {

        if(jsonobject == null){
            try {
                jsonobject = new JSONObject(s_result);
                Json_item = jsonobject.getJSONArray("result");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String s_value = "";
        try {
            s_value =   URLDecoder.decode(Json_item.getJSONObject(n_parser_cnt).getString(s_name), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return s_value;
    }

    public boolean setNextData() {
        if(++n_parser_cnt < Json_item.length()){
            return true;
        }else{
            return false;
        }
    }

    public String getResult() {
        return s_result;
    }


    //네트워크 연결 스레드
    private class DBConnecter extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... arg0) {
            return db_connect((String) arg0[0]);
        }

        protected void onPostExecute(String result) {

        }

        protected String db_connect(String s_url) {
            String result = "";
            try {
                String url = s_url + "?" + URLEncodedUtils.format(v_para_arr, "utf-8");
                HttpGet request = new HttpGet(url);

                HttpClient client = new DefaultHttpClient();
                ResponseHandler<String> reshandler = new BasicResponseHandler();
                result = client.execute(request, reshandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}
