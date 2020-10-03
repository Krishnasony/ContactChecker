package com.example.contactchecker.di

import android.app.Application
import androidx.room.Room
import com.example.contactchecker.room.ContactCheckerDb
import com.example.contactchecker.room.dao.ContactCheckerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideContactDatabase(application: Application): ContactCheckerDb {
        return Room
            .databaseBuilder(application, ContactCheckerDb::class.java, "contacts.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideContactDao(appDatabase: ContactCheckerDb): ContactCheckerDao {
        return appDatabase.contactCheckerDao
    }

}
