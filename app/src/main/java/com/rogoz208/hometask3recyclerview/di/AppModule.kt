package com.rogoz208.hometask3recyclerview.di

import com.rogoz208.hometask3recyclerview.data.repos.MemoryCacheContactsMockRepoImpl
import com.rogoz208.hometask3recyclerview.domain.repos.ContactsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContactsRepo(): ContactsRepo = MemoryCacheContactsMockRepoImpl()
}