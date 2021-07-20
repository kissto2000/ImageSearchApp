package com.portfolio.imagesearchapp.ui.photo

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import com.portfolio.imagesearchapp.Const
import com.portfolio.imagesearchapp.R
import com.portfolio.imagesearchapp.base.BaseFragment
import com.portfolio.imagesearchapp.databinding.FragmentPhotoBinding
import com.portfolio.imagesearchapp.network.model.SearchImageObj
import org.koin.android.ext.android.inject


class PhotoFragment : BaseFragment<FragmentPhotoBinding, PhotoViewModel>() {

    @LayoutRes
    override fun getLayoutResourceId() = R.layout.fragment_photo

    override val viewModel: PhotoViewModel by inject()

    override fun setBinding() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            requireArguments().getSerializable(Const.KEY.DOCUMENT)?.let {
                viewModel.document.set(it as SearchImageObj.Document)
            }
        }

        viewModel.create()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                activity()?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}