package hos.houns.pockmp

import android.util.Log
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.logging.HttpLoggingInterceptor


/**
 * Created by hospicehounsou on 26,February,2020
 * Dakar, Senegal.
 */
actual object PlatformServiceLocator {

    actual val httpClientEngine: HttpClientEngine by lazy {
        Log.e(PlatformServiceLocator::class.java.name, "httpClientEngine")
        OkHttp.create {
            val networkInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            addInterceptor(interceptor = networkInterceptor)
            //addNetworkInterceptor(networkInterceptor)
        }
    }
}