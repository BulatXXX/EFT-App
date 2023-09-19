package com.example.kotlinpracticemirea.di

import android.app.Application
import android.app.SharedElementCallback
import androidx.room.Room
import com.example.kotlinpracticemirea.FleaMarketDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton

import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: FleaMarketDatabase.Callback
    ) = Room.databaseBuilder(app , FleaMarketDatabase::class.java , "items-database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun provideDao(db: FleaMarketDatabase) = db.fleaMarketDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope