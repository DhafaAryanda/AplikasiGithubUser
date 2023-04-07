package com.example.myapplication.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.ListUserAdapter
import com.example.myapplication.database.UserItem
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.setting.SettingsActivity
import com.example.myapplication.ui.detail.DetailUserActivity
import com.example.myapplication.ui.favorite.FavoriteUserActivity

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity(), ListUserAdapter.OnUserItemClick {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.postSearchuser("dicoding")
        initObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search_user_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.postSearchuser(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> SettingsActivity.start(this@MainActivity)
            R.id.favorite -> FavoriteUserActivity.start(this@MainActivity)
        }
        return super.onOptionsItemSelected(item)
    }

    private var mCurrentToast: Toast? = null

    private fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        if(mCurrentToast != null) {
            mCurrentToast?.cancel()
        }
        mCurrentToast = Toast.makeText(this, message, duration)
        mCurrentToast?.show()
    }

    private fun initObserver() {
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        mainViewModel.listSearch.observe(this) {
            setListUsers(it)
        }
        mainViewModel.toastText.observe(this) {
            if (!it.isNullOrBlank()) {
                showToast(it)
            }
        }
    }

    private fun setListUsers(listUsers: List<UserItem>?) {
        with(binding) {
            val manager = LinearLayoutManager(this@MainActivity)
            val itemDecoration = DividerItemDecoration(this@MainActivity, manager.orientation)
            rvListUser.apply {
                adapter = ListUserAdapter(listUsers ?: emptyList(), this@MainActivity)
                layoutManager = manager
                addItemDecoration(itemDecoration)
            }
            tvNoDataUser.isVisible = listUsers.isNullOrEmpty()
        }
    }

    private fun showLoading(value: Boolean) {
        with(binding) {
            progressBar.isVisible = value
            rvListUser.isVisible = !value
            tvNoDataUser.isVisible = !value
        }

    }

    override fun onUserItemClick(username: String) {
        DetailUserActivity.start(this@MainActivity, username)
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }

}