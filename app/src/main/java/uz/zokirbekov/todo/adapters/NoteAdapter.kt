package uz.zokirbekov.todo.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import uz.zokirbekov.todo.R
import uz.zokirbekov.todo.models.Note
import uz.zokirbekov.todo.util.ItemClickListener
import uz.zokirbekov.todo.util.SqlWorker

class NoteAdapter(var context:Context,var notes:ArrayList<Note>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    var inflater = LayoutInflater.from(context)
    var itemClickListiner:ItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NoteViewHolder {
        var view = inflater.inflate(R.layout.layout_note,parent,false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder?, position: Int) {
        holder?.textTitle?.text = notes.get(position).title
        holder?.textNote?.text = notes.get(position).note
        holder?.textCreateDate?.text = "Create Date : ${SqlWorker.dateToString(notes.get(position).create_date)}"
        holder?.textUpdateDate?.text = "Update Date : ${SqlWorker.dateToString(notes.get(position).update_date)}"
        holder?.delete?.setOnClickListener { itemClickListiner?.OnDeleteClick(notes.get(position).id) }
        var animation = AnimationUtils.loadAnimation(context,R.anim.item_animation_fall_down)
        holder?.itemView?.startAnimation(animation)
        holder?.itemView?.setOnClickListener {
            itemClickListiner?.OnItemClick(notes.get(position),position)
        }
    }

    class NoteViewHolder(view: View?) : RecyclerView.ViewHolder(view)
    {
        var textTitle: TextView? = view?.findViewById(R.id.textTitle)
        var textNote: TextView? = view?.findViewById(R.id.textNote)
        var textCreateDate:TextView? = view?.findViewById(R.id.text_create_date)
        var textUpdateDate:TextView? = view?.findViewById(R.id.text_update_date)
        var delete: ImageView? = view?.findViewById(R.id.delete)
    }
}

