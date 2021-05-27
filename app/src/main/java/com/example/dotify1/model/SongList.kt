import com.example.dotify1.model.Song

data class SongList(
    val title: String,
    val numOfSongs: Int,
    val songs: List<Song>,
)
