package com.vitorthemyth.tracker_data.di

import android.app.Application
import androidx.room.Room
import com.example.tracker_domain.repository.TrackerRepository
import com.vitorthemyth.tracker_data.local.TrackerDataBase
import com.vitorthemyth.tracker_data.remote.OpenFoodApi
import com.vitorthemyth.tracker_data.repository.TrackerRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TrackerDataModule {

    @Provides
    @Singleton
    fun provideClientOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()
    }

    @Provides
    @Singleton
    fun providesOpenFoodApi(client: OkHttpClient): OpenFoodApi {
        return Retrofit.Builder()
            .baseUrl(OpenFoodApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesTrackerDataBase(app: Application): TrackerDataBase {
        return Room.databaseBuilder(
            context = app,
            klass = TrackerDataBase::class.java,
            name = "tracker_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesTrackerRepository(
        api: OpenFoodApi,
        db: TrackerDataBase
    ): TrackerRepository {
        return TrackerRepositoryImp(
            dao = db.dao,
            api = api
        )
    }
}