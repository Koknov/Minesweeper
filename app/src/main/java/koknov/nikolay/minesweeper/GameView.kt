package koknov.nikolay.minesweeper

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import koknov.nikolay.minesweeper.MainActivity.Companion.viewHeight
import koknov.nikolay.minesweeper.MainActivity.Companion.viewWidth
import java.util.*


class GameView : View {

    companion object {
        const val IMAGE_SIZE = 50
    }

    lateinit var game: Game
    lateinit var mode: GameMode
    private var paint = Paint()
    private val activity = getActivity()

    private val widthCol = viewWidth!!.toFloat() / IMAGE_SIZE
    private val heightRow = viewHeight!!.toFloat() / IMAGE_SIZE
    val columns = ifRotated(widthCol-1, heightRow-1).toInt()
    val rows = ifRotated(heightRow-1, widthCol-1).toInt()
    private var clickTime = 0L


    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    init {
        setImages()
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
            for (coord in Field.getAllCoords()) {
                game.getCell(coord).image?.apply {
                    canvas!!.drawBitmap(
                        this as Bitmap,
                        columns + (ifRotated(coord.x, coord.y).toFloat()) * IMAGE_SIZE,
                        rows + (ifRotated(coord.y, coord.x).toFloat()) * IMAGE_SIZE,
                        paint
                    )
                }
            }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false

        val x = (ifRotated(event.x, event.y).toFloat() - widthCol) / IMAGE_SIZE.toFloat()
        val y = (ifRotated(event.y, event.x).toFloat() - heightRow) / IMAGE_SIZE.toFloat()

        val coord = Coord(x.toInt(), y.toInt())
        if (!Field.inRange(coord)) return false

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                clickTime = System.currentTimeMillis()
                return true
            }
            MotionEvent.ACTION_UP -> {
                if (System.currentTimeMillis() - clickTime > 200)
                    game.open(coord)
                else
                    game.flagged(coord)
                if (game.gameState != GameState.PLAYED) {
                    when (game.gameState) {
                        GameState.BOMBED -> dialogWindow(context.getString(R.string.lose))
                        GameState.WINNER -> dialogWindow(context.getString(R.string.win))
                        else -> return false
                    }
                }
            }
        }
        return performClick()
    }

    override fun performClick(): Boolean {
        invalidate()
        return super.performClick()
    }

    fun restartGame(game: Game, mode: GameMode) {
        this.game = game
        this.mode = mode
    }

    private fun dialogWindow(text: String) {
        AlertDialog.Builder(context).apply {
            setMessage(text)
            setPositiveButton(R.string.rstrt) { dialog, id ->
                restartGame(Game(columns, rows, mode.bomb).apply { start() }, mode)
                invalidate()
            }
            setNegativeButton(R.string.to_menu) { dialog, id ->
                activity?.finish()
            }
            create()
        }.show()
    }

    private fun setImages() {
        for (box in Cell.values())
            box.image = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources,
                    resources.getIdentifier(
                        box.name.toLowerCase(Locale.ENGLISH),
                        "drawable",
                        context.packageName
                    )
                ),
                IMAGE_SIZE, IMAGE_SIZE, true
            )
    }

    private fun getActivity(): Activity? {
        var context = context
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }

    private fun ifRotated(a: Number, b: Number): Number =
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) a else b
}