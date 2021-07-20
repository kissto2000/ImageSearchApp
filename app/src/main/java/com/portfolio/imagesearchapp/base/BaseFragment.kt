package com.portfolio.imagesearchapp.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.portfolio.imagesearchapp.Const
import com.portfolio.imagesearchapp.ui.MainActivity
import com.portfolio.imagesearchapp.R

abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel>: Fragment(), BaseFragmentInit {

    @LayoutRes
    abstract fun getLayoutResourceId(): Int

    lateinit var binding: T
        private set

    abstract val viewModel: VM

    /**
     * Functions
     */
    fun activity(): MainActivity? {
        return if (activity != null) activity as MainActivity
               else null
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

    /**
     * Life Cycle
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifeCycle("onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifeCycle("onCreate")

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        lifeCycle("onCreateView")

        activity()?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment)?.let {
            it.childFragmentManager.fragments.forEach { fragment ->
                when (fragment.javaClass.simpleName) {
                    Const.MAIN_FRAGMENT -> {
                        activity()?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    }
                    else -> {
                        activity()?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    }
                }
            }
        }
        return DataBindingUtil.inflate<T>(inflater, getLayoutResourceId(), container, false).apply { binding = this }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifeCycle("onViewCreated")
        setBinding()
    }

    override fun onStart() {
        super.onStart()
        lifeCycle("onStart")
    }

    override fun onResume() {
        super.onResume()
        lifeCycle("onResume")
    }

    override fun onPause() {
        lifeCycle("onPause")
        super.onPause()
    }

    override fun onStop() {
        lifeCycle("onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        lifeCycle("onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        lifeCycle("onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        lifeCycle("onDetach")
        super.onDetach()
    }

    /**
     * Logger
     */
    private fun lifeCycle(life: String) {
        Log.i("LIFECYCLE", javaClass.simpleName + ": " +life)
    }
}