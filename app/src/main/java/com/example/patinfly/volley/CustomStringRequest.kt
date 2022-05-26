package com.example.patinfly.volley

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject

class CustomStringRequest(
    method:Int, url: String,
    listener: Response.Listener<String>,
    errorListener: Response.ErrorListener,
    credentials:String
)
    : StringRequest(method, url, listener, errorListener) {

    private var mCredentials:String = credentials

    @Throws(AuthFailureError::class)
    override fun getHeaders(): Map<String, String> {
        val headers = HashMap<String, String>()
        headers["Content-Type"] = "application/json"
        headers["api-key"] = mCredentials
        return headers
    }
}