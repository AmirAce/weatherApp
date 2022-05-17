package com.cis2818.weatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class SecondViewModel : ViewModel() {

    private val API: String = "b670cd460139c89c8d7554e20e95abfa"
    private var temp: MutableLiveData<String> = MutableLiveData()
    private var date: MutableLiveData<String> = MutableLiveData()
    private var desc: MutableLiveData<String> = MutableLiveData()
    private var icon: MutableLiveData<String> = MutableLiveData()

    fun getTemp(): MutableLiveData<String> {
        return temp
    }

    fun getDate(): MutableLiveData<String> {
        return date
    }

    fun getDesc(): MutableLiveData<String> {
        return desc
    }

    fun getIcon(): MutableLiveData<String> {
        return icon
    }


    fun setCurrentWeather(city: String, queue: RequestQueue) {
        val url =
            "https://api.openweathermap.org/data/2.5/weather?q=$city&units=imperial&appid=$API"
        val stringRequest =
            StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->
                // create JSONObject
                val obj = JSONObject(response)

                //	get current weather information of a city
                var main =obj.getJSONObject("main")
                temp.setValue("%.0f° F".format(main.getDouble("temp")))
                val toDate = obj.getLong("dt")
                date.setValue(SimpleDateFormat("EEE, MMMM dd hh:mm a", Locale.ENGLISH).format(Date(toDate*1000)))
                val weather = obj.getJSONArray("weather").getJSONObject(0)
                desc.setValue(weather.getString("main"))
                icon.setValue("https://openweathermap.org/img/w/${weather.getString("icon")}.png")


            },
                Response.ErrorListener { temp.value = "0" })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)

    }
}