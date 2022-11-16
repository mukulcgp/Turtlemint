package com.tur.turtlemint.presentation.detail

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.example.IssueResponse
import com.tur.turtlemint.R
import com.tur.turtlemint.domain.model.comments.Comments
import com.tur.turtlemint.presentation.loadImage
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class CardAdapterComments(var cardModelList: List<Comments>) : RecyclerView.Adapter<ViewHolderStories>() {

    override fun getItemCount() = cardModelList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderStories {
        var viewHolder = ViewHolderStories(parent.inflate(R.layout.holder_comments))
        return viewHolder
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolderStories, position: Int) {

        val itemCardModel = cardModelList[position]
        holder.bindPhoto(itemCardModel, position)
        holder.itemView.setOnClickListener {
            }
    }

}

class ViewHolderStories(itemView: View) : RecyclerView.ViewHolder(itemView)/*, View.OnClickListener*/ {
    //2
    private var view: View = itemView
    private var cardModel: Comments? = null

    companion object {
        private var FRAGMENT_NAME = ""
        private var POSITION = -1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun bindPhoto(cardModel: Comments, position: Int) {
        this.cardModel = cardModel
        POSITION = position
        val imageProf: ImageView = view.findViewById(R.id.photo_image_view_comments)
        val imageProg: ProgressBar = view.findViewById(R.id.photo_progress_bar_comments)
        val tvName: TextView = view.findViewById(R.id.tv_name_comments)
        val tvDesc: TextView = view.findViewById(R.id.tv_desc_comments)
        val tvUser: TextView = view.findViewById(R.id.tv_user_comments)
        val tvUpdate: TextView = view.findViewById(R.id.tv_updated_comments)


        imageProf.loadImage(cardModel.user!!.avatarUrl!!, imageProg)
        tvName.text = cardModel.authorAssociation
        tvDesc.text = cardModel.body
        tvUser.text = cardModel.user!!.login



        var uDate = cardModel.updatedAt!!.substring(0,10)

        var dd = uDate.substring(8, 10)
        var mm = uDate.substring(5, 7)
        var yyyy = uDate.substring(0, 4)

        tvUpdate.text = dd+"-"+mm+"-"+yyyy


    }
}

private fun ViewGroup.inflate(@LayoutRes layout: Int, attach: Boolean = false) =
    LayoutInflater.from(context).inflate(layout, this, attach)

