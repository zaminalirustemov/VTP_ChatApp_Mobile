package com.asparagas.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asparagas.activity.ChatActivity
import com.asparagas.model.User
import com.asparagas.vtp_chatapp.R
import com.asparagas.vtp_chatapp.databinding.ItemUserBinding
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(private val context: Context, private val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate( LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = userList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.txtUserName.text = user.userName
        Glide.with(context).load(user.profileImage).placeholder(R.drawable.profile_image).into(holder.imgUser)

        holder.layoutUser.setOnClickListener {
            val intent = Intent(context,ChatActivity::class.java)
            intent.putExtra("userId",user.userId)
            intent.putExtra("userName",user.userName)
            context.startActivity(intent)
        }
    }

    class ViewHolder(val binding : ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        val txtUserName: TextView = binding.root.findViewById(R.id.userName)
        val txtTemp:TextView = binding.root.findViewById(R.id.temp)
        val imgUser: CircleImageView = binding.root.findViewById(R.id.userImage)
        val layoutUser: LinearLayout = binding.root.findViewById(R.id.layoutUser)
    }
}