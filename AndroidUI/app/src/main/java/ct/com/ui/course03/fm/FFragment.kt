package ct.com.ui.course03.fm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ct.com.ui.R
import ct.com.ui.course03.RvAdapter
import ct.com.ui.course03.widget.FRecyclerView

class FFragment : Fragment() {

    private lateinit var rv: FRecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fm_f_list, container, false)
        with(rootView) {
            rv = findViewById(R.id.rv_f)
            rv.adapter = RvAdapter()
            rv.layoutManager = LinearLayoutManager(requireContext())
        }
        return rootView
    }
}