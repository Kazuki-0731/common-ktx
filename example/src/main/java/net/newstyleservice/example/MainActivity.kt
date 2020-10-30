package net.newstyleservice.example

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import app.ss_n.common_ktx.extension.setOnSingleClickListener
import app.ss_n.common_ktx.observer.EventObserver
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.fab
import kotlinx.android.synthetic.main.activity_main.toolbar

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnSingleClickListener { view ->
            view?.let {
                val pref = Preferences(this)
                pref.tapCount++
                Snackbar.make(it, "Tapped count is ${pref.tapCount}", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

                mainViewModel.requestApi()
            }
        }

        // one shot event
        mainViewModel.getShikureList().observe(this, EventObserver { shikureList ->
            shikureList.forEach { shikure ->
                Log.d("response content", shikure.content)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
