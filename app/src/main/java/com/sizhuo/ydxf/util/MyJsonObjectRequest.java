package com.sizhuo.ydxf.util;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

/**
 * @ClassName:	MyJsonObjectRequest
 * @Description:TODO()
 * @author:	sizhuo
 * @date:	2015年10月13日 上午11:51:25
 *
 */
public class MyJsonObjectRequest extends JsonRequest<JSONObject>{

    public MyJsonObjectRequest(int method, String url, String requestBody,
                               Listener<JSONObject> listener, ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
        Log.d("log.d", "construct");
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        String jsonString;
        try {
            jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }catch (JSONException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

}

