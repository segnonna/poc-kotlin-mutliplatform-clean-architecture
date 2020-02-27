package hos.houns.pockmp

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.ios.Ios


/**
 * Created by hospicehounsou on 26,February,2020
 * Dakar, Senegal.
 */

@ThreadLocal
actual object PlatformServiceLocator {
    actual val httpClientEngine: HttpClientEngine by lazy { Ios.create() }
}