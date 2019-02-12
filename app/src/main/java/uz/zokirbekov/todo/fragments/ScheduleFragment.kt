package uz.zokirbekov.todo.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import uz.zokirbekov.todo.R
import uz.zokirbekov.todo.adapters.ScheduleAdapter
import uz.zokirbekov.todo.adapters.VerticalSpaceItemDecoration
import uz.zokirbekov.todo.models.Schedule
import uz.zokirbekov.todo.util.DialogDissmisListener
import uz.zokirbekov.todo.util.ItemClickListener
import uz.zokirbekov.todo.util.SqlWorker

class ScheduleFragment : Fragment(), DialogDissmisListener, ItemClickListener {
    override fun <T> OnItemClick(obj: T, position: Int) {
        AddScheduleDialogFragment.newInstanse((obj as Schedule),sqlWorker,this,true).show(fragmentManager,"UPDATE_SCHEDULE_DIALOG_FRAGMENT")
    }

    override fun OnDeleteClick(id: Int) {
        sqlWorker?.deleteSchdule(id)
        updateListView()
    }


    var listView:RecyclerView? = null
    var addButton:FloatingActionButton? = null
    var schedule:ArrayList<Schedule>? = null
    var sqlWorker: SqlWorker? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fragment_schedule,container,false)

        listView = view?.findViewById(R.id.scheduleListView)
        addButton = view?.findViewById(R.id.addButton)
        sqlWorker = SqlWorker(context)
        init()
        listView?.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL,false)
        listView?.addItemDecoration(VerticalSpaceItemDecoration(100))
        listView?.adapter = ScheduleAdapter(context,schedule!!)
        (listView?.adapter as? ScheduleAdapter)?.itemClickListiner = this
        updateListView()

        addButton?.setOnClickListener {
            AddScheduleDialogFragment.newInstanse(Schedule(),sqlWorker,this).show(fragmentManager,"INSERT_SCHEDULE_FRAGMENT")
        }

        return view
    }

    private fun init()
    {
        sqlWorker?.db = sqlWorker?.writableDatabase
        schedule = sqlWorker?.selectAllSchdules()
    }

    private fun updateListView()
    {
        schedule = sqlWorker?.selectAllSchdules()
        (listView?.adapter as? ScheduleAdapter)?.schedules = schedule!!
        listView?.adapter?.notifyDataSetChanged()
    }

    override fun OnDissmis() {
        updateListView()
    }

    override fun onDestroy() {
        super.onDestroy()
        sqlWorker?.dispose()
    }
}