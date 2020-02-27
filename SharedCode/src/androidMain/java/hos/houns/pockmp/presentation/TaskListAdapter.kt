package hos.houns.pockmp.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hos.houns.pockmp.R
import hos.houns.pockmp.domain.model.Task
import kotlinx.android.extensions.LayoutContainer


/**
 * Created by hospicehounsou on 27,February,2020
 * Dakar, Senegal.
 */
class TaskListAdapter(
    private val tasks: List<Task>,
    private val itemClick: (Task) -> Unit
) :
    RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return ViewHolder(view, itemClick)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTask(tasks[position])
    }


    class ViewHolder(override val containerView: View, private val itemClick: (Task) -> Unit) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindTask(task: Task) {
            with(task) {
                containerView.findViewById<TextView>(R.id.task_textview).text = task.title
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

    override fun getItemCount() = tasks.size
}