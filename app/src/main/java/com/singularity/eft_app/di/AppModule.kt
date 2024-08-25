package com.singularity.eft_app.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.singularity.eft_app.Item.ItemRepository
import com.singularity.eft_app.SearchHistoryManager
import com.singularity.eft_app.UI.authorization.UserRepository
import com.singularity.eft_app.room.EFTAppDatabase
import com.singularity.eft_app.room.ItemDao
import com.google.firebase.auth.FirebaseAuth
import com.singularity.eft_app.apollo.ApolloItemClient
import com.singularity.eft_app.apollo.ItemClient

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
    fun provideItemRepository(dao: ItemDao,searchHistoryManager: SearchHistoryManager,apolloItemClient: ItemClient) = ItemRepository(dao, searchHistoryManager,apolloItemClient)

    @Provides
    @Singleton
    fun provideSearchHistoryManager(context: Context) = SearchHistoryManager(context)

    @Provides
    @Singleton
    fun provideUserRepository(auth:FirebaseAuth) = UserRepository(auth)

    @Provides
    @Singleton
    fun provideFireBaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideApolloClient():ItemClient = ApolloItemClient(ApolloClient.Builder().serverUrl("https://api.tarkov.dev/graphql").build())

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.applicationContext

}

