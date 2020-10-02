package com.example.contactchecker.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "contact")
@Parcelize
data class ContactModel(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id:Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "number") val number: String,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "nickName") val nickName: String?
):Parcelable
