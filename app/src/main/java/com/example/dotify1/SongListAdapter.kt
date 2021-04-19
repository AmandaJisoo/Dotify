package com.example.dotify1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song
import com.example.dotify1.databinding.SongBinding

class SongListAdapter(var songList: MutableList<Song>) : RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {
    var onSongClickListener: ((song: Song) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = SongBinding.inflate(LayoutInflater.from(parent.context))
        return SongViewHolder(binding)
    }

    override fun getItemCount(): Int = songList.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val curSong = songList[position]

        with(holder.binding) {
            songTitle.text = curSong.title
            singer.text = curSong.artist
            albumCover.setImageResource(curSong.smallImageID)
            song.setOnClickListener{
                onSongClickListener?.invoke(curSong)
            }
        }
    }

    fun updateSongs (shuffledSongs: List<Song>) {
        // Animated way of applying updates to list
        val checkDiff = SongDiffCallback(songList, shuffledSongs)
        val diffResult = DiffUtil.calculateDiff(checkDiff)
        diffResult.dispatchUpdatesTo(this)
        songList = shuffledSongs as MutableList<Song>
    }

    class SongViewHolder(val binding: SongBinding): RecyclerView.ViewHolder(binding.root)
}