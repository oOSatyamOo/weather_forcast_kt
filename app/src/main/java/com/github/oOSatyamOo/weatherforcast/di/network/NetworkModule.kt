package com.github.oOSatyamOo.weatherforcast.di.network

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.github.oOSatyamOo.weatherforcast.BuildConfig
import com.github.oOSatyamOo.weatherforcast.data.local.ForecastDao
import com.github.oOSatyamOo.weatherforcast.data.local.WeatherDatabase
import com.github.oOSatyamOo.weatherforcast.repo.WeatherApiService
import com.github.oOSatyamOo.weatherforcast.repo.WeatherRepo
import com.github.oOSatyamOo.weatherforcast.repo.WeatherRepositoryImpl
import com.github.oOSatyamOo.weatherforcast.repo.usecase.GetWeatherForecastUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): String = BuildConfig.OPENWEATHER_BASE_URL+"data/2.5/"

    @Provides
    @Singleton
    @Named("apiKey")
    fun provideApiKey(): String = BuildConfig.OPENWEATHER_API_KEY

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Log.d("WeatherAPI", message)   // All logs will appear with tag "WeatherAPI"
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY   // Logs full request + response body
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        customInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(customInterceptor)           // Your Log.e() logs
            .addInterceptor(httpLoggingInterceptor)      // Full detailed logs
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(): WeatherApiService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.OPENWEATHER_BASE_URL+"data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)

//    @Provides
//    @Singleton
//    @Named("otherBaseUrl")
//    fun provideRetrofitWithDifferentBaseUrl(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl("https://api.openweathermap.org/data/2.5/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }


    @Provides
    @Singleton
    @Named("heavyAuthLoadURL")
    fun provideRetrofitWithHeavyAuthLoadURL(): Retrofit {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(180, TimeUnit.SECONDS) // Connect timeout
            .readTimeout(180, TimeUnit.SECONDS) // Read timeout
            .writeTimeout(180, TimeUnit.SECONDS) // Write timeout
//            .addInterceptor(loggingInterceptor) // Add logging interceptor (optional)
            .build()
        return Retrofit.Builder()
            .baseUrl("otherHeavyAuthLoadURL")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    @Provides
    @Singleton
    @Named("otherBaseUrl")
    fun provideApiServiceWithDifferentBaseUrl(@Named("otherBaseUrl") retrofit: Retrofit): WeatherRepo {
        return retrofit.create(WeatherRepo::class.java)
    }

    @Provides
    @Singleton
    fun provideGetWeatherForecastUseCase(repository: WeatherRepo): GetWeatherForecastUseCase =
        GetWeatherForecastUseCase(repository)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(context, WeatherDatabase::class.java, "weather_db")
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides
    @Singleton
    fun provideDao(db: WeatherDatabase) = db.forecastDao()


    @Provides
    @Singleton
    fun provideRepository(
        api: WeatherApiService,
        dao: ForecastDao,
        @Named("apiKey") key: String
    ): WeatherRepo = WeatherRepositoryImpl(api, dao, key)
}