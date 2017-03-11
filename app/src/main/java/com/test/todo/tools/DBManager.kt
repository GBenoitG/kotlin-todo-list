package com.test.todo.tools

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import java.util.*

/**
 * Created by Benoit on 28/02/2017.
 */
class DBManager private constructor(context: Context){

    companion object {

        lateinit var instance: DBManager

        fun createInstance(context: Context) {
            DBManager(context)
        }
    }

    private val DB_VERSION: Long = 2

    var realm: Realm? = null

    init {
        instance = this
        Realm.init(context)
        val config = RealmConfiguration.Builder()
                .schemaVersion(DB_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build()
        try {
            realm = Realm.getInstance(config)
        } catch (e: IllegalArgumentException) {
            Realm.deleteRealm(config)
            realm = Realm.getInstance(config)
        }
    }

    fun insert(realmObject: RealmObject): RealmObject? {
        realm?.beginTransaction()
        val obj = realm?.copyToRealmOrUpdate(realmObject)
        realm?.commitTransaction()
        return obj
    }

    fun insert(realmObjects: ArrayList<out RealmObject>){
        realm?.beginTransaction()
        realm?.copyToRealmOrUpdate(realmObjects)
        realm?.commitTransaction()
    }

    fun updateObject(transaction: Realm.Transaction) {
        realm?.executeTransaction(transaction)
    }

    fun deleteObject(realmObject: RealmObject) {
        realm?.beginTransaction()
        realmObject.deleteFromRealm()
        realm?.commitTransaction()
    }

}