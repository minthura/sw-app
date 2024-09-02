package com.minthuya.sw

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.minthuya.sw.databinding.SwActivityBinding

class SwActivity : AppCompatActivity(), SWListener {

    private lateinit var binding: SwActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = SwActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun setTopNavBarVisibility(isVisible: Boolean) {
        binding.appBarLayout.isVisible = isVisible
    }

    companion object {
        fun start(context: Context) = with(context) {
            startActivity(Intent(this, SwActivity::class.java))
        }
    }
}