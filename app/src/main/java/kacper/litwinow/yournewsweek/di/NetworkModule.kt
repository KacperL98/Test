package kacper.litwinow.yournewsweek.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kacper.litwinow.yournewsweek.api.service.ApiService
import kacper.litwinow.yournewsweek.domain.repository.Repository
import kacper.litwinow.yournewsweek.domain.repository.RepositoryImpl
import kacper.litwinow.yournewsweek.helpers.RetrofitExtension
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
     fun provideRetrofitProductsService(@ApplicationContext context: Context): ApiService =
        RetrofitExtension.createAqiWebService(context)

    @Provides
    @Singleton
    fun provideProductsRepository(api: ApiService): Repository {
        return RepositoryImpl(api)
    }
}