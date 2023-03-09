package com.example.yelp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MainActivity"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "O8da-9CjscoFnxB3ohaI_zgKCDN4IppL0VrjenDwErsuKkdD9tZQqyNR1AoAulIymsFalN0N51M-SpBLkLXWIQDd2PExcsTtNwbEyEgSHlS43W1YRk5iOXYmPPMGZHYx"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val restaurants = mutableListOf<YelpRestaurent>()
        val adapter = RestaurantAdapter(this,restaurants)
        findViewById<RecyclerView>(R.id.rvRestaruants).adapter=adapter
        findViewById<RecyclerView>(R.id.rvRestaruants).layoutManager=LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val yelpService = retrofit.create(YelpService::class.java)
        val restaurents = yelpService.searchRestaurents("Bearer $API_KEY","Avocado Toast","New York").enqueue(object : Callback<YelpSearchResult>
        {
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                Log.d(TAG,"Response $response")
                val body = response.body()
                if (body==null)
                {
                    Log.w(TAG,"Did not recive valid response body from yelp api...exiting")
                    return
                }
                restaurants.addAll(body.restaurent)
                adapter.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.d(TAG,"Failure $t")
            }
        })
    }

}