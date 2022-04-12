package com.alvinaby.newsappv2.activity

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alvinaby.newsappv2.R
import com.alvinaby.newsappv2.databinding.ActivityMainBinding
import com.alvinaby.newsappv2.fragment.AccountFragment
import com.alvinaby.newsappv2.fragment.HomeFragment
import com.alvinaby.newsappv2.fragment.SearchFragment
import com.alvinaby.newsappv2.utils.NetworkUtils
import com.alvinaby.newsappv2.utils.ThemeUtils
import com.alvinaby.newsappv2.view.ActivityViewInterface

class MainActivity : AppCompatActivity(), ActivityViewInterface {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Launch HomeFragment first
        if (savedInstanceState == null)
            openFragment(HomeFragment.newInstance())

        //Navigate fragments
        navFragment()

        //Theme
        val themeUtils = ThemeUtils(this)
        themeUtils.checkTheme()
        binding.themeBtn.setOnClickListener { themeUtils.changeTheme() }

        //Detect network
        @Suppress("DEPRECATION")
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(NetworkUtils(this), intentFilter)
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.contentFragment, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    private fun navFragment() {
        binding.navbar.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.home -> {
                    openFragment(HomeFragment.newInstance())
                    return@setOnItemSelectedListener true
                }
                R.id.search -> {
                    openFragment(SearchFragment.newInstance())
                    return@setOnItemSelectedListener true
                }
                R.id.account -> {
                    openFragment(AccountFragment.newInstance())
                    return@setOnItemSelectedListener true
                }
            }
            false
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
        if (this.isPackageInstalled(package_chrome)) {
            customTabs.intent.setPackage(package_chrome)
            customTabs.launchUrl(this, Uri.parse(url))
        } else {
            startActivity(Intent(this, WebViewActivity::class.java).putExtra("URL", url))
        }

    }

    //Check if package (app destination) is installed or not
    private fun Context.isPackageInstalled(packageName: String): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    override fun onNetworkChanged(isConnected: Boolean) {
        if (!isConnected) {
            Toast.makeText(this, "No network connection", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        var package_chrome = "com.android.chrome"
    }
}
