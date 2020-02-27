package hos.houns.pockmp.presentation.tasks

import hos.houns.pockmp.domain.defaultDispatcher
import hos.houns.pockmp.domain.model.Task
import hos.houns.pockmp.domain.uiDispatcher
import hos.houns.pockmp.domain.usecase.GetTasks
import hos.houns.pockmp.domain.usecase.UseCase
import hos.houns.pockmp.presentation.BasePresenter
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


/**
 * Created by hospicehounsou on 26,February,2020
 * Dakar, Senegal.
 */
class TAsksPresenter(
    private val getTasks: GetTasks,
    coroutineContext: CoroutineContext = defaultDispatcher
) : BasePresenter<TaskView>(coroutineContext) {

    override fun onViewAttached(view: TaskView) {
        view.setLoadingVisible(true)
        getTasks()
    }

    private fun getTasks() {
        scope.launch {
            getTasks(
                UseCase.None,
                onSuccess = { view?.setTasks(it) },
                onFailure = { view?.showTasksFailedToLoad() }
            )
            launch(uiDispatcher) {
                view?.setLoadingVisible(false)
            }

        }
    }
}

interface TaskView {

    fun setTasks(tasks: List<Task>)

    fun showTasksFailedToLoad()

    fun setLoadingVisible(visible: Boolean)
}