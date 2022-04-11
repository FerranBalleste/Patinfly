package com.example.patinfly.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.patinfly.model.Scooters
import com.example.patinfly.R


class ScooterRecyclerViewAdapter(private val scooters: Scooters) :
        RecyclerView.Adapter<ScooterRecyclerViewAdapter.ViewHolder>() {

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val name: TextView
            val lat: TextView
            val lon: TextView
            val image: ImageView
            val root: View

            init {
                // Define click listener for the ViewHolder's View.
                name = view.findViewById(R.id.sitem_name)
                lat = view.findViewById(R.id.sitem_lat)
                lon = view.findViewById(R.id.sitem_lon)
                image = view.findViewById(R.id.sitem_image)
                root = view
            }
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_scooter, viewGroup, false)

            return ViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.name.text = scooters.scooters.get(position).name
            viewHolder.lat.text = scooters.scooters.get(position).lat
            viewHolder.lon.text = scooters.scooters.get(position).lon
            viewHolder.image.setImageResource(when (scooters.scooters.get(position).lon){
                "0" -> R.drawable.outline_battery_0_bar_24
                "1" -> R.drawable.outline_battery_1_bar_24
                "2" -> R.drawable.outline_battery_2_bar_24
                "3" -> R.drawable.outline_battery_3_bar_24
                "4" -> R.drawable.outline_battery_5_bar_24
                "5" -> R.drawable.outline_battery_full_24
                "6" -> R.drawable.outline_battery_0_bar_24
                else -> R.drawable.outline_battery_0_bar_24
            })

            viewHolder.root.setOnClickListener {
                Toast.makeText(viewHolder.root.context,
                    "Row selected %d".format(position),
                    Toast.LENGTH_LONG).show()
            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = scooters.scooters.size

    }
