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
import android.widget.ListView
import uz.zokirbekov.todo.R
import uz.zokirbekov.todo.adapter.NoteAdapter
import uz.zokirbekov.todo.adapter.ScheduleAdapter
import uz.zokirbekov.todo.adapter.VerticalSpaceItemDecoration
import uz.zokirbekov.todo.models.Note
import uz.zokirbekov.todo.models.Schedule
import uz.zokirbekov.todo.util.DialogDissmisListener
import uz.zokirbekov.todo.util.SqlWorker

class ScheduleFragment : Fragment(), DialogDissmisListener {


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
        updateListView()

        addButton?.setOnClickListener {
            showAddFragment()
        }

        return view
    }

    private fun init()
    {
        sqlWorker?.db = sqlWorker?.writableDatabase
        schedule = sqlWorker?.selectAllSchdules()
    }

    private fun showAddFragment()
    {
        AddScheduleDialogFragment.newInstanse(null,sqlWorker,this).show(fragmentManager,"INSERT_SCHEDULE_FRAGMENT")
    }


    private fun updateListView()
    {
        schedule = sqlWorker?.selectAllSchdules()
        var adapter = ScheduleAdapter(context,schedule!!)
        adapter.itemClickListiner = object : ScheduleAdapter.ItemClickListiner
        {
            override fun OnClick(schedule: Schedule, position: Int) {
                AddScheduleDialogFragment.newInstanse(schedule,sqlWorker,this@ScheduleFragment,true).show(fragmentManager,"UPDATE_SCHEDULE_FRAGMENT")
            }
        }
        listView?.adapter = adapter
    }

    override fun OnDissmis() {
        updateListView() //To change body of created functions use File | Settings | File Templates.
    }
}