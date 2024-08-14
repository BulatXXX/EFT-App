package com.singularity.eft_app.di

import android.app.Application
import androidx.room.Room
import com.singularity.eft_app.Item.ItemRepository
import com.singularity.eft_app.SearchHistoryManager
import com.singularity.eft_app.fragments.authorization.UserRepository
import com.singularity.eft_app.room.EFTAppDatabase
import com.singularity.eft_app.room.ItemDao
import com.google.firebase.auth.FirebaseAuth
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
    fun provideDatabase(
        app: Application ,
    ) = Room.databaseBuilder(app , EFTAppDatabase::class.java , "items-database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: EFTAppDatabase) = db.itemDao()

    @Provides
    @Singleton
    fun provideItemRepository(dao: ItemDao,searchHistoryManager: SearchHistoryManager) = ItemRepository(dao, searchHistoryManager)

    @Provides
    @Singleton
    fun provideSearchHistoryManager() = SearchHistoryManager()

    @Provides
    @Singleton
    fun provideUserRepository(auth:FirebaseAuth) = UserRepository(auth)

    @Provides
    @Singleton
    fun provideFireBaseAuth() = FirebaseAuth.getInstance()
}

