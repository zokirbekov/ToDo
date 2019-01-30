package uz.zokirbekov.todo

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.widget.FrameLayout
import uz.zokirbekov.todo.fragments.NotesListFragment
import uz.zokirbekov.todo.models.Note
import uz.zokirbekov.todo.util.SqlWorker

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.contentFragments,NotesListFragment())
        fragmentTransaction.commit()
    }
}
