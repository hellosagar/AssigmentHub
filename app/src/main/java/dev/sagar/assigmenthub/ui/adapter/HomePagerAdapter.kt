package dev.sagar.assigmenthub.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.amplifyframework.datastore.generated.model.Assignment
import com.amplifyframework.util.GsonFactory
import dev.sagar.assigmenthub.ui.fragment.EndedFragment
import dev.sagar.assigmenthub.ui.fragment.OnGoingFragment
import dev.sagar.assigmenthub.utils.OnDataUpdate

class HomePagerAdapter(
    fragmentActivity: FragmentActivity,
    private val onGoingAssignments: List<Assignment>,
    private val endedAssignments: List<Assignment>
) :
    FragmentStateAdapter(fragmentActivity) {

    private lateinit var ongoingFragment: OnGoingFragment
    private lateinit var endedFragment: EndedFragment

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                val data = GsonFactory.instance().toJson(onGoingAssignments)
                ongoingFragment = OnGoingFragment.newInstance(data)
                return ongoingFragment
            }
            else -> {
                val data = GsonFactory.instance().toJson(endedAssignments)
                endedFragment = EndedFragment.newInstance(data)
                return endedFragment
            }
        }
    }

    fun updateData(onGoingAssignments: List<Assignment>, endedAssignments: List<Assignment>) {
        (ongoingFragment as OnDataUpdate).onDataUpdate(onGoingAssignments)
        (endedFragment as OnDataUpdate).onDataUpdate(endedAssignments)
    }
}
