package hos.houns.pockmp.domain.usecase

import hos.houns.pockmp.data.TasksApi
import hos.houns.pockmp.domain.model.*
import kotlinx.serialization.ImplicitReflectionSerializer


/**
 * Created by hospicehounsou on 26,February,2020
 * Dakar, Senegal.
 */
class GetTasks(private val taskApi: TasksApi) : UseCase<List<Task>, UseCase.None>() {

    @ImplicitReflectionSerializer
    override suspend fun run(params: None): Either<Exception, List<Task>> {
        return try {
            val tasks = taskApi.getAllTasks().toModels()
            Success(tasks)
        } catch (e: Exception) {
            println(e)
            Failure(e)
        }
    }
}