package com.shahnoza.galereyabilanishlash.offlinedatabase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.shahnoza.galereyabilanishlash.models.ImageUser

import kotlin.collections.ArrayList

const val TABLE_NAME="images"
const val ID="id"
const val ABSOLUTE="absolutepath"
const val IMAGE="image"

class MyBase(context: Context):SQLiteOpenHelper(context,"myimage.db",null,1),Reja {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "create table $TABLE_NAME($ID integer primary key autoincrement not null,$ABSOLUTE text not null,$IMAGE blob not null)"
        db?.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {


    }

    override fun addImage(imageUser: ImageUser) {
    val writer=writableDatabase
        val contentValues=ContentValues()
        contentValues.put(ABSOLUTE,imageUser.absolutePath)
        contentValues.put(IMAGE,imageUser.image)
        writer.insert(TABLE_NAME,null,contentValues)
        writer.close()

    }

    override fun getAllImage(): ArrayList<ImageUser> {

        val list = ArrayList<ImageUser>()
        val query = "select * from $TABLE_NAME"
        val db =this.readableDatabase

        val cursor=db.rawQuery(query,null)
        if (cursor.moveToFirst()){
            do{
                val imageUser=ImageUser()
                imageUser.id=cursor.getInt(0)
                imageUser.absolutePath=cursor.getString(1)
                imageUser.image=cursor.getBlob(2)
     list.add(imageUser)
            }while (cursor.moveToNext())
        }
return list
    }
}