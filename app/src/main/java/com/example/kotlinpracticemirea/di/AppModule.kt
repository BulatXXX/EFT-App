package com.example.kotlinpracticemirea.di

import android.app.Application
import androidx.room.Room
import com.example.kotlinpracticemirea.room.FleaMarketDao
import com.example.kotlinpracticemirea.room.FleaMarketDatabase
import com.example.kotlinpracticemirea.FleaMarketItemRepository
import com.example.kotlinpracticemirea.ItemRepository
import com.example.kotlinpracticemirea.retrofit.ItemApi
import com.example.kotlinpracticemirea.retrofit.ItemInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton

import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
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
    @Singleton
    fun provideDao(db: FleaMarketDatabase) = db.fleaMarketDao()

    @Provides
    @Singleton
    fun provideRepository(dao: FleaMarketDao) = FleaMarketItemRepository(dao)

    @Provides
    @Singleton
    fun provideItemRepository() = ItemRepository()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideRetrofitInstance(): ItemApi {
        val ItemService: ItemApi by lazy {
            Retrofit
                .Builder()
                .baseUrl("https://api.tarkov.dev/graphql/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build().create(ItemApi::class.java)
        }
        return ItemService
    }



}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope