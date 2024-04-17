package com.example.kotlinpracticemirea.di

import android.app.Application
import androidx.room.Room
import com.example.kotlinpracticemirea.Item.ItemRepository
import com.example.kotlinpracticemirea.SearchHistoryManager
import com.example.kotlinpracticemirea.room.FleaMarketDatabase
import com.example.kotlinpracticemirea.room.ItemDao
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
    ) = Room.databaseBuilder(app , FleaMarketDatabase::class.java , "items-database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: FleaMarketDatabase) = db.itemDao()


    @Provides
    @Singleton
    fun provideItemRepository(dao: ItemDao,searchHistoryManager: SearchHistoryManager) = ItemRepository(dao, searchHistoryManager)

    @Provides
    @Singleton
    fun provideSearchHistoryManager() = SearchHistoryManager()


}

