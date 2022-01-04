package com.alvinaby.newsappv2

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvinaby.newsappv2.databinding.ActivityMainBinding
import com.alvinaby.newsappv2.di.AppModule
import com.alvinaby.newsappv2.di.DaggerAppComponent
import com.alvinaby.newsappv2.model.Responses
import com.alvinaby.newsappv2.presenter.NewsPresenter
import com.alvinaby.newsappv2.utils.NetworkUtils
import com.alvinaby.newsappv2.utils.ThemeUtils
import com.alvinaby.newsappv2.view.NewsAdapter
import com.alvinaby.newsappv2.view.ViewInterface
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ViewInterface {
    @Inject lateinit var newsPresenter: NewsPresenter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Theme
        val themeUtils = ThemeUtils(this)
        themeUtils.checkTheme()
        binding.themeBtn.setOnClickListener { themeUtils.changeTheme() }

        //Load news list
        loadNews()

        //Refresh news list
        binding.refreshNews.setOnRefreshListener {
            loadNews()
            binding.refreshNews.isRefreshing = false
        }

        //Detect network
        @Suppress("DEPRECATION")
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(NetworkUtils(this), intentFilter)

        //Navigation Bar
        binding.navbar.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.home -> binding.newsView.smoothScrollToPosition(0)
                R.id.search -> Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
                R.id.account -> Toast.makeText(this, "Account", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    private fun loadNews() {
        val appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this, this))
            .build()

        appComponent.inject(this)
        newsPresenter.loadNews()
    }

    override fun onDestroy() {
        super.onDestroy()
        newsPresenter.disposeNews()
    }

    override fun onSuccess(responses: Responses){
        val bindView = binding.newsView
        val articles = responses.articles

        bindView.setHasFixedSize(true)
        bindView.layoutManager = LinearLayoutManager(this)
        bindView.adapter = articles?.let { NewsAdapter(it) }
    }

    override fun onError() {
        val toast = Toast.makeText(this, "No news found", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    override fun onNetworkChanged(isConnected: Boolean) {
        if (!isConnected) {
            val toast = Toast.makeText(this, "No network connection", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    override fun openNews(url: String) {
        //Chrome Custom Tabs
        val params = CustomTabColorSchemeParams.Builder()
        params.setToolbarColor(ContextCompat.getColor(this@MainActivity, R.color.header))

        val builder = CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(params.build())
            .setShowTitle(true)
            .setInstantAppsEnabled(true)
            .setShareState(CustomTabsIntent.SHARE_STATE_ON)

        val customTabs = builder.build()

        //Use Custom Tabs if Chrome is installed, or use Webview if Chrome is not installed
        if (this.isPackageInstalled(package_name)) {
            customTabs.intent.setPackage(package_name)
            customTabs.launchUrl(this, Uri.parse(url))
        } else {
            startActivity(Intent(this, WebViewActivity::class.java).putExtra("URL", url))
        }

    }

    private fun Context.isPackageInstalled(packageName: String): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    companion object {
        var package_name = "com.android.chrome"
    }
}
