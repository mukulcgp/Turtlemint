package com.tur.turtlemint.presentation.issues

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
import com.tur.turtlemint.data.source.local.entity.IssueEntity
import com.tur.turtlemint.presentation.loadImage
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class CardAdapterLocal(var cardModelList: List<IssueEntity>) : RecyclerView.Adapter<ViewHolderStoriesLocal>() {

    override fun getItemCount() = cardModelList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderStoriesLocal {
        var viewHolder = ViewHolderStoriesLocal(parent.inflate(R.layout.holder_issues_local))
        return viewHolder
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolderStoriesLocal, position: Int) {

        val itemCardModel = cardModelList[position]
        holder.bindPhoto(itemCardModel, position)
        holder.itemView.setOnClickListener {

        }
    }

}

class ViewHolderStoriesLocal(itemView: View) : RecyclerView.ViewHolder(itemView)/*, View.OnClickListener*/ {
    //2
    private var view: View = itemView
    private var cardModel: IssueEntity? = null

    companion object {
        private var FRAGMENT_NAME = ""
        private var POSITION = -1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun bindPhoto(cardModel: IssueEntity, position: Int) {
        this.cardModel = cardModel
        POSITION = position

        val tvName: TextView = view.findViewById(R.id.tv_name_local)
        val tvDesc: TextView = view.findViewById(R.id.tv_desc_local)
        val tvUser: TextView = view.findViewById(R.id.tv_user_local)
        val tvUpdate: TextView = view.findViewById(R.id.tv_updated_local)



        tvName.text = cardModel.title
        tvDesc.text = cardModel.desc
        tvUser.text = cardModel.uName



        var uDate = cardModel.uDate!!.substring(0,10)

        var dd = uDate.substring(8, 10)
        var mm = uDate.substring(5, 7)
        var yyyy = uDate.substring(0, 4)

        tvUpdate.text = dd+"-"+mm+"-"+yyyy


    }
}

private fun ViewGroup.inflate(@LayoutRes layout: Int, attach: Boolean = false) =
    LayoutInflater.from(context).inflate(layout, this, attach)

