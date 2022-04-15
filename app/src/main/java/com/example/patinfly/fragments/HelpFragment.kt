package com.example.patinfly.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.patinfly.R

private const val FIRST_PHOTO = 1
private const val MAX_PHOTO = 5

class HelpFragment : Fragment() {
    private var currentPic = FIRST_PHOTO
    private var maxPic = MAX_PHOTO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_help, container, false)

        val photo = view.findViewById<ImageView>(R.id.help_picture)

        view.findViewById<Button>(R.id.help_leftleft).setOnClickListener{
            currentPic = 1
            updatePhoto(photo)
        }
        view.findViewById<Button>(R.id.help_left).setOnClickListener{
            if(currentPic > 1) currentPic--
            updatePhoto(photo)
        }
        view.findViewById<Button>(R.id.help_right).setOnClickListener{
            if(currentPic < maxPic) currentPic++
            updatePhoto(photo)
        }
        view.findViewById<Button>(R.id.help_rightright).setOnClickListener{
            currentPic = maxPic
            updatePhoto(photo)
        }

        return view
    }

    private fun updatePhoto(imageView: ImageView){
        val name = "help_image$currentPic"
        val id = this.resources.getIdentifier(name, "drawable", context?.packageName)
        val drawable = context?.let { ContextCompat.getDrawable(it, id) }
        imageView.setImageDrawable(drawable)
    }

}