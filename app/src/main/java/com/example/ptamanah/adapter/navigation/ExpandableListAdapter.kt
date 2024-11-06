package com.example.ptamanah.adapter.navigation

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.ptamanah.R

class ExpandableListAdapter(
    private val context: Context,
    private val originalItems: List<NavMenuItem>,
    private val onItemClick: (NavMenuItem) -> Unit
) : BaseExpandableListAdapter() {
    // Flatten list untuk menampung semua item yang visible
    private val visibleItems = mutableListOf<NavMenuItem>()
    private val expandedItems = mutableSetOf<String>()

    init {
        // Initialize dengan top level items
        updateVisibleItems()
    }

    private fun updateVisibleItems() {
        visibleItems.clear()
        addItems(originalItems, 0)
    }

    private fun addItems(items: List<NavMenuItem>, level: Int) {
        items.forEach { item ->
            item.level = level
            visibleItems.add(item)
            if (expandedItems.contains(item.id) && item.children.isNotEmpty()) {
                addItems(item.children, level + 1)
            }
        }
    }

    override fun getGroupCount(): Int = visibleItems.size

    override fun getChildrenCount(groupPosition: Int): Int = 0 // We don't use child views

    override fun getGroup(groupPosition: Int): NavMenuItem = visibleItems[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int): Any? = null

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = 0

    override fun hasStableIds(): Boolean = false

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val item = getGroup(groupPosition)
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.list_item_nav_group, parent, false)

        // Calculate padding based on level
        val paddingStart = (item.level * 32) + 16
        view.setPadding(paddingStart.dp(context), 8.dp(context), 16.dp(context), 8.dp(context))

        val menuIcon = view.findViewById<ImageView>(R.id.menuIcon)
        val titleView = view.findViewById<TextView>(R.id.lblListHeader)
        val badgeText = view.findViewById<TextView>(R.id.badgeText)
        val arrowIcon = view.findViewById<ImageView>(R.id.imageArrow)

        // Set icon
        item.icon?.let {
            menuIcon.setImageResource(it)
            menuIcon.visibility = View.VISIBLE
        } ?: run {
            menuIcon.visibility = View.GONE
        }

        titleView.text = item.title

        // Set badge if exists
        if (item.badge != null) {
            badgeText.text = item.badge
            badgeText.visibility = View.VISIBLE
        } else {
            badgeText.visibility = View.GONE
        }

        // Show arrow only for items with children
        if (item.children.isEmpty()) {
            arrowIcon.visibility = View.GONE
        } else {
            arrowIcon.visibility = View.VISIBLE
            arrowIcon.setImageResource(
                if (expandedItems.contains(item.id))
                    R.drawable.ic_arrow_up
                else
                    R.drawable.ic_arrow_down
            )
        }

        view.setOnClickListener {
            // Handle expansion for items with children
            if (item.children.isNotEmpty()) {
                toggleItem(groupPosition)
            }
            onItemClick(item)
        }

        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        return View(context) // We don't use child views
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = false

    fun toggleItem(position: Int) {
        val item = visibleItems[position]
        if (item.children.isNotEmpty()) {
            if (expandedItems.contains(item.id)) {
                expandedItems.remove(item.id)
            } else {
                expandedItems.add(item.id)
            }
            updateVisibleItems()
            notifyDataSetChanged()
        }
    }

    fun Int.dp(context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }
}
