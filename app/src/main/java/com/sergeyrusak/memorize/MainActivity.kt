package com.sergeyrusak.memorize
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import java.util.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val r = Random()
        val cards = arrayListOf(R.drawable.c1, R.drawable.c2, R.drawable.c3,
            R.drawable.c4, R.drawable.c5, R.drawable.c6, R.drawable.c7,
            R.drawable.c8,
            R.drawable.c1, R.drawable.c2, R.drawable.c3,
            R.drawable.c4, R.drawable.c5, R.drawable.c6, R.drawable.c7,
            R.drawable.c8)
        val positions = Array(4){Array(4){0} }
        val cardCover = Array(4){Array(4){true} }
        val layout = LinearLayout(applicationContext)
        layout.orientation = LinearLayout.VERTICAL
        val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        val params2 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0)
        params.weight = 1f
        params2.weight = 1f
        cards.shuffle()
        for (i in 0 until 4){
            val layoutLine = LinearLayout(applicationContext)
            layoutLine.orientation = LinearLayout.HORIZONTAL
            for (j in 0 until 4){
                val card = ImageView(applicationContext)
                val randCardPos = r.nextInt(cards.size)
                card.setImageResource(R.drawable.c0)
                positions[i][j] = cards[randCardPos]
                cards.removeAt(randCardPos)
                card.layoutParams = params
                card.setOnClickListener(View.OnClickListener {view ->
                    if (cardCover[i][j]){
                        card.setImageResource(positions[i][j])
                    }
                    else{
                        card.setImageResource(R.drawable.c0)
                    }
                    cardCover[i][j] = !cardCover[i][j]
                })
                layoutLine.addView(card)
            }
            layoutLine.layoutParams = params2
            layout.addView(layoutLine)
        }
        setContentView(layout)
    }
}
