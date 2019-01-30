package uz.zokirbekov.todo.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.zokirbekov.todo.R

public class NotesListFragment : Fragment() {
    var notesList:RecyclerView? = null
    var addButton:FloatingActionButton? = null

    public override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fragment_notes_list,null,false)
        notesList = view?.findViewById(R.id.noteListview)
        addButton = view?.findViewById(R.id.addButton)

        return view
    }
}