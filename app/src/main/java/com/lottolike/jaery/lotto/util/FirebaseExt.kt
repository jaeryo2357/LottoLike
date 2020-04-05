package com.lottolike.jaery.lotto.util

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseExt {
    fun getLottoNumber(play : (Int)->Unit){
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("LottoNum").document("Lotto")
        docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                        play(document.getLong("Num")!!.toInt())
                    } else {
                        Log.d(TAG, "No such document")
                        play(-1)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                    play(-1)
                }
    }
}