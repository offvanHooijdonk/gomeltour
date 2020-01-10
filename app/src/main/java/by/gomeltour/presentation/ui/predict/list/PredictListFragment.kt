package by.gomeltour.presentation.ui.predict.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tobe.gomeltour.R
import com.tobe.gomeltour.databinding.PredictListBinding
import by.gomeltour.helper.setUpDefault
import by.gomeltour.presentation.ui.main.EFABTransformer
import by.gomeltour.presentation.ui.main.MainActivity
import kotlinx.android.synthetic.main.fr_predict_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Yahor_Fralou on 9/21/2018 4:36 PM.
 */

class PredictListFragment : Fragment(), MainActivity.FABClickListener {

    private val viewModel: PredictListViewModel by viewModel()

    private lateinit var adapter: PredictAdapter
    private lateinit var ctx: Context
    private var prevScrollDirDown = false
    private val fabTransformer: EFABTransformer by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ctx = requireContext()
        val binding = DataBindingUtil.inflate<PredictListBinding>(inflater, R.layout.fr_predict_list, container, false)
        binding.model = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPredicts.layoutManager = LinearLayoutManager(ctx)
        adapter = PredictAdapter(ctx)
        rvPredicts.adapter = adapter
        refresh_predict_info.setUpDefault()
        refresh_predict_info.setOnRefreshListener { viewModel.updatePredicts() }

        rvPredicts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val dirDown = dy > 0
                if (prevScrollDirDown xor dirDown) {
                    viewModel.onListScroll(dirDown)
                }
                prevScrollDirDown = dirDown
            }
        })

        viewModel.viewStart()
    }

    override fun onStart() {
        super.onStart()

        fabTransformer.transformTo(iconRes = R.drawable.ic_wand_24, extended = true, text = context?.getString(R.string.add_predict_cta))
    }

    override fun onFabClicked() {
        viewModel.onActionButtonClick()
    }
}