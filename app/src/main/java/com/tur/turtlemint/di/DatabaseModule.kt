package com.tur.turtlemint.di

import android.app.Application
import androidx.room.Room
import com.tur.turtlemint.data.source.local.AppDatabase
import com.tur.turtlemint.data.source.local.dao.IssueDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).allowMainThreadQueries().build()
    }

    @Provides
    internal fun provideIssueDao(appDatabase: AppDatabase): IssueDao {
        return appDatabase.issueDao
    }
}
