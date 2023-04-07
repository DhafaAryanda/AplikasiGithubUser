package com.example.myapplication.ui.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.myapplication.database.DetailUserResponse
import com.example.myapplication.R
import com.example.myapplication.database.FavoriteUser
import com.example.myapplication.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator
import android.view.Menu
import android.view.MenuItem
import com.example.myapplication.adapter.FollowPagerAdapter
import com.example.myapplication.ui.favorite.FavoriteUserActivity
import com.example.myapplication.ui.favorite.FavoriteUserViewModelFactory
import com.example.myapplication.ui.main.MainViewModel
import com.example.myapplication.ui.setting.SettingsActivity

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val username by lazy { intent.getStringExtra(USERNAME) }
    private val mainViewModel by viewModels<MainViewModel>()
    private val detailViewModel by viewModels<DetailUserViewModel> { FavoriteUserViewModelFactory.getInstance(application) }
    private lateinit var favoriteUser: FavoriteUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mainViewModel.getDetailUser(username ?: "")
        initPager()
        initObserver()
        setFavoriteClickListener()
    }

    private fun initPager() {
        val sectionsPagerAdapter = FollowPagerAdapter(this, username ?: "")
        val viewPager = binding.layoutFollow.vpFollowingFollowers
        val tabs = binding.layoutFollow.tlFollowingFollowers
        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()
    }

    private fun initObserver() {
        mainViewModel.isLoading.observe(this) { showLoading(it) }
        mainViewModel.detailUser.observe(this) { setDetailUser(it) }
        mainViewModel.toastText.observe(this) {
//            it.getContentIfNotHandled()?.let { toastText ->
//                showToast(toastText)
//            }
        }
    }

    private fun setDetailUser(user: DetailUserResponse?) {
        with(binding) {
            Glide.with(this.root.context)
                .load(user?.avatarUrl)
                .into(ivProfilePicture)
            tvUsername.text = user?.login
            tvFullName.text = user?.name ?: getString(R.string.no_name)
            tvLocation.text = user?.location ?: getString(R.string.no_location)
            tvFollowing.text = getString(R.string.following, user?.following ?: 0)
            tvFollowers.text = getString(R.string.followers, user?.followers ?: 0)
        }
        favoriteUser = FavoriteUser(user?.id.toString(), user?.login, user?.avatarUrl)
        detailViewModel.isFavorite(favoriteUser.userId).observe(this) {
            setFavoriteUser(it)
        }
    }

    private fun setFavoriteClickListener() {
        binding.fabFavoriteUser.setOnClickListener {
            if (binding.fabFavoriteUser.tag == "favorite") {
                detailViewModel.delete(favoriteUser)
                showToast(getString(R.string.remove_favorite_user))
            } else {
                detailViewModel.insert(favoriteUser)
                showToast(getString(R.string.add_favorite_user))
            }
        }
    }

    private fun setFavoriteUser(value: Boolean) {
        binding.fabFavoriteUser.apply {
            if (value) {
                tag = "favorite"
                setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailUserActivity,
                        R.drawable.ic_favorite_24
                    )
                )
            } else {
                tag = ""
                setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailUserActivity,
                        R.drawable.ic_favorite_border_24
                    )
                )
            }
        }
    }

    private fun showLoading(value: Boolean) {
        binding.apply {
            pbLoadingScreenDetail.isVisible = value
            fabFavoriteUser.isVisible = !value
            clProfileContent.isInvisible = value
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        menu.removeItem(R.id.search)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
            }
            R.id.favorite -> { FavoriteUserActivity.start(this@DetailUserActivity) }
            R.id.setting -> { SettingsActivity.start(this@DetailUserActivity) }
        }
        onBackPressedDispatcher.addCallback(this@DetailUserActivity) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun start(context: Context, username: String) {
            val starter = Intent(context, DetailUserActivity::class.java)
                .putExtra(USERNAME, username)
            context.startActivity(starter)
        }

        private val TAB_TITLES = arrayListOf(
            "Followers",
            "Following"
        )

        private const val USERNAME = "username"
    }

    private var mCurrentToast: Toast? = null
    private fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        if(mCurrentToast != null) {
            mCurrentToast?.cancel()
        }
        mCurrentToast = Toast.makeText(this, message, duration)
        mCurrentToast?.show()
    }
}