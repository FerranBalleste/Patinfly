package com.example.patinfly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.patinfly.R
import com.example.patinfly.model.Rents


class HistoryRecyclerViewAdapter(private val rents: Rents) :
        RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>() {

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val name: TextView
            val startTime: TextView
            val endTime: TextView
            val duration: TextView
            val distance: TextView
            val price: TextView

            val root: View

            init {
                // Define click listener for the ViewHolder's View.
                name = view.findViewById(R.id.hitem_name)
                startTime = view.findViewById(R.id.hitem_start)
                endTime = view.findViewById(R.id.hitem_end)
                duration = view.findViewById(R.id.hitem_duration)
                distance = view.findViewById(R.id.hitem_distance)
                price = view.findViewById(R.id.hitem_price)
                root = view
            }
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_history, viewGroup, false)

            return ViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.name.text = rents.rents.get(position).name
            viewHolder.startTime.text = rents.rents.get(position).startTime
            viewHolder.endTime.text = rents.rents.get(position).endTime
            viewHolder.duration.text = rents.rents.get(position).duration
            viewHolder.distance.text = rents.rents.get(position).distance
            viewHolder.price.text = rents.rents.get(position).price
            /*
            viewHolder.root.setOnClickListener {
                Toast.makeText(viewHolder.root.context,
                    "Row selected %d".format(position),
                    Toast.LENGTH_LONG).show()
            }*/
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = rents.rents.size

    }
