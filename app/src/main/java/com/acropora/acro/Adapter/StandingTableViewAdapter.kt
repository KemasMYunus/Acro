package com.acropora.acro.Adapter

import android.graphics.Color
import android.view.Gravity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.acropora.acro.Model.StandingModel
import com.acropora.acro.R

class StandingTableViewAdapter(private val movieList: List<StandingModel>) : RecyclerView.Adapter<StandingTableViewAdapter.RowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.table_list_item, parent, false)
        return RowViewHolder(itemView)
    }

    private fun setHeaderBg(view: View) {
        view.setBackgroundResource(R.drawable.table_header_cell_bg)
    }

    private fun setContentBg(view: View) {
        view.setBackgroundResource(R.drawable.table_content_cell_bg)
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        val rowPos = holder.bindingAdapterPosition

        if (rowPos == 0) {
            // Header Cells. Main Headings appear here
            holder.apply {
                setHeaderBg(txtRank)
                setHeaderBg(txtClub)
                setHeaderBg(txtGame)
                setHeaderBg(txtWin)
                setHeaderBg(txtLose)
                setHeaderBg(txtPoint)
                txtRank.apply {
                    text = "Rank"
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    setTextColor(Color.WHITE)
                }
                txtClub.apply {
                    text = "Club"
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    setTextColor(Color.WHITE)
                }
                txtGame.apply {
                    text = "Game"
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    setTextColor(Color.WHITE)
                }
                txtWin.apply {
                    text = "Win"
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    setTextColor(Color.WHITE)
                }
                txtLose.apply {
                    text = "Lose"
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    setTextColor(Color.WHITE)
                }
                txtPoint.apply {
                    text = "Point"
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    setTextColor(Color.WHITE)
                }
            }
        } else {
            val modal = movieList[rowPos - 1]

            holder.apply {
                setContentBg(txtRank)
                setContentBg(txtClub)
                setContentBg(txtGame)
                setContentBg(txtWin)
                setContentBg(txtLose)
                setContentBg(txtPoint)

                txtRank.apply {
                    text = modal.rank.toString()
                    gravity = Gravity.CENTER
                }
                txtClub.apply {
                    text = modal.club
                }
                txtGame.apply {
                    text = modal.game.toString()
                    gravity = Gravity.CENTER
                }
                txtWin.apply {
                    text = modal.win.toString()
                    gravity = Gravity.CENTER
                }
                txtLose.apply {
                    text = modal.lose.toString()
                    gravity = Gravity.CENTER
                }
                txtPoint.apply {
                    text = modal.point.toString()
                    gravity = Gravity.CENTER
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return movieList.size + 1 // one more to add header row
    }

    inner class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtRank: TextView = itemView.findViewById(R.id.txtRank)
        val txtClub: TextView = itemView.findViewById(R.id.txtClub)
        val txtGame: TextView = itemView.findViewById(R.id.txtGame)
        val txtWin: TextView = itemView.findViewById(R.id.txtWin)
        val txtLose: TextView = itemView.findViewById(R.id.txtLose)
        val txtPoint: TextView = itemView.findViewById(R.id.txtPoint)

    }
}