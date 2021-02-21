package dev.sagar.assigmenthub.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.sagar.assigmenthub.ui.fragment.EndedFragment
import dev.sagar.assigmenthub.ui.fragment.OnGoingFragment

class HomePagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                OnGoingFragment()
            }
            else -> {
                EndedFragment()
            }
        }
    }
}
