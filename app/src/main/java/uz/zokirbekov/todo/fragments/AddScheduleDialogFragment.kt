package uz.zokirbekov.todo.fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TextInputEditText
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import uz.zokirbekov.todo.R
import uz.zokirbekov.todo.models.Note
import uz.zokirbekov.todo.models.Schedule
import uz.zokirbekov.todo.util.AlarmWorker
import uz.zokirbekov.todo.util.DateSetListener
import uz.zokirbekov.todo.util.DialogDissmisListener
import uz.zokirbekov.todo.util.SqlWorker
import java.util.*

class AddScheduleDialogFragment : DialogFragment(), View.OnClickListener
{
    var db:SqlWorker? = null
    var schedule: Schedule? = null
    var isUpdate:Boolean = false
    var dialogDissmisListener:DialogDissmisListener? = null
    lateinit var alarmWorker: AlarmWorker

    var textDate: TextView? = null
    var textTime: TextView? = null
    var button: Button? = null
    var textTitle: TextInputEditText? = null
    var delete: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_add_schedule,container,false)

        textDate = view?.findViewById(R.id.textDate)
        textTime = view?.findViewById(R.id.textTime)
        button = view?.findViewById(R.id.addButton)
        textTitle = view?.findViewById(R.id.textTitle)

        button?.setOnClickListener(this)
        textDate?.setOnClickListener(this)
        textTime?.setOnClickListener(this)

        alarmWorker = AlarmWorker(context)

        dialog.window.setBackgroundDrawableResource(R.drawable.circle_border)
        showSchedule()
        return view
    }

    private fun showSchedule()
    {
        if (schedule != null && isUpdate)
        {
            textTitle?.setText(schedule?.title)
            textDate?.setText(SqlWorker.dateToString(schedule?.time!!))
            textTime?.setText(SqlWorker.timeToString(schedule?.time!!))
            button?.setText("Update")
        }
    }

    private fun newSchedule() : Schedule
    {
        var schedule = Schedule()
        schedule.id = SqlWorker.LastScheduleId + 1
        schedule.title = textTitle?.text.toString()
        schedule.time = SqlWorker.stringToTimestamp("${textDate?.text.toString()} ${textTime?.text.toString()}")
        return schedule
    }
    private fun updateSchedule() : Schedule
    {
        schedule?.title = textTitle?.text.toString()
        schedule?.time = SqlWorker.stringToTimestamp("${textDate?.text.toString()} ${textTime?.text.toString()}")
        schedule?.update_date = Date()
        return schedule!!
    }

    override fun onClick(v: View?) {
        when(v) {
            button -> {
                val schedule = if (isUpdate) updateSchedule() else newSchedule()
                if (schedule.time < Date()) {
                    Toast.makeText(context,"Time should be bigger than now",Toast.LENGTH_LONG).show()
                    return
                }
                if (isUpdate)
                {
                    db?.updateSchdule(schedule)
                    alarmWorker.update(schedule)
                }
                else
                {
                    db?.insertSchdule(schedule)
                    alarmWorker.setAlarm(schedule)
                }

                dialogDissmisListener?.OnDissmis()
                dismiss()
            }
            textDate ->
            {
                val datePicker = DatePickerDialogFragment()
                datePicker.date = schedule?.time
                datePicker.dateSetListener = object : DateSetListener
                {
                    override fun OnTimeSet(date: Date?) {
                        textDate?.setText(SqlWorker.dateToString(date!!))
                    }

                }
                datePicker.show(fragmentManager,"DATE_PICKER_DIALOG")
            }
            textTime ->
            {
                val timePicker = TimePickerDialogFragment()
                timePicker.date = schedule?.time
                timePicker.dateSetListener = object : DateSetListener
                {
                    override fun OnTimeSet(date: Date?) {
                        textTime?.setText(SqlWorker.timeToString(date!!))
                    }

                }
                timePicker.show(fragmentManager,"TIME_PICKER_DIALOG")
            }
        }

    }

    companion object {
        fun newInstanse(sch:Schedule?, db:SqlWorker?,listiner: DialogDissmisListener, isUpdate:Boolean = false) : AddScheduleDialogFragment
        {
            var fragment = AddScheduleDialogFragment()
            fragment.schedule = sch
            fragment.db = db
            fragment.dialogDissmisListener = listiner
            fragment.isUpdate = isUpdate
            return fragment
        }
    }

}