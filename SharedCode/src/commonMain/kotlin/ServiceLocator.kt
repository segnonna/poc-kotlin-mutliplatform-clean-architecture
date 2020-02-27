package hos.houns.pockmp

import hos.houns.pockmp.data.TasksApi
import hos.houns.pockmp.domain.usecase.GetTasks
import hos.houns.pockmp.presentation.tasks.TAsksPresenter
import io.ktor.client.engine.HttpClientEngine
import kotlin.native.concurrent.ThreadLocal


/**
 * Created by hospicehounsou on 26,February,2020
 * Dakar, Senegal.
 */

expect object PlatformServiceLocator {

    val httpClientEngine: HttpClientEngine
}

@ThreadLocal
object ServiceLocator {

    val taskApi by lazy { TasksApi(PlatformServiceLocator.httpClientEngine) }

    val getTasks = GetTasks(taskApi)

    val tasksPresenter: TAsksPresenter
        get() = TAsksPresenter(getTasks)

}