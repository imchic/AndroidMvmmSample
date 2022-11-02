package com.example.imchic.view.screens

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imchic.R
import com.example.imchic.base.BaseFragment
import com.example.imchic.databinding.FragmentDevmodeBinding
import com.example.imchic.model.DevModeListItem
import com.example.imchic.view.MainActivity

class DevModeFragment : BaseFragment<FragmentDevmodeBinding>(R.layout.fragment_devmode) {

    override fun initView() {

        binding.run {

            val devModeListArrHead = resources.getStringArray(R.array.devModeHead)
            val devModeListArrSub = resources.getStringArray(R.array.devModeSub)

            val layoutManager = LinearLayoutManager(context)
            listView.layoutManager = layoutManager

            val adapter = DevModeListAdapter(requireContext())
            adapter.addItem(DevModeListItem(devModeListArrHead[0], devModeListArrSub[0]))
            adapter.addItem(DevModeListItem(devModeListArrHead[1], devModeListArrSub[1]))
            adapter.addItem(DevModeListItem(devModeListArrHead[2], devModeListArrSub[2]))

            listView.adapter = adapter
        }

    }

}

// custom recylerView adapter
class DevModeListAdapter(private val context: Context) : RecyclerView.Adapter<DevModeListAdapter.ViewHolder>() {

    private val items = ArrayList<DevModeListItem>()

    private fun ViewGroup.inflate(layoutRes: Int): View = // 확장함수 사용
        LayoutInflater.from(context).inflate(layoutRes, this, false)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevModeListAdapter.ViewHolder {

        val itemView = parent.inflate(R.layout.listview_item)

        val layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        itemView.layoutParams = layoutParams

        //항목으로 사용할 view 객체를 생성한다.
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: DevModeListItem) {
        items.add(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewHead: TextView = itemView.findViewById(R.id.tv_list1)
        private val textViewSub: TextView = itemView.findViewById(R.id.tv_list2)

        // 해상도에 따라 텍스트뷰 크기 조절
//        init {
//            val display = (context as MainActivity).windowManager.defaultDisplay
//            val width = display.width
//            val height = display.height
//
//            textViewHead.layoutParams.width = (width * 0.1).toInt()
//            textViewSub.layoutParams.width = (width * 0.3).toInt()
//        }

        fun setItem(item: DevModeListItem) {
            textViewHead.text = item.title
            textViewSub.text = item.subTitle
        }
    }
}