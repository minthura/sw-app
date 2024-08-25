package com.minthuya.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.minthuya.component.parent
import com.minthuya.networkkit.UiResult
import com.minthuya.sample.databinding.SampleActivityBinding
import com.minthuya.sample.di.DaggerSampleComponent
import com.minthuya.sample.ui.SampleViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SampleActivity : AppCompatActivity() {

    private lateinit var binding: SampleActivityBinding

    @Inject
    lateinit var viewModel: SampleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerSampleComponent.factory().create(this, parent()).inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = SampleActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    val tv = findViewById<TextView>(R.id.txt_output)
                    when (it) {
                        is UiResult.Error -> tv.text = "code: ${it.error?.code}, message: ${it.error?.type}"
                        is UiResult.Loading -> tv.text = "Loading.."
                        is UiResult.Success -> tv.text = it.body?.weather?.firstOrNull()?.main
                    }
                }
            }
        }
        viewModel.fetchWeather()
    }

    companion object {
        fun start(context: Context) = with(context) {
            startActivity(Intent(this, SampleActivity::class.java))
        }
    }
}