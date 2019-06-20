package pl.biarritz.android

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragmentList = ArrayList<Fragment>()

    override fun getItem(position: Int) = fragmentList[position]

    override fun getCount() = fragmentList.size

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }
}