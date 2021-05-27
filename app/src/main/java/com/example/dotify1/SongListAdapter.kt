package com.example.dotify1

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dotify1.databinding.SongBinding
import com.example.dotify1.model.Song


class SongListAdapter(private var listOfSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

        var onSongClickListener: ((song: Song) -> Unit)? = null
    var onSongLongPressClickListener: ((title: String, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = SongBinding.inflate(LayoutInflater.from(parent.context))
        return SongViewHolder(binding)
    }

    override fun getItemCount(): Int = listOfSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val curSong = listOfSongs[position]

        with(holder.binding) {
            songTitle.text = curSong.title
            singer.text = curSong.artist
            albumCover.load(curSong.smallImageURL)
            song.setOnClickListener{
                onSongClickListener?.invoke(curSong)
            }

            song.setOnLongClickListener {
                onSongLongPressClickListener?.invoke(curSong.title, position)
                notifyDataSetChanged()
                return@setOnLongClickListener true
            }
        }
    }

    fun updateSongs (shuffledSongs: List<Song>) {
        // Animated way of applying updates to list
        val checkDiff = SongDiffCallback(shuffledSongs as MutableList<Song>, listOfSongs)
        val diffResult = DiffUtil.calculateDiff(checkDiff)
        diffResult.dispatchUpdatesTo(this)
        listOfSongs = shuffledSongs
    }

    class SongViewHolder(val binding: SongBinding): RecyclerView.ViewHolder(binding.root)
}