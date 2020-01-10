package by.gomeltour.presentation.views

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import by.gomeltour.R
import kotlinx.android.synthetic.main.item_answer_form.view.*

/**
 * Created by Yahor_Fralou on 9/28/2018 2:13 PM.
 */

/**
 * A view that shows Answer option input and controls: select, remove
 * @constructor
 * @param ctx Context
 * @param position order position in the group of answers; used to know which view has risen an event
 * @param attrs Attributes that are set through xml layout construction
 */
class AnswerFormView(ctx: Context?, private var position: Int = 0, attrs: AttributeSet? = null) : FrameLayout(ctx, attrs) {

    constructor(ctx: Context?, attrs: AttributeSet?) : this(ctx, 0, attrs)

    private var hintText: String? = null

    init {

        val ta: TypedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.AnswerFormView, 0, 0)
        try {
            hintText = ta.getString(R.styleable.AnswerFormView_inputHint)
        } catch (e: Exception) {
        } finally {
            ta.recycle()
        }

        View.inflate(ctx, R.layout.item_answer_form, this)

        updateInputHint()
    }

    fun getAnswerText() = inputAnswer.text.toString()

    fun onRemoveClick(listener: (Int) -> Unit) {
        imgRemoveAnswer.setOnClickListener { listener(position) }
    }

    fun onAnswerSelected(listener: (Int) -> Unit) {
        radioAnswer.setOnCheckedChangeListener { _, isChecked -> if (isChecked) listener(position) }
    }

    fun checkAnswer(isCheck: Boolean) {
        radioAnswer.isChecked = isCheck
    }

    fun setCloseEnabled(isEnabled: Boolean) {
        imgRemoveAnswer.isEnabled = isEnabled
    }

    fun isAnswerSelected() = radioAnswer.isChecked

    fun updatePosition(position: Int) {
        this.position = position
        updateInputHint()
    }

    private fun updateInputHint() {
        inputAnswer.hint = context.getString(R.string.form_hint_answer, (position + 1).toString())
    }
}