package cat.copernic.daniel.marketcomparator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

class ExpandListAdapter : BaseExpandableListAdapter() {

    private lateinit var _context : Context
    private lateinit var _listDataHeader : List<String>
    private lateinit var _listDataChild : HashMap<String, List<String>>

    fun ExpandListAdapter(context : Context,listDataHeader : List<String>, listChildData : HashMap<String, List<String>> ){
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    override fun getGroupCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getGroup(groupPosition: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))!!
                    .get(childPosition)!!
    }

    override fun getGroupId(groupPosition: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        TODO("Not yet implemented")
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val childText : String = getChild(groupPosition,childPosition) as String

        if(convertView == null){
            var infalInflater = this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var convertView = infalInflater.inflate(R.layout.list_item,null)
        }
        var txtListChild : TextView  = convertView!!.findViewById(R.id.lblListItem)

        txtListChild.setText(childText)
        return convertView!!
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

}