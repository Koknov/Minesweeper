package koknov.nikolay.minesweeper

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class About : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)

        val btnBack = findViewById<ImageButton>(R.id.about_back)
        btnBack.setOnClickListener {
            this.finish()
        }
    }
}
