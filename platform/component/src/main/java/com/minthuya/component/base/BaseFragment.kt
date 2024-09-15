package com.minthuya.component.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

interface BaseFragmentListener {
    fun onViewCreated()
}
abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment(), BaseFragmentListener {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        setupDi()
        setupView()
        setupEvents()
        setupObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated()
    }

    protected fun showSnackBarWithAction(text: String, retry: (() -> Unit)? = null) =
        Snackbar.make(binding.root, text, Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") { retry?.invoke() }
            .show()

    protected fun showSnackBar(text: String) =
        Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG)
            .show()

    open fun setupDi() {}
    open fun setupView() {}
    open fun setupEvents() {}
    open fun setupObservers() {}
}