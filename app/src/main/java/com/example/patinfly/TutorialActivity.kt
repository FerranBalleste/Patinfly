package com.example.patinfly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.patinfly.databinding.ActivityTutorialBinding

private const val FIRST_PHOTO = 1
private const val MAX_PHOTO = 5

class TutorialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTutorialBinding
    private var currentPic = FIRST_PHOTO
    private var maxPic = MAX_PHOTO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tutorialYesBtn.setOnClickListener(){
            binding.tutorialInnerLayout.visibility = View.GONE

            val picture = binding.tutorialPicture
            picture.visibility = View.VISIBLE

            binding.tutorialLeftleft.setOnClickListener{
                currentPic = 1
                updatePhoto(picture)
            }
            binding.tutorialLeft.setOnClickListener{
                if(currentPic > 1) currentPic--
                updatePhoto(picture)
            }
            binding.tutorialRight.setOnClickListener{
                if(currentPic < maxPic) currentPic++
                updatePhoto(picture)
            }
            binding.tutorialRightright.setOnClickListener{
                currentPic = maxPic
                updatePhoto(picture)
            }
        }

        binding.tutorialNoBtn.setOnClickListener(){
            val intent = Intent(this, NavigationDrawerActivity::class.java)
            startActivity(intent)
        }

    }

    private fun updatePhoto(imageView: ImageView){
        val name = "help_image$currentPic"
        val id = this.resources.getIdentifier(name, "drawable", this.packageName)
        val drawable = ContextCompat.getDrawable(this, id)
        imageView.setImageDrawable(drawable)
    }
}