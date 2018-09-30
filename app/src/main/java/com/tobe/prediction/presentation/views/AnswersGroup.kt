package com.tobe.prediction.presentation.views

import android.content.Context
import android.view.View

/**
 * Created by Yahor_Fralou on 9/28/2018 3:00 PM.
 */

/**
 * Holds a group of Answer form units, manages selecting, adding, removing and listening to controls interaction
 *
 * @constructor Creates a new instance ready-to-use.
 * @param maxAnswerNum maximum allowed answers
 * @param minAnswerNum minimum allowed answers
 * @param removeListener called when a remove is requested from UI so app can react to it. NO REMOVE actually performs
 */
class AnswersGroup(private val maxAnswerNum: Int, private val minAnswerNum: Int, private val removeListener: (View, Int) -> Unit) {
    companion object {
        const val NONE_SELECTED = -1
    }

    private val views = mutableListOf<AnswerFormView>()
    private var closeBtnCurrentlyEnabled = false

    var size: Int = 0
        get() = views.size
        private set
    var selectedPosition: Int = -1
        get() {
            var position = NONE_SELECTED
            views.forEachIndexed { i, view ->
                if (view.isAnswerSelected()) {
                    position = i
                    return@forEachIndexed
                }
            }
            return position
        }
        private set

    /**
     * Adds a new Answer option to the group
     * @throws AnswersLimitViolationException if a maximum limit for answers is already reached
     */
    @Throws(AnswersLimitViolationException::class)
    fun addNewAnswer(ctx: Context): View {
        validateAddNew()

        val view = AnswerFormView(ctx, views.size)
        setUp(view)
        views.add(view)

        setUpControlsAvailability()

        return view
    }

    /**
     * Removes a view at specified [position]
     * @param position at which answer should be deleted
     * @throws AnswersLimitViolationException if minimum answers number already already reached
     */
    @Throws(AnswersLimitViolationException::class)
    fun removeAnswer(position: Int) { // todo validate the position exists
        validateRemove(position)

        removeAt(position)
    }

    /**
     * Performs any setup needed for the view before it can be returned to creation requester
     */
    private fun setUp(view: AnswerFormView) {
        attachListeners(view)
    }

    /**
     * Attaches listeners to the view events
     */
    private fun attachListeners(view: AnswerFormView) {
        view.onAnswerSelected { i -> onAnswerSelected(i) }
        view.onRemoveClick { i -> onRemoveClick(i) }
    }

    /**
     * Performs the vew removal at [position]
     * @param position at which to remove
     */
    private fun removeAt(position: Int) {
        views.removeAt(position)
        normalizePositions()
        setUpControlsAvailability()
    }

    /**
     * Reacts on radio check event. Unchecks all radios and checks th selected one
     */
    private fun onAnswerSelected(position: Int) {
        views.forEach { v: AnswerFormView -> v.checkAnswer(false) }
        views[position].checkAnswer(true)
    }

    /**
     * Reacts on 'remove' button click. Calls external listener to inform about the remove request
     */
    private fun onRemoveClick(position: Int) {
        removeListener(views[position], position)
    }

    @Throws(AnswersLimitViolationException::class)
    private fun validateAddNew() {
        if (views.size >= maxAnswerNum) throw AnswersLimitViolationException(maxAnswerNum, minAnswerNum)
    }

    @Throws(AnswersLimitViolationException::class)
    private fun validateRemove(position: Int) {
        if (views.size <= minAnswerNum) throw AnswersLimitViolationException(maxAnswerNum, minAnswerNum)
    }

    /**
     * Reset the positions of views according to actual position in the views list
     */
    private fun normalizePositions() {
        views.forEachIndexed { index, view -> view.updatePosition(index) }
    }

    /**
     * Check if any controls are to be hidden/shown/enabled/disabled and perform corresponding action
     */
    private fun setUpControlsAvailability() {
        val closeBtnNeedEnable = views.size > minAnswerNum
        if (!(closeBtnNeedEnable && closeBtnCurrentlyEnabled)) views.forEach { view -> view.setCloseEnabled(closeBtnNeedEnable) }

        closeBtnCurrentlyEnabled = closeBtnNeedEnable
    }

    fun getAnswers(): List<String> {
        val list = mutableListOf<String>()
        views.forEach { v -> if (!v.getAnswerText().isBlank()) list.add(v.getAnswerText()) }
        return list
    }

    class AnswersLimitViolationException(maxNum: Int, minNum: Int) : Exception("Limit for answers exceeded, limit is from $minNum to $maxNum")

}