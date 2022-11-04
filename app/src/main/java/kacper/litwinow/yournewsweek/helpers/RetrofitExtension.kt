package kacper.litwinow.yournewsweek.helpers

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kacper.litwinow.yournewsweek.common.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitExtension {

    inline fun <reified T> createAqiWebService(
        url: String,
        @ApplicationContext context: Context
    ): T =
        create(url, createOkHttpClient(context))

    inline fun <reified T> createAqiWebService(@ApplicationContext context: Context): T =
        createAqiWebService(BASE_URL, context)

    inline fun <reified T> create(baseUrl: String, okHttpClient: OkHttpClient): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(T::class.java)

    fun createOkHttpClient(
        @ApplicationContext context: Context,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            addInterceptor(logging)
            addInterceptor(provideChuckerInterceptor(context))
        }.build()

    private fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        return with(ChuckerInterceptor.Builder(context)) {
            collector(chuckerCollector)
            maxContentLength(250_000L)
            build()
        }
    }
}