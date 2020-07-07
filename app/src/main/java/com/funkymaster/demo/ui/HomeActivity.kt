package com.funkymaster.demo.ui


import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.funkymaster.demo.R


class HomeActivity : AppCompatActivity() {

    private lateinit var tablayout: TabLayout
    private lateinit var viewPager: ViewPager




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        iniVariables()
    }

    fun iniVariables(){
        tablayout = findViewById(R.id.tablayout)
        viewPager = findViewById(R.id.viewPager)

        val adapter = MyViewPagerAdapter(
            supportFragmentManager
        )
        adapter.addFragment(FragmentProduct(), "Product")
        adapter.addFragment(FragmentBrand(), "Brand")
        viewPager.adapter = adapter
        tablayout.setupWithViewPager(viewPager)

    }

    class MyViewPagerAdapter(manager: FragmentManager) :FragmentPagerAdapter(manager) {

        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

    }


}
