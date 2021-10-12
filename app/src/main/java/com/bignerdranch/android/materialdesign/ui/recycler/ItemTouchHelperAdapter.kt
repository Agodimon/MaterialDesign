package com.bignerdranch.android.materialdesign.ui.recycler

interface ItemTouchHelperAdapter { fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}