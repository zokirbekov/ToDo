package uz.zokirbekov.todo

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.FrameLayout
import uz.zokirbekov.todo.fragments.NotesListFragment
import uz.zokirbekov.todo.fragments.ScheduleFragment
import uz.zokirbekov.todo.models.Note
import uz.zokirbekov.todo.models.Schedule
import uz.zokirbekov.todo.util.AlarmWorker
import uz.zokirbekov.todo.util.SqlWorker
import android.view.KeyEvent.KEYCODE_BACK
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.ViewConfiguration



class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    var bottomNavigation:BottomNavigationView? = null
    var toolbar: Toolbar? = null

    var noteFragment = NotesListFragment()
    var scheduleFragment = ScheduleFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation = findViewById(R.id.bottomNavigationView)
        toolbar = findViewById(R.id.toolBar)
        bottomNavigation?.setOnNavigationItemSelectedListener(this)
        switchFragment(R.id.action_notes)
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        switchFragment(item.itemId)
        return true
    }

    fun changeColor(rId:Int)
    {
        bottomNavigation?.setBackgroundColor(ContextCompat.getColor(this,rId))
        bottomNavigation?.itemBackgroundResource = rId
        toolbar?.setBackgroundColor(ContextCompat.getColor(this,rId))
        var color:Int = 0
        when (rId)
        {
            R.color.colorPrimary -> {
                color = ContextCompat.getColor(this,R.color.colorPrimaryDark)
            }
            R.color.colorGreen -> {
                color  = ContextCompat.getColor(this,R.color.colorGreenDark)
            }
        }
        val hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey()
        val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)

        if (!hasMenuKey && !hasBackKey) {
            window.navigationBarColor = color
        }
        window.statusBarColor = color

    }

    fun switchFragment(i:Int)
    {

        var fragmentTransaction = supportFragmentManager.beginTransaction()
        when(i)
        {
            R.id.action_notes -> {
                fragmentTransaction.replace(R.id.contentFragments, noteFragment)
                changeColor(R.color.colorPrimary)
            }
            R.id.action_schedules -> {
                fragmentTransaction.replace(R.id.contentFragments,scheduleFragment)
                changeColor(R.color.colorGreen)
            }
        }
        fragmentTransaction.commit()
    }
}
