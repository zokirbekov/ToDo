package uz.zokirbekov.todo

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.view.MenuItem
import android.widget.FrameLayout
import uz.zokirbekov.todo.fragments.NotesListFragment
import uz.zokirbekov.todo.fragments.ScheduleFragment
import uz.zokirbekov.todo.models.Note
import uz.zokirbekov.todo.util.SqlWorker

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    var bottomNavigation:BottomNavigationView? = null

    var noteFragment = NotesListFragment()
    var scheduleFragment = ScheduleFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation = findViewById(R.id.bottomNavigationView)
        bottomNavigation?.setOnNavigationItemSelectedListener(this)
        switchFragment(R.id.action_notes)
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        switchFragment(item.itemId)
        return true
    }

    fun switchFragment(i:Int)
    {
        var fragmentTransaction = supportFragmentManager.beginTransaction()
        when(i)
        {
            R.id.action_notes -> fragmentTransaction.replace(R.id.contentFragments,noteFragment)
            R.id.action_schedules -> fragmentTransaction.replace(R.id.contentFragments,scheduleFragment)
        }
        fragmentTransaction.commit()
    }

}
