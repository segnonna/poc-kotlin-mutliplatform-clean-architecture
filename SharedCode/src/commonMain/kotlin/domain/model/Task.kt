package hos.houns.pockmp.domain.model

import hos.houns.pockmp.data.entity.TaskEntity


/**
 * Created by hospicehounsou on 26,February,2020
 * Dakar, Senegal.
 */

data class Task(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean)


fun TaskEntity.toModel()=Task(id =id,userId = userId,title = title,completed = completed)

fun List<TaskEntity>.toModels(): MutableList<Task> {
    val mutableList = mutableListOf<Task>()
    this.forEach {
        mutableList.add(Task(id = it.id, userId = it.userId, title = it.title,completed = it.completed))
    }

    return mutableList
}