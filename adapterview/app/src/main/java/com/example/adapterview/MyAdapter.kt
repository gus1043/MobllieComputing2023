package com.example.adapterview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AbsListView.RecyclerListener
import androidx.recyclerview.widget.RecyclerView
import com.example.adapterview.databinding.ListItemBinding

class MyAdapter(private val dataSet:Array<String>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){
    }

    override fun getItemCount() = dataSet.size

    override fun OnCreateViewHolder(parent: ViewGroup, viewType:Int) : MyViewHolder {
      return MyViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        binding.itemText.text=dataSet[position]
        binding.itemRoot.setOnClickListener{
            Log.d("TAG", "position $position clicked")
        }
    }
}
