package com.example.patinfly.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.patinfly.NavigationDrawerActivity
import com.example.patinfly.R
import com.example.patinfly.fragments.DetailsFragment
import com.example.patinfly.fragments.DetailsFragmentDirections
import com.example.patinfly.fragments.HomeFragmentDirections
import com.example.patinfly.model.Scooters


class ScooterRecyclerViewAdapter(private val scooters: Scooters, private val navController: NavController) :
        RecyclerView.Adapter<ScooterRecyclerViewAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val name: TextView
            val lat: TextView
            val lon: TextView
            val image: ImageView
            val root: View

            init {
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
            val scooter = scooters.scooters.get(position)

            viewHolder.name.text = scooter.name
            viewHolder.lat.text = scooter.latitude.toString()
            viewHolder.lon.text = scooter.longitude.toString()
            viewHolder.image.setImageResource(when ((scooter.battery_level).toInt()*5/100){
                0 -> R.drawable.outline_battery_0_bar_24
                1 -> R.drawable.outline_battery_1_bar_24
                2 -> R.drawable.outline_battery_2_bar_24
                3 -> R.drawable.outline_battery_3_bar_24
                4 -> R.drawable.outline_battery_5_bar_24
                5 -> R.drawable.outline_battery_full_24
                6 -> R.drawable.outline_battery_0_bar_24
                else -> R.drawable.outline_battery_0_bar_24
            })

            viewHolder.root.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                    scooter.name,
                    scooter.latitude.toString(),
                    scooter.longitude.toString(),
                    scooter.km_use.toString(),
                    scooter.date_last_maintenance!!,
                    scooter.battery_level.toString()
                )
                navController.navigate(action)
            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = scooters.scooters.size
    }
