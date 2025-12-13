package com.example.hacklab.config

import android.content.Context
import com.cloudinary.android.MediaManager

object CloudinaryConfig {
    fun init(context: Context) {
        val config = HashMap<String, String>()
        config["cloud_name"] = "drowg7pgh"
        MediaManager.init(context, config)
    }
}