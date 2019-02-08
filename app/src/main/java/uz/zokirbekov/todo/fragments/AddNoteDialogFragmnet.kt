package uz.zokirbekov.todo.fragments

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.layout_note.*
import uz.zokirbekov.todo.R
import uz.zokirbekov.todo.models.Note
import uz.zokirbekov.todo.util.DialogDissmisListener
import uz.zokirbekov.todo.util.SqlWorker
import java.util.*

class AddNoteDialogFragmnet() : DialogFragment()
{
    var db:SqlWorker? = null
    var note:Note? = null
    var isUpdate:Boolean = false

    private var textTitle:TextInputEditText? = null
    private var textNote:TextInputEditText? = null
    private var addButton:Button? = null


    var dialogDissmisListener:DialogDissmisListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fragment_add_note,null,false)

        textTitle = view?.findViewById(R.id.textTitle)
        textNote = view?.findViewById(R.id.textNote)
        addButton = view?.findViewById(R.id.addButton)

        addButton?.setOnClickListener {
            if (isUpdate) {
                db?.updateNote(updateNote())
            }
            else
                db?.insertNote(newNote())

            dialogDissmisListener?.OnDissmis()
            dismiss()
        }

        dialog.window.setBackgroundDrawableResource(R.drawable.circle_border)
        showNote()
        return view
    }
    companion object {
        fun newInstanse(nt:Note?,db:SqlWorker?,listiner:DialogDissmisListener?,isUpdate:Boolean = false) : AddNoteDialogFragmnet
        {
            var fragment = AddNoteDialogFragmnet()
            fragment.note = nt
            fragment.db = db
            fragment.dialogDissmisListener = listiner
            fragment.isUpdate = isUpdate
            return fragment
        }
    }
    private fun showNote()
    {
        if (note != null)
        {
            textTitle?.setText(note?.title)
            textNote?.setText(note?.note)
            addButton?.setText("Update")
        }
    }
    private fun newNote() : Note
    {
        var note = Note()
        note.title = textTitle?.text.toString()
        note.note = textNote?.text.toString()
        note.create_date = Date()
        note.update_date = Date()
        return note
    }
    private fun updateNote() : Note
    {
        note?.title = textTitle?.text.toString()
        note?.note = textNote?.text.toString()
        note?.update_date = Date()
        return note!!
    }

}