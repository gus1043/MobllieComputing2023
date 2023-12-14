package com.example.sqlitepractice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitepractice.databinding.ListItemBinding
import com.example.sqlitepractice.roomdb.MyTable

class MyAdapter(private var dataSet: MutableList<MyTable>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root)
    override fun getItemCount() = dataSet.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun setList(newList: MutableList<MyTable>): MutableList<MyTable> {
        dataSet = newList
        notifyDataSetChanged()
        return dataSet
    }

    fun addItem(item: MyTable) {
        dataSet.add(item)
        notifyDataSetChanged()
    }

    fun getElement(pos: Int): MyTable {
        return dataSet[pos]
    }

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onBindViewHolder(holder : MyViewHolder, position : Int) {
        val binding = (holder as MyViewHolder).binding
        binding.rank.text = dataSet[position].rank.toString()
        binding.albumCover.setImageDrawable (
            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_launcher_background))
        binding.title.text = dataSet[position].title
        binding.artist.text = dataSet[position].artist
        binding.album.text = dataSet[position].album
        binding.numLike.text = dataSet[position].num_like.toString()
        binding.root.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }
}
