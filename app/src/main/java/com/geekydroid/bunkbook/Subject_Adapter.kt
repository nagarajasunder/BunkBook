package com.geekydroid.bunkbook

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class Subject_Adapter(var list: List<Subject>) :
    RecyclerView.Adapter<Subject_Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.subject_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var subject = list[position]

        holder.sub_name.text = subject.sub_name
        holder.attend_count.text = "${subject.attended}/${subject.total_classes}"
        holder.info.text = "Your are good to go"
        holder.percent.text = "${subject.attendance.toInt()}%"


        holder.main_layout.setOnClickListener {
            var intent = Intent(holder.itemView.context, Subject_view::class.java)
            intent.putExtra("subject", subject)
            holder.itemView.context.startActivity(intent)
        }

        holder.progress.apply {
            progress = 0f
            progressMax = 100f
            setProgressWithAnimation(subject.attendance.toInt().toFloat(), 1500)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var progress: CircularProgressBar = view.findViewById(R.id.progress)
        var sub_name = view.findViewById<TextView>(R.id.sub_name)
        var attend_count = view.findViewById<TextView>(R.id.attend_count)
        var info = view.findViewById<TextView>(R.id.info)
        var percent = view.findViewById<TextView>(R.id.percent)
        var main_layout = view.findViewById<CardView>(R.id.main_layout)

    }
}