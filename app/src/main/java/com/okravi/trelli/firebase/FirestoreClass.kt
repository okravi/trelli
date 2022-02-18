package com.okravi.trelli.firebase

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.okravi.trelli.activities.MainActivity
import com.okravi.trelli.activities.SignInActivity
import com.okravi.trelli.activities.SignUpActivity

import com.okravi.trelli.models.User
import com.okravi.trelli.utils.Constants

class FirestoreClass {

    private val mFirestore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, userInfo: User){
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserId()).set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }.addOnFailureListener {
                e->
                Log.e(activity.javaClass.simpleName, "Error registering user")
            }
    }

    fun signInUser(activity: Activity){
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val loggedInUser = document.toObject(User::class.java)!!

                when(activity){
                    is SignInActivity ->{
                        activity.signInSuccess(loggedInUser)
                    }

                    is MainActivity ->{
                        activity.updateNavigationUserDetails(loggedInUser)
                    }
                }

            }.addOnFailureListener {
                    e->
                Log.e(activity.javaClass.simpleName, "Error signing in user")
            }
    }

    fun getCurrentUserId(): String{

        var currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""

        if (currentUser != null){
            currentUserID = currentUser.uid
        }

        return currentUserID
    }
}