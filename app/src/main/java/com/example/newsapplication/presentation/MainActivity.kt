package com.example.newsapplication.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ActivityMainBinding
import com.example.newsapplication.domain.models.starting.StartingInfo
import com.example.newsapplication.presentation.adapters.NewsInitializationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private var startingInfoList: MutableList<StartingInfo> = mutableListOf()
    private var currentItemPosition = 0

    private lateinit var handler: Handler

    private val newsInitializationAdapter by lazy {
        NewsInitializationAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)

        initializeViews()
    }

    private fun initializeViews() {
        initializeViewPager()
    }

    private fun initializeViewPager() {
        val infoIds = listOf(
            R.string.news_image_intro,
            R.string.news_navigate,
            R.string.news_navigate_world,
            R.string.news_revolution
        )

        val imageIds = listOf(
            R.drawable.news_image_intro,
            R.drawable.news_navigate,
            R.drawable.news_navigate_world,
            R.drawable.news_revolution
        )

        startingInfoList.addAll(infoIds.indices.map { index ->
            StartingInfo(
                index + 1, resources.getString(infoIds[index]), imageIds[index]
            )
        })

        newsInitializationAdapter.differ.submitList(startingInfoList)
        mBinding?.vpInitializer?.apply {
            adapter = newsInitializationAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            mBinding?.circleIndicator?.setViewPager(mBinding?.vpInitializer)
        }

        handler = Handler(Looper.getMainLooper())
        handler.postDelayed(autoSwipeRunnable, 2000)
    }

    private val autoSwipeRunnable = object : Runnable {
        override fun run() {
            // Check if reached the end of the list, reset to the beginning
            if (currentItemPosition == newsInitializationAdapter.itemCount - 1) {
                startNewsActivity()
            } else {
                currentItemPosition++
            }
            // Smooth scroll to the next item
            mBinding?.vpInitializer?.setCurrentItem(currentItemPosition, true)
            // Schedule the next runnable
            handler.postDelayed(this, 2000) // Delay 2 seconds before next swipe
        }
    }

    private fun startNewsActivity() {
        val intent = Intent(this@MainActivity, NewsActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        handler.removeCallbacks(autoSwipeRunnable)
        mBinding = null
        super.onDestroy()
    }
}