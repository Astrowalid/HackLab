package com.example.hacklab.cloudinary

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.android.callback.ErrorInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest



fun uploadUserProfileImage(
    context: Context,       // ← nécessaire pour le Toast
    uri: Uri,
    onSuccess: (String) -> Unit
) {
    val uid = FirebaseAuth.getInstance().uid ?: return

    MediaManager.get().upload(uri)
        .unsigned("user_avatar") // ton preset Cloudinary
        .option("public_id", uid)
        .option("overwrite", true)
        .callback(object : UploadCallback {
            override fun onStart(requestId: String) {
                Toast.makeText(context, "Upload started...", Toast.LENGTH_SHORT).show()
            }

            override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                // Optionnel : tu peux afficher un Toast de progression si tu veux
            }

            override fun onSuccess(requestId: String, resultData: MutableMap<Any?, Any?>) {
                val imageUrl = resultData["secure_url"] as String

                // Met à jour le profil Firebase Auth
                val user = FirebaseAuth.getInstance().currentUser
                val profileUpdates = userProfileChangeRequest {
                    photoUri = Uri.parse(imageUrl)
                }
                user?.updateProfile(profileUpdates)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Image uploaded and profile updated!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Profile update failed!", Toast.LENGTH_SHORT).show()
                        }
                    }

                onSuccess(imageUrl)
            }

            override fun onError(requestId: String, error: ErrorInfo) {
                Toast.makeText(context, "Upload error: ${error.description}", Toast.LENGTH_LONG).show()
            }

            override fun onReschedule(requestId: String, error: ErrorInfo) {
                Toast.makeText(context, "Upload rescheduled: ${error.description}", Toast.LENGTH_LONG).show()
            }
        })
        .dispatch()
}
