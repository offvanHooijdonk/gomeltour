package by.gomeltour.presentation.ui.predict.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.gomeltour.R
import by.gomeltour.helper.gone
import by.gomeltour.helper.setDragToRing
import by.gomeltour.presentation.ui.main.MainActivity
import kotlinx.android.synthetic.main.act_main.*
import kotlinx.android.synthetic.main.fr_play.*

class GameFragment : Fragment() {

    companion object {
        fun instance() = GameFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_play, container, false)
    }

    override fun onViewCreated(layoutView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(layoutView, savedInstanceState)

        // todo setup common usage for FAB
        (requireActivity() as? MainActivity)?.fabAddNew?.hide()

        fabPositive.setDragToRing(imgTarget, false,
                onStart = { setTargetPositive() },
                onCancel = { setTargetUnset() },
                onHit = { highlightTarget(it) },
                onSuccess = {
                    fabPositive.hide()
                    fabNegative.gone()
                })
        fabNegative.setDragToRing(imgTarget, true,
                onStart = { setTargetNegative() },
                onCancel = { setTargetUnset() },
                onHit = { highlightTarget(it) },
                onSuccess = {
                    fabNegative.hide()
                    fabPositive.gone()
                })

    }

    private fun highlightTarget(isHighlight: Boolean) {
        //imgTarget.alpha = if (isHighlight) 0.8f else 0.3f
        imgTarget.animate()
                .alpha(if (isHighlight) 0.6f else 0.3f)
                .setDuration(150)
                .start()
    }

    private fun setTargetPositive() = imgTarget.setImageResource(R.drawable.target_ring_pos)

    private fun setTargetNegative() = imgTarget.setImageResource(R.drawable.target_ring_neg)

    private fun setTargetUnset() = imgTarget.setImageResource(R.drawable.target_ring)

}