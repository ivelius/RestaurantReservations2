package com.example.yanbraslavsky.restaurantreservations.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.yanbraslavsky.restaurantreservations.database.enteties.TableEntity
import io.reactivex.Single

@Dao
interface TableDao {

    @Query("SELECT * FROM TableEntity")
    fun getAllTables(): Single<List<TableEntity>>

    @Insert
    fun insert(table: TableEntity)
}