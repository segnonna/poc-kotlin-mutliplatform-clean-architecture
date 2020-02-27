package hos.houns.pockmp.domain

import kotlin.coroutines.CoroutineContext


/**
 * Created by hospicehounsou on 26,February,2020
 * Dakar, Senegal.
 */

expect val defaultDispatcher: CoroutineContext

expect val uiDispatcher: CoroutineContext