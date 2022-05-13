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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.patinfly.NavigationDrawerActivity
import com.example.patinfly.R
import com.example.patinfly.fragments.DetailsFragment
import com.example.patinfly.model.Scooters


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
            viewHolder.lat.text = scooters.scooters.get(position).latitude.toString()
            viewHolder.lon.text = scooters.scooters.get(position).longitude.toString()
            Log.i("SCOOTER BATTERY CALC", ((scooters.scooters.get(position).battery_level).toInt()*5/100).toString())
            viewHolder.image.setImageResource(when ((scooters.scooters.get(position).battery_level).toInt()*5/100){
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
                //Toast.makeText(viewHolder.root.context, "Row selected %d".format(position), Toast.LENGTH_LONG).show()
                val context: Context = viewHolder.root.context
                if(context is NavigationDrawerActivity) {
                    context.supportFragmentManager.commit {
                        val fragment = DetailsFragment()
                        fragment.arguments = scooters.scooters.get(position).bundle
                        Log.i("SCOOTER FULL", scooters.scooters.get(position).toString())
                        replace(R.id.nav_host_fragment_content_drawer, fragment)
                        setReorderingAllowed(true)
                        addToBackStack("home")
                    }
                }
            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = scooters.scooters.size

    }
