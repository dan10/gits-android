package com.danieloliveira.gistsandroid.data.data_local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danieloliveira.gistsandroid.data.data_local.daos.GistDao
import com.danieloliveira.gistsandroid.data.data_local.model.FileEntity
import com.danieloliveira.gistsandroid.data.data_local.model.GistEntity
import com.danieloliveira.gistsandroid.data.data_local.model.OwnerEntity

@Database(entities = [FileEntity::class, GistEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun gistDao(): GistDao
}