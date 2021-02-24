package dev.sagar.assigmenthub.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.amplifyframework.datastore.generated.model.Assignment
import com.amplifyframework.util.GsonFactory
import dev.sagar.assigmenthub.ui.fragment.EndedFragment
import dev.sagar.assigmenthub.ui.fragment.OnGoingFragment

class HomePagerAdapter(
    fragmentActivity: FragmentActivity,
    private val onGoingAssignments: List<Assignment>,
    private val endedAssignments: List<Assignment>
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val data = GsonFactory.instance().toJson(onGoingAssignments)
                OnGoingFragment.newInstance(data)
            }
            else -> {
                val data = GsonFactory.instance().toJson(endedAssignments)
                EndedFragment.newInstance(data)
            }
        }
    }
}
