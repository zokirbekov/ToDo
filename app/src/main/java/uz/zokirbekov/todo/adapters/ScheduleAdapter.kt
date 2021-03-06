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
import uz.zokirbekov.todo.models.Schedule
import uz.zokirbekov.todo.util.ItemClickListener
import uz.zokirbekov.todo.util.SqlWorker

class ScheduleAdapter(var context:Context, var schedules:ArrayList<Schedule>) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>()
{
    var inflater = LayoutInflater.from(context)
    var itemClickListiner: ItemClickListener<Schedule>? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ScheduleViewHolder {
        val view = inflater.inflate(R.layout.layout_schedule,parent,false)
        return ScheduleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return schedules.size
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder?, position: Int) {
        holder?.textTitle?.text = schedules.get(position).title
        holder?.textTime?.text = SqlWorker.timestampToString(schedules.get(position).time)
        holder?.textCreateDate?.text = "Create Date : ${SqlWorker.dateToString(schedules.get(position).create_date)}"
        holder?.textUpdateDate?.text = "Update Date : ${SqlWorker.dateToString(schedules.get(position).update_date)}"
        var animation = AnimationUtils.loadAnimation(context,R.anim.item_animation_fall_down)
        holder?.itemView?.startAnimation(animation)
        holder?.itemView?.setOnClickListener {
            itemClickListiner?.OnItemClick(schedules.get(position),position)
        }
        holder?.delete?.setOnClickListener { itemClickListiner?.OnDeleteClick(schedules.get(position)) }
    }

    class ScheduleViewHolder(var view: View) : RecyclerView.ViewHolder(view)
    {
        var textTitle: TextView? = view.findViewById(R.id.textTitle)
        var textTime: TextView? = view.findViewById(R.id.textTime)
        var textCreateDate: TextView? = view.findViewById(R.id.text_create_date)
        var textUpdateDate: TextView? = view.findViewById(R.id.text_update_date)
        var delete:ImageView? = view.findViewById(R.id.delete)
    }

}