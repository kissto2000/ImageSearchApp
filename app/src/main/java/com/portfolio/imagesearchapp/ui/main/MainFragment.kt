package com.portfolio.imagesearchapp.ui.main

import android.graphics.Insets
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsets
import androidx.annotation.LayoutRes
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.imagesearchapp.Const
import com.portfolio.imagesearchapp.GlobalApp
import com.portfolio.imagesearchapp.R
import com.portfolio.imagesearchapp.base.BaseFragment
import com.portfolio.imagesearchapp.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject


class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {

    @LayoutRes
    override fun getLayoutResourceId() = R.layout.fragment_main

    override val viewModel: MainViewModel by inject()

    override fun setBinding() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDisplayWidth()

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val lastPosition = (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                if (lastPosition > viewModel.searchImages.size - 10 && viewModel.isContinue && !viewModel.isEnd) {
                    viewModel.isContinue = false
                    viewModel.getSearchImage(viewModel.query, ++viewModel.page)
                }
            }
        })

        viewModel.document.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.action_navigation_main_to_navigation_photo, Bundle().apply {
                putSerializable(Const.KEY.DOCUMENT, it)
            })
        })

        viewModel.editClear.observe(viewLifecycleOwner, {
            binding.edtSearch.setText("")
        })
    }

    private fun getDisplayWidth() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity?.windowManager?.currentWindowMetrics
            val insets: Insets? = windowMetrics?.windowInsets?.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            insets?.let {
                GlobalApp.width = windowMetrics.bounds.width() - insets.left - insets.right
            }
        } else {
            val displayMetrics = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
            GlobalApp.width = displayMetrics.widthPixels
        }
    }
}