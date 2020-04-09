package com.lottolike.jaery.lotto.util

import android.content.ContentValues.TAG
import android.content.Context
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

    fun updateLottoNumber(){
        val db = FirebaseFirestore.getInstance()
        val sfDocRef = db.collection("LottoNum").document("Lotto")

        db.runTransaction { transaction ->
            val snapshot = transaction.get(sfDocRef)

            // Note: this could be done without a transaction
            //       by updating the population using FieldValue.increment()
            val newPopulation = snapshot.getLong("Num") ?: 0
            transaction.update(sfDocRef, "Num", newPopulation + 1)

            // Success
            null
        }.addOnSuccessListener { Log.d(TAG, "Transaction success!") }
                .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }
    }

    fun uploadLottoResult(lottoNo:Int,level : Int){
        val db = FirebaseFirestore.getInstance()
        val sfDocRef = db.collection("LottoRank").document("Lotto$lottoNo")

        db.runTransaction { transaction ->
            val snapshot = transaction.get(sfDocRef)

            // Note: this could be done without a transaction
            //       by updating the population using FieldValue.increment()
            val newPopulation = snapshot.getLong("${level}등") ?: 0
            transaction.update(sfDocRef, "${level}등", newPopulation + 1)

            // Success
            null
        }.addOnSuccessListener { Log.d(TAG, "Transaction success!") }
                .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }
    }

    fun getLottoRank(lottoNo:Int,play: (Boolean,LinkedHashMap<String,Float>?) -> Unit){
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("LottoRank").document("Lotto$lottoNo")

        docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                       val hash = linkedMapOf(
                               "1등" to (document.getLong("1등") ?: 0).toFloat(),
                               "2등" to (document.getLong("2등") ?: 0).toFloat(),
                               "3등" to (document.getLong("3등") ?: 0).toFloat(),
                               "4등" to (document.getLong("4등") ?: 0).toFloat(),
                               "5등" to (document.getLong("5등") ?: 0).toFloat()
                       )
                       play(true,hash)
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    play(false,null)
                    Log.d(TAG, "get failed with ", exception)
                }
    }
}