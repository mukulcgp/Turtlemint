package com.tur.turtlemint.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tur.turtlemint.data.source.local.dao.IssueDao
import com.tur.turtlemint.data.source.local.entity.IssueEntity

/**
 * To manage data items that can be accessed, updated
 * & maintain relationships between them
 *
 * @Created by MUKUL
 */
@Database(entities = [IssueEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val issueDao: IssueDao

    companion object {
        const val DB_NAME = "TurtleDatabase.db"
    }
}
