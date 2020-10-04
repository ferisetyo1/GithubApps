package feri.com.githubapps.shared

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(val ctx: Context, val fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var listFragment = ArrayList<Fragment>()
    var listTittle = ArrayList<String>()

    fun addFragment(fm: Fragment, s: String) {
        listFragment.add(fm)
        listTittle.add(s)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return listTittle.get(position)
    }

    override fun getItem(position: Int): Fragment {
        return listFragment.get(position)
    }

    override fun getCount(): Int {
        return listFragment.size
    }
}