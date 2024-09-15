package com.minthuya.sw

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import com.minthuya.component.parent
import com.minthuya.sw.databinding.SwActivityBinding
import com.minthuya.sw.di.DaggerSWComponent
import com.minthuya.sw.navigator.SWInternalNavigator
import javax.inject.Inject

class SwActivity : AppCompatActivity() {

    private lateinit var binding: SwActivityBinding

    @Inject
    lateinit var internalNavigator: SWInternalNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = SwActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = (
            supportFragmentManager.findFragmentById(
                R.id.fragment_container
            ) as NavHostFragment
            ).navController
        DaggerSWComponent.factory().create(
            this,
            this,
            navController,
            parent()
        ).inject(this)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.menuSettings.setOnClickListener {
            internalNavigator.navigateToSettings()
        }
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    fun setTopNavBarVisibility(isVisible: Boolean) {
        binding.appBarLayout.isVisible = isVisible
    }

    fun setTopNavBarTitle(title: String) {
        binding.topAppBar.title = title
    }

    fun setSettingsVisible(isVisible: Boolean) {
        binding.menuSettings.isVisible = isVisible
    }

    fun setNavigationIconVisibility(isVisible: Boolean) {
        binding.topAppBar.navigationIcon = if (isVisible) {
            ContextCompat.getDrawable(this, R.drawable.sw_arrow_back_24)
        } else {
            null
        }
    }

    companion object {
        fun start(context: Context) = with(context) {
            startActivity(Intent(this, SwActivity::class.java))
        }
    }
}