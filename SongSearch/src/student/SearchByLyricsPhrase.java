/***************************************************************************
 * Revision History (newest first)
 ***************************************************************************
 * 2018 Vichey Sun added testSearch method
 * 2018 Aaron Spencer created the file and added code
 **************************************************************************/
package student;
import java.util.*;
/**
 * Searches for songs in a SongCollection whose lyrics match a phrase. The
 * matches are given in order of relevance to the lyric phrase.
 * @author aaron
 */

public class SearchByLyricsPhrase {
    private Song[] songs;
    /**
     * The constructor stores the array of Songs in the SongCollection
     * @param sc  A song Collection
     */
    public SearchByLyricsPhrase(SongCollection sc){
        songs = sc.getAllSongs();
    }
    public Song[] search(String lyricPhrase){
        List<RankedSong> rankedSongs = new ArrayList<>();
        for (Song song : songs){
            //each song is given a rank based on the relevance to lyricPhrase
            int rank = PhraseRanking.rankPhrase(song.getLyrics(), lyricPhrase);
            if (rank > 0 ){
                //if there is a match, the song is added to rankedSongs
                rankedSongs.add(new RankedSong(song,rank));
            }
        }
        //The ArrayList is converted to an array and sorted by natural order,
        //which is based on rank
        RankedSong[] orderedSongs = new RankedSong[rankedSongs.size()];
        rankedSongs.toArray(orderedSongs);
        Arrays.sort(orderedSongs);
        //The RankedSongs, now in sorted order, are converted back to Songs and
        //placed in an array
        Song[] result = new Song[orderedSongs.length];
        for (int i = 0; i < result.length; i++){
            result[i] = orderedSongs[i].toSong();
        }
        return result;//these songs are sorted by rank
    }
    /**
     * testSearch sorts the songs by rank based on the lyricsPhrase using the
     * same method in search. Then the total number of matches are given, and
     * the top 10 matches are printed in sorted order.
     * @author Vichey Sun
     * @param lyricPhrase 
     */
    private void testSearch(String lyricPhrase){
        List<RankedSong> rankedSongs = new ArrayList<>();
        for (Song song : songs){
            //each song is given a rank based on the relevance to lyricPhrase
            int rank = PhraseRanking.rankPhrase(song.getLyrics(), lyricPhrase);
            if (rank > 0 ){
                //if there is a match, the song is added to rankedSongs
                rankedSongs.add(new RankedSong(song,rank));
            }
        }
        //The ArrayList is converted to an array and sorted by natural order,
        //which is based on rank
        RankedSong[] orderedSongs = new RankedSong[rankedSongs.size()];
        rankedSongs.toArray(orderedSongs);
        Arrays.sort(orderedSongs);
        //prints the test data for the ranked Songs
        System.out.println("Total Songs = "+orderedSongs.length+", first 10 matches:");
        for (int i = 0; i < orderedSongs.length && i < 10; i++){
            System.out.println(orderedSongs[i]);
        }
    }
    
    public static void main(String args[]){
        if (args.length == 0) {
            System.err.println("usage: prog songfile [search string]");
            return;
        }

        SongCollection sc = new SongCollection(args[0]);
        SearchByLyricsPhrase sblp = new SearchByLyricsPhrase(sc);

        if (args.length > 1) {
            sblp.testSearch(args[1]);
        }
    }
}
