package com.example.notifyfirebase

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var title=findViewById<EditText>(R.id.title)
        var descreiption=findViewById<EditText>(R.id.description)
        var  button=findViewById<Button>(R.id.sendNotification)



        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.d("Rahul", it.toString())

            val user = hashMapOf(
                "token" to it.toString()
            )
            FirebaseFirestore.getInstance().collection("user")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                }
        }

        val tokenList: List<String> =
            mutableListOf(
                "eKvj-FmORUihm3Dp2EO21G:APA91bG0I0FKBCI93iCsiDrrJvgOrLQmwsd5YSlKuGGcPSCp5UP72OsJNbNUB80qVZFnGlHaNpVM2iG5LwgW2Yvl5E63wwwVDy5AjlJRztEYFJ0PZgb378_iGRVHoDfD2C3XIhuRjv3U",
                "ff218xgeQUexDuRHbGeb-R:APA91bGEb15nb4D93jPjUGbFviMuiXesJAHm1ub4ywJdUIhfvPPHt-E6Em4mYMXfOqLqFwEyed277N-qtYvrRa6g2wGYP4YdA6vOdbcxtioWCw2nHsCo0E6zlDYtRwns2vlDbB7tWm-p"
                ,"dJXvK72rSAGwyf-GC2HDVM:APA91bG48VB0qhOOK3tvqhrE8_RxhMuknP_MYYobLieBBgS1QdSvZ3od3dShz3O7XKFiMxIf79Nc8bwVWXOZ5pb60iV2t0fESzC6cwurJ8Q_rrDx1CqEE8lQO84DQGsOTZWcEbhGAC2G")
        val headerMap =
            hashMapOf<String, String>("Authorization" to "key=AAAAl8F3tho:APA91bF5Ap7gNG8dm8U7fTAyoTZ7iQvYfp8XN-yRaiUFlp9XVyjK6svPO_2BQwq_wzjNtzoqG05KJqNDBKAvXAm9icnfqXK6ZGOlN3v7xyFpRL7lJqkWQy5Pz9xAPWnieM-Bvl5gPweA")



        button.setOnClickListener{

            val notificationData = NotificationData(
                Notification(
                    "notoficationsss",
                    title.text.toString(),
                    true,
                    descreiption.text.toString()
                ), tokenList
            )
            ApiCalling.Create().send(headerMap, notificationData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val notification = it
                }

        }


    }
}
