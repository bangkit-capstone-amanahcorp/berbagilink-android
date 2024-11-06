package com.example.ptamanah.adapter.navigation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import com.example.ptamanah.R

class ExpandableListAdapter(
    private val context: Context,
    private val titleList: List<String>,
    private val dataList: HashMap<String, List<String>>
) : BaseExpandableListAdapter() {

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return dataList[titleList[listPosition]]?.get(expandedListPosition) ?: ""
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val expandedListText = getChild(listPosition, expandedListPosition) as String
        if (convertView == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_item_nav_child, null)
        }
        val expandedListTextView = convertView!!.findViewById<TextView>(R.id.lblListItem)
        expandedListTextView.text = expandedListText
        return convertView
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return dataList[titleList[listPosition]]?.size ?: 0
    }

    override fun getGroup(listPosition: Int): Any {
        return titleList[listPosition]
    }

    override fun getGroupCount(): Int {
        return titleList.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val listTitle = getGroup(listPosition) as String
        if (convertView == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_item_nav_group, null)
        }
        val listTitleTextView = convertView!!.findViewById<TextView>(R.id.lblListHeader)
        listTitleTextView.text = listTitle
        val arrowIcon = convertView.findViewById<ImageView>(R.id.imageArrow)
        arrowIcon.setImageResource(if (isExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down)
        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }

}