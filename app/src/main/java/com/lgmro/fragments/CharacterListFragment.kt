package com.lgmro.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.ClassCastException

class CharacterListFragment : Fragment() {
    private lateinit var listener: OnListSelected
    private lateinit var names: Array<String>
    private lateinit var description: Array<String>
    private lateinit var imageResid: IntArray

    companion object {
        fun newInstance() = CharacterListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)


        val activity = activity as Context
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = CharacterListAdapter(activity)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val resources = context.resources
        names = resources.getStringArray(R.array.names)
        description = resources.getStringArray((R.array.description))

        val typedArray = resources.obtainTypedArray(R.array.Images)
        val imageCount = names.size
        imageResid = IntArray(imageCount)
        for (i in 0 until imageCount) {
            imageResid[i] = typedArray.getResourceId(i, 0)
        }
        typedArray.recycle()

        if (context is OnListSelected) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + "must implemented")
        }
    }

    internal inner class CharacterListAdapter(
        context: Context,
    ) : RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {

        inner class CharacterViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind (character: Character) {
               itemView.findViewById<TextView>(R.id.list_name).text = character.name
               itemView.findViewById<ImageView>(R.id.list_img).setImageResource(character.imageResid)

                }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CharacterViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
            return CharacterViewHolder(view)
        }


        override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
            val character = Character(
                names[position],
                description[position],
                imageResid[position]
            )
            holder.bind(character)
            holder.itemView.setOnClickListener{
                listener.onSelected(character)
            }
        }

        override fun getItemCount() : Int {
            return names.size
        }


    }

    interface OnListSelected {
        fun onSelected(character: Character)
    }

}

