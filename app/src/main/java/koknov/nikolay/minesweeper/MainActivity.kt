package koknov.nikolay.minesweeper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        var viewWidth: Int? = null
        var viewHeight: Int? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        easyB.setOnClickListener { createBoardActivity(GameMode.EASY) }
        mediumB.setOnClickListener { createBoardActivity(GameMode.MEDIUM) }
        hardB.setOnClickListener { createBoardActivity(GameMode.HARD) }

        val layoutParams = findViewById<ConstraintLayout>(R.id.constraint)
        layoutParams.viewTreeObserver.addOnGlobalLayoutListener {
            viewHeight = layoutParams.height
            viewWidth = layoutParams.width
        }


        setSupportActionBar(toolbar_main)
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.app_name)
        // Initialize the action bar drawer toggle instance
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar_main,
            R.string.drawer_open,
            R.string.drawer_close
        ) {}
        //Drawer
        drawerToggle.isDrawerIndicatorEnabled = true
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        // Set nav_graph view nav_graph item selected listener
        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_about -> {
                    startActivity(
                        Intent(this, About::class.java)
                    )
                }
            }
            // Close the drawer
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun createBoardActivity(mode: GameMode) {
        val intent = Intent(this, GameActivity::class.java).apply {
            GameActivity.run {
                putExtra(MODE, mode.name)
            }
        }
        startActivity(intent)
    }

}