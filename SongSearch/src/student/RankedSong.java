/***************************************************************************
 * Revision History (newest first)
 ***************************************************************************
 * 2018 Alexander Millet starting code
 **************************************************************************/
package student;

/**
 * The RankedSong class contains all of the data in a Song and adds a rank,
 * based on it's relevance to a particular match. The RankedSong is naturally
 * ordered by its rank rather than alphabetically by author.
 * @author Alexander Millet
 */
public class RankedSong extends Song {
    private final int rank;
    
    /**
     * The RankedSong constructor takes a Song and a ranking as arguments
     * @param song
     * @param rank 
     */
    public RankedSong(Song song, int rank){
        super(song.getArtist(),song.getTitle(),song.getLyrics());
        this.rank = rank;
    }
    /**
     * RankedSong is ordered by its rank to make finding relevant matches convenient
     * @param song2 Another RankedSong
     * @return positive int for greater than, negative for less than, 0 for equal
     */
    @Override
    public int compareTo(Song song2){
        return this.rank - ((RankedSong)song2).getRank();
    }
    
    /**
     * Output is rank, followed by artist, followed by title
     * @return output string
     */
    @Override
    public String toString(){
        return Integer.toString(rank)+" "+super.toString();
    }
    /**
     * Converts the RankedSong back into a Song if the rank is not necessary
     * @return Song without the rank
     */
    public Song toSong(){
        return new Song(this.artist, this.title, this.lyrics);
    }
    public int getRank(){
        return this.rank;
    }
}
