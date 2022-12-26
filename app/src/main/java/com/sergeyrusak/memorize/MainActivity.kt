package com.sergeyrusak.memorize

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random

class MainActivity : AppCompatActivity() {
    var openCardsCount = 0
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
        var firstCard: View = View(this)
        var cardsAvl = 16
        val layout = LinearLayout(applicationContext)
        layout.orientation = LinearLayout.VERTICAL
        val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        val params2 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0)
        params.weight = 1f
        params2.weight = 1f

        for (i in 0 until 4){
            val layoutLine = LinearLayout(applicationContext)
            layoutLine.orientation = LinearLayout.HORIZONTAL
            for (j in 0 until 4){
                val card = ImageView(applicationContext)
                val randCardPos = r.nextInt(cards.size)
                card.setImageResource(R.drawable.c0)
                positions[i][j] = cards[randCardPos]
                card.layoutParams = params
                card.tag = cards[randCardPos].toString()
                cards.removeAt(randCardPos)
                card.setOnClickListener(View.OnClickListener {view ->
                    val iv: ImageView = view as ImageView
                    iv.setImageResource(positions[i][j])
                    if (openCardsCount == 0){
                        openCardsCount++
                        firstCard = view
                        view.isClickable = false
                    }
                    else if (openCardsCount == 1){
                        if (view.tag.equals(firstCard.tag)){
                            cardsAvl -= 2
                            GlobalScope.launch (Dispatchers.Main) {
                                setBackgroundWithDelay(view, firstCard,true) }
                            if (cardsAvl == 0){
                                Toast.makeText(applicationContext, "Вы победили!", Toast.LENGTH_LONG).show()
                            }
                        }
                        else{
                            iv.setImageResource(positions[i][j])
                            openCardsCount = 2
                            GlobalScope.launch (Dispatchers.Main) {
                                setBackgroundWithDelay(view, firstCard,false) }
                        }
                    }
                })
                layoutLine.addView(card)
            }
            layoutLine.layoutParams = params2
            layout.addView(layoutLine)
        }
        setContentView(layout)
    }

    suspend fun setBackgroundWithDelay(v1: View, v2: View,remove:Boolean) {
        delay(500)
        if (remove){
            v1.visibility = View.INVISIBLE
            v2.visibility = View.INVISIBLE
            v1.isClickable = false
            v2.isClickable = false
            openCardsCount = 0
        }
        else
        {
            delay(500)
            val iv1: ImageView = v1 as ImageView
            val iv2: ImageView = v2 as ImageView
            iv1.setImageResource(R.drawable.c0)
            iv2.setImageResource(R.drawable.c0)
            v1.isClickable = true
            v2.isClickable = true
            openCardsCount = 0
        }
    }
}
