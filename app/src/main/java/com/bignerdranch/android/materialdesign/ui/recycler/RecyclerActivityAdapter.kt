package com.bignerdranch.android.materialdesign.ui.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.materialdesign.R

class RecyclerActivityAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: MutableList<Pair<Data, Boolean>>
) : RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

    companion object {
        private const val TYPE_NOTE = 0
        private const val TYPE_HEADER = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_NOTE) {
            NoteViewHolder(
                inflater.inflate(R.layout.recycler_activity_item, parent, false) as View
            )
        } else {
            HeaderViewHolder(
                inflater.inflate(R.layout.recycler_header, parent, false) as View
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADER
            else -> TYPE_NOTE
        }
    }

    fun appendItem() {
        data.add(generateItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateItem() = Pair(Data("New Note", ""), false)

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class NoteViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        override fun bind(data: Pair<Data, Boolean>) {
            itemView.apply {
                findViewById<TextView>(R.id.recycler_view_item_text).text = data.first.newNote
                setOnClickListener {
                    onListItemClickListener.onItemClick(data.first)
                }
                findViewById<TextView>(R.id.recycler_view_item_text).setOnClickListener {
                    toggleText()

                }
                findViewById<AppCompatEditText>(R.id.recycler_view_item_edit_text).visibility =
                    if (data.second) View.VISIBLE else View.GONE
                findViewById<AppCompatImageView>(R.id.addItemImageView).setOnClickListener {
                    addItem()
                }
                findViewById<AppCompatImageView>(R.id.removeItemImageView).setOnClickListener {
                    removeItem()
                }
            }

        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        private fun generateItem() = Data("New note", "")
        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }

        private fun addItem() {
            data.add(layoutPosition, Pair(generateItem(), false))
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun toggleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<Data, Boolean>) {
        }
    }
}