package dev.sagar.assigmenthub.utils

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import dev.hellosagar.assigmenthub.R
import dev.hellosagar.assigmenthub.databinding.ProgressButtonLayoutBinding

class ProgressButton(
    context: Context,
    view: ProgressButtonLayoutBinding,
    private val btnText: String
) {

    private var cardView: CardView = view.cvProgressButton
    private var layout: ConstraintLayout = view.clProgressButton
    private var innerClProgressButton: ConstraintLayout = view.innerClProgressButton
    private var progressBar: ProgressBar = view.progressBarProgressButton
    private var textView: TextView = view.tvProgressButton
    private var fadeIn: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)

    init {
        textView.text = btnText
    }

    fun btnActivated(text: String) {
        progressBar.animation = fadeIn
        progressBar.visible()
        textView.animation = fadeIn
        textView.text = text
    }

    fun btnFinished(text: String) {
        innerClProgressButton.setBackgroundColor(
            ContextCompat.getColor(
                cardView.context,
                R.color.green
            )
        )
        textView.text = text
        progressBar.invisible()
    }

    fun btnReset() {
        textView.text = btnText
        progressBar.invisible()
    }
}
