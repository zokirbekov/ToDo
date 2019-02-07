package uz.zokirbekov.todo.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.zokirbekov.todo.R

class ScheduleFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fragment_schedule,container,false)
        return view
    }
}