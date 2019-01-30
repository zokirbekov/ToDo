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
import uz.zokirbekov.todo.adapter.NoteAdapter
import uz.zokirbekov.todo.adapter.VerticalSpaceItemDecoration
import uz.zokirbekov.todo.models.Note
import uz.zokirbekov.todo.util.SqlWorker

public class NotesListFragment : Fragment() {
    var notesList:RecyclerView? = null
    var addButton:FloatingActionButton? = null
    var notes:ArrayList<Note>? = null
    var sqlWorker:SqlWorker? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fragment_notes_list,container,false)
        notesList = view?.findViewById(R.id.noteListview)
        addButton = view?.findViewById(R.id.addButton)
        sqlWorker = SqlWorker(context)
        init()
        var adapter = NoteAdapter(context,notes!!)
        notesList?.layoutManager = LinearLayoutManager(context,LinearLayout.VERTICAL,false)
        notesList?.addItemDecoration(VerticalSpaceItemDecoration(100))
        notesList?.adapter = adapter

        return view
    }
    private fun init()
    {
        sqlWorker?.db = sqlWorker?.writableDatabase
        notes = sqlWorker?.selectAll()
    }
}