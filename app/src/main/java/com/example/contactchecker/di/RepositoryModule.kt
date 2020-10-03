package com.example.contactchecker.di

import android.app.Application
import com.example.contactchecker.repo.ContactListRepo
import com.example.contactchecker.repo.ContactRepo
import com.example.contactchecker.room.dao.ContactCheckerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideContactListRepository(
        contactCheckerDao: ContactCheckerDao,
        application: Application
    ): ContactListRepo {
        return ContactListRepo(application,contactCheckerDao)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideContactRepository(
        contactCheckerDao: ContactCheckerDao
    ): ContactRepo {
        return ContactRepo(contactCheckerDao)
    }

}