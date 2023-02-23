package com.amarturelo.usersgithub.presentation.users.adapter.model

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.amarturelo.usersgithub.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.amarturelo.usersgithub.ext.dp
import com.amarturelo.usersgithub.presentation.users.vo.UserListItemVO

@EpoxyModelClass
abstract class UserItemModel : EpoxyModelWithHolder<UserItemModel.Holder>() {

    @EpoxyAttribute
    var itemClickedListener: (UserListItemVO) -> Unit = { }

    @EpoxyAttribute
    lateinit var item: UserListItemVO

    override fun getDefaultLayout(): Int = R.layout.user_view_item

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.tvFullNameUser.text = item.login
        holder.tvPeopleDescription.text = item.login
        Glide.with(holder.root)
            .load(item.avatarUrl)
            .transform(CenterCrop(), RoundedCorners(16.dp))
            .into(holder.ivUser)

        holder.root.setOnClickListener {
            itemClickedListener(item)
        }
    }

    class Holder : EpoxyHolder() {
        lateinit var root: View
        lateinit var ivUser: ImageView
        lateinit var tvFullNameUser: TextView
        lateinit var tvPeopleDescription: TextView
        override fun bindView(itemView: View) {
            root = itemView
            ivUser = itemView.findViewById(R.id.ivUser)
            tvFullNameUser = itemView.findViewById(R.id.tvUserFullName)
            tvPeopleDescription = itemView.findViewById(R.id.tvUserDescription)
        }

    }
}