package com.example.myapplication.ui.favorite

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.ListFavoriteUserAdapter
import com.example.myapplication.databinding.ActivityFavoriteUserBinding
import com.example.myapplication.ui.setting.SettingsActivity
import com.example.myapplication.ui.detail.DetailUserActivity

class FavoriteUserActivity : AppCompatActivity(), ListFavoriteUserAdapter.OnUserFavoriteClick {

    private lateinit var binding: ActivityFavoriteUserBinding
    private val detailViewModel by viewModels<FavoriteUserViewModel> {
        FavoriteUserViewModelFactory.getInstance(application)
    }
    private lateinit var adapter: ListFavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel.getAllFavoriteUser().observe(this) { favUserList ->
            if (favUserList != null) {
                adapter.setListFavorite(favUserList)
                binding.tvNoDataUser.isVisible = favUserList.isEmpty()
            } else {
                binding.tvNoDataUser.isVisible = true
            }
        }

        adapter = ListFavoriteUserAdapter(this)

        binding.rvListUser.apply {
            val manager = LinearLayoutManager(this@FavoriteUserActivity)
            val decoration = DividerItemDecoration(this@FavoriteUserActivity, manager.orientation)
            adapter = this@FavoriteUserActivity.adapter
            layoutManager = manager
            addItemDecoration(decoration)
        }
        supportActionBar?.title = getString(R.string.favorite_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        menu.removeItem(R.id.search)
        menu.removeItem(R.id.favorite)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        } else if (item.itemId == R.id.setting) {
            SettingsActivity.start(this)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onUserFavoriteClick(username: String) {
        DetailUserActivity.start(this, username)
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, FavoriteUserActivity::class.java)
            context.startActivity(starter)
        }
    }

}
