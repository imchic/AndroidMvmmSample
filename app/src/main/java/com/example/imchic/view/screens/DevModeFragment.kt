package com.example.imchic.view.screens

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imchic.R
import com.example.imchic.base.BaseFragment
import com.example.imchic.databinding.FragmentDevmodeBinding
import com.example.imchic.model.DevModeListItem
import com.example.imchic.util.AppUtil
import com.example.imchic.view.MainActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder


//class DevModeFragment
//    : BaseFragment<FragmentDevmodeBinding>(R.layout.fragment_devmode), DevModeListAdapter.OnDevModeListener {
//    override fun initView() {
//
//        binding.run {
//
//            val devModeListArrHead = resources.getStringArray(R.array.devModeHead)
//            val devModeListArrSub = resources.getStringArray(R.array.devModeSub)
//
//            val layoutManager = LinearLayoutManager(context)
//            viewDevmodeList.layoutManager = layoutManager
//
//            val adapter = DevModeListAdapter(requireContext())
//            adapter.addItem(DevModeListItem(devModeListArrHead[0], devModeListArrSub[0]))
//            adapter.addItem(DevModeListItem(devModeListArrHead[1], devModeListArrSub[1]))
//            adapter.addItem(DevModeListItem(devModeListArrHead[2], devModeListArrSub[2]))
//
//            adapter.setOnDevModeListener(this@DevModeFragment)
//
//            viewDevmodeList.adapter = adapter
//
//        }
//
//    }
//
//    override fun setDevServerUrl(url: String) {
//        AppUtil.logD("setDevServerUrl => $url")
//        AppUtil.setDevServerUrl(requireContext(), url)
//        globalToast(AppUtil.ToastType.INFO, "개발 서버 주소가 변경되었습니다.", Toast.LENGTH_SHORT)
//    }
//}
//
//internal class DevModeListAdapter(private val context: Context) : RecyclerView.Adapter<DevModeListAdapter.ViewHolder>() {
//
//    // listener 생성
//    private var adapterListener: OnDevModeListener? = null
//    interface OnDevModeListener {
//        fun setDevServerUrl(url: String)
//    }
//
//    fun setOnDevModeListener(listener: OnDevModeListener) {
//        this.adapterListener = listener
//    }
//
//
//    private val items = ArrayList<DevModeListItem>()
//
//    private fun ViewGroup.inflate(layoutRes: Int): View = // 확장함수 사용
//        LayoutInflater.from(context).inflate(layoutRes, this, false)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevModeListAdapter.ViewHolder {
//
//        val itemView = parent.inflate(R.layout.list_devmode_item)
//
//        val layoutParams = RecyclerView.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//
//        itemView.layoutParams = layoutParams
//
//        //항목으로 사용할 view 객체를 생성한다.
//        return ViewHolder(itemView)
//
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = items[position]
//        holder.setItem(item)
//    }
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    fun addItem(item: DevModeListItem) {
//        items.add(item)
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        private val textViewHead: TextView = itemView.findViewById(R.id.tv_list1)
//        private val textViewSub: TextView = itemView.findViewById(R.id.tv_list2)
//
//        init {
//            itemView.setOnClickListener {
//                val position = adapterPosition
//                val item = items[position]
//                when (position) {
//                    0 -> setDevServerUrl()
//                    1 -> showLoginUserInfo()
//                }
//            }
//        }
//
//        fun setItem(item: DevModeListItem) {
//            textViewHead.text = item.title
//            textViewSub.text = item.subTitle
//        }
//    }
//
//    private fun showLoginUserInfo() {
//        AppUtil.logD("showLoginUserInfo")
//        // LoginInfoFragment로 이동
//        (context as MainActivity).navController.navigate(R.id.LoginInfoFragment)
//
//    }
//
//    private fun setDevServerUrl() {
//        val builder = MaterialAlertDialogBuilder(context)
//        builder.setTitle("서버주소 입력")
//
//        val serverInfoEditTextView = EditText(context)
//        serverInfoEditTextView.hint = "http://"
//
//        val container = FrameLayout(context)
//        val params = FrameLayout.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        params.leftMargin = 50
//        params.rightMargin = 50
//        serverInfoEditTextView.layoutParams = params
//        container.addView(serverInfoEditTextView)
//
//        builder.setView(container)
//        builder.setPositiveButton("확인") { dialog, which ->
//            val serverUrl = serverInfoEditTextView.text.toString()
//            if (serverUrl.isNotEmpty()) {
//                adapterListener?.setDevServerUrl(serverUrl)
//            } else {
//                Toast.makeText(context, "서버주소를 입력해주세요.", Toast.LENGTH_SHORT).show()
//            }
//        }
//        builder.setNegativeButton("취소") { dialog, which ->
//            dialog.dismiss()
//        }
//        builder.show()
//    }
//}