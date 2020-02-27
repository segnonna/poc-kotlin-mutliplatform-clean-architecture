package hos.houns.pockmp.data.entity

import kotlinx.serialization.Serializable


/**
 * Created by hospicehounsou on 26,February,2020
 * Dakar, Senegal.
 */

@Serializable
data class TaskEntity(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean)