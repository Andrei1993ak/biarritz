package pl.biarritz.android

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import pl.biarritz.android.fragments.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
        setupBottomNavigation()
        setupViewPager()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_call -> {
                callToBarberShop(true)

                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        callToBarberShop(false)
    }


    private fun callToBarberShop(askingPermissionAllowed: Boolean) {
        val permissionToCall = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)

        if (permissionToCall == PackageManager.PERMISSION_GRANTED) {
            try {
                val callIntent = Intent(Intent.ACTION_CALL) //use ACTION_CALL class
                callIntent.data = Uri.parse(Constants.PHONE_URI)
                startActivity(callIntent)
            } catch (e: Exception) {
                Log.e(MainActivity::class.java.simpleName, "callToBarberShop", e)
            }
        } else if (askingPermissionAllowed) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 10)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupBottomNavigation() {
        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    pager.currentItem = 0
                    true
                }
                R.id.navigation_prices -> {
                    pager.currentItem = 1
                    true
                }
                R.id.navigation_contacts -> {
                    pager.currentItem = 2
                    true
                }
                R.id.navigation_profile -> {
                    pager.currentItem = 3
                    true
                }
                R.id.navigation_order -> {
                    pager.currentItem = 4
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun setupViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(HomeFragment())
        viewPagerAdapter.addFragment(PriceFragment())
        viewPagerAdapter.addFragment(ContactsFragment())
        viewPagerAdapter.addFragment(ProfileFragment())
        viewPagerAdapter.addFragment(OrderFragment())
        pager.adapter = viewPagerAdapter

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageSelected(position: Int) {
                navigation.menu.getItem(position).isChecked = true
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

        })
    }
}
