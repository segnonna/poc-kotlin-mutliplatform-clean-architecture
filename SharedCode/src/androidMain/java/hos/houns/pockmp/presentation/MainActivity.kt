package hos.houns.pockmp.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hos.houns.pockmp.R
import hos.houns.pockmp.ServiceLocator
import hos.houns.pockmp.domain.model.Task
import hos.houns.pockmp.presentation.tasks.TaskView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TaskView {
    private val presenter by lazy { ServiceLocator.tasksPresenter }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun setLoadingVisible(visible: Boolean) {
        if (visible) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun setTasks(tasks: List<Task>) {
        recycleview.visibility = View.VISIBLE
        recycleview.layoutManager = LinearLayoutManager(this)
        val adapter = TaskListAdapter(tasks) {
            Toast.makeText(this, "item clicked: ${it.title}", Toast.LENGTH_LONG).show()
        }
        recycleview.adapter = adapter
    }

    override fun showTasksFailedToLoad() {
        Log.e(MainActivity::class.java.name, "showTasksFailedToLoad")
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }
}