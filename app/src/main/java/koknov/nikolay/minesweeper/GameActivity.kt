package koknov.nikolay.minesweeper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : AppCompatActivity() {

    companion object {
        const val MODE = "MODE"

    }

    private lateinit var mViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)
        mViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        val gameView = findViewById<GameView>(R.id.game_view)
        if (mViewModel.game == null) {
            val mode = GameMode.valueOf(intent.getStringExtra(MODE)!!)
            val game = Game(gameView.columns, gameView.rows, mode.bomb).apply { start() }
            gameView.restartGame(game, mode)
        }
        else {
            gameView.restartGame(mViewModel.game!!, mViewModel.mode!!)
        }



        setSupportActionBar(toolbar_game)
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.app_name)
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawer_layout_game,
            toolbar_game,
            R.string.drawer_open,
            R.string.drawer_close
        ) {}
        drawerToggle.isDrawerIndicatorEnabled = true
        drawer_layout_game.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        nav_view_game.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_about -> {
                    startActivity(
                        Intent(this, About::class.java)
                    )
                }
                R.id.nav_rest -> {
                    val mode = GameMode.valueOf(intent.getStringExtra(MODE)!!)
                    val game = Game(gameView.columns, gameView.rows, mode.bomb).apply { start() }
                    gameView.restartGame(game, mode)
                }
                R.id.nav_menu -> {
                    this.finish()
                }
            }
            drawer_layout_game.closeDrawer(GravityCompat.START)
            true
        }
    }


    override fun onStop() {
        super.onStop()
        val gameView = findViewById<GameView>(R.id.game_view)
        mViewModel.also {
            it.game = gameView.game
            it.mode = gameView.mode
        }
    }
}