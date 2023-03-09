package com.example.yelp

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class RestaurantAdapter(val context: MainActivity, val restaurants: List<YelpRestaurent>) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restarant,parent,false))
    }

    override fun getItemCount() = restaurants.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restarant = restaurants[position]
        holder.bind(restarant)
    }

    inner class ViewHolder  (itemView: View):RecyclerView.ViewHolder(itemView)
    {
        fun bind(restarant: YelpRestaurent) {
            itemView.findViewById<TextView>(R.id.tvName).text=restarant.name
            itemView.findViewById<RatingBar>(R.id.ratingBar).rating=restarant.rating.toFloat()
            itemView.findViewById<TextView>(R.id.tvNumReviews).text="${restarant.numReviews} Reviews"
            itemView.findViewById<TextView>(R.id.tvAddress).text=restarant.location.address
            itemView.findViewById<TextView>(R.id.tvCategory).text=restarant.categories[0].title
            itemView.findViewById<TextView>(R.id.tvDistance).text=restarant.displayDistance()
            itemView.findViewById<TextView>(R.id.tvDollar).text=restarant.price
            Glide.with(context).load(restarant.imageUrl).apply(RequestOptions().transforms(CenterCrop(),RoundedCorners(20))).into(itemView.findViewById(R.id.imageView))
        }

    }

}
