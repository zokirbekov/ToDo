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
import uz.zokirbekov.todo.adapters.NoteAdapter
import uz.zokirbekov.todo.adapters.VerticalSpaceItemDecoration
import uz.zokirbekov.todo.models.Note
import uz.zokirbekov.todo.util.DialogDissmisListener
import uz.zokirbekov.todo.util.ItemClickListener
import uz.zokirbekov.todo.util.SqlWorker

public class NotesListFragment : Fragment(), DialogDissmisListener, ItemClickListener<Note>{

    override fun OnItemClick(obj: Note, position: Int) {
        AddNoteDialogFragmnet.newInstanse(obj,sqlWorker,this,true).show(fragmentManager,"UPDATE_NOTE_DIALOG_FRAGMENT")
    }

    override fun OnDeleteClick(obj: Note) {
        sqlWorker?.deleteNote(obj.id)
        updateListView()
    }

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
        notesList?.layoutManager = LinearLayoutManager(context,LinearLayout.VERTICAL,false)
        notesList?.addItemDecoration(VerticalSpaceItemDecoration(100))
        notesList?.adapter = NoteAdapter(context, notes!!)
        (notesList?.adapter as? NoteAdapter)?.itemClickListiner = this
        addButton?.setOnClickListener {
            AddNoteDialogFragmnet.newInstanse(null,sqlWorker,this).show(fragmentManager,"INSERT_NOTE_DIALOG_FRAGMENT")
        }
        return view
    }

    private fun init()
    {
        sqlWorker?.db = sqlWorker?.writableDatabase
        notes = sqlWorker?.selectAllNotes()
    }

    override fun OnDissmis() {
        updateListView()
    }

    private fun updateListView()
    {
        notes = sqlWorker?.selectAllNotes()
        (notesList?.adapter as? NoteAdapter)?.notes = notes!!
        notesList?.adapter?.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        sqlWorker?.dispose()
    }
}