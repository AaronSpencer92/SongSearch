/***************************************************************************
 * Revision History (newest first)
 ***************************************************************************
 * 2018 Aaron Spencer added code and comments + revisions by Vichey Sun
 * 2016 Anne Applin formatting and JavaDoc added 
 * 2015 Starting code by Prof. Boothe 
 **************************************************************************/

package student;

import java.io.*;
import java.util.*;
/**
 * Search by Artist Prefix searches the artists in the song database 
 * for artists that begin with the input String.
 * @author bboothe
 */

public class SearchByArtistPrefix {
    // keep a direct reference to the song array
    private Song[] songs;  

    /**
     * constructor initializes the property. {Done]
     * @param sc a SongCollection object
     */
    public SearchByArtistPrefix(SongCollection sc) {
        songs = sc.getAllSongs();
    }

    /**
     * @author Aaron Spencer
     * find all songs matching artist prefix uses binary search should operate
     * in time log n + k (# matches)
     * converts artistPrefix to lowercase and creates a Song object with 
     * artist prefix as the artist in order to have a Song to compare.
     * walks back to find the first "beginsWith" match, then walks forward
     * adding to the arrayList until it finds the last match.
     *
     * @param artistPrefix all or part of the artist's name
     * @return an array of songs by artists with substrings that match 
     *    the prefix
     */
    public Song[] search(String artistPrefix) {
        //we keep track of comparisons to check the efficiency of the method
        int comparisons = 0;
        String artPrefix = artistPrefix.toLowerCase();
        //this song is used only as a search key; title and lyrics are blank
        Song key = new Song(artPrefix, "", "");
        Comparator<Song> cmp = new Song.CmpArtist();
        //we use the length to exclude artists that match the beginning of 
        //the search prefix, but are shorter than the prefix
        int len = artPrefix.length();
        //finds a match, but not necessarily the first match in linear order
        int i = Arrays.binarySearch(songs, key, cmp);
        //we keep track of comparisons used in the binary search
        comparisons += ((CmpCnt)cmp).getCmpCnt();
        //if an exact match is not found, the binary search will return a
        //negative number where the entry would be found; so we want prefixes
        //indexed at the first partial match
        if (i < 0){
            i = -i -1;
        }
        //saving our place
        int firstIndex = i;
        List<Song> matches = new ArrayList<>();
        //we only compare artists up to the length of the prefix. First
        //we check artists earlier in the array until there are no more matches,
        //then go forward, adding them to matches in sorted order
        while (i >= 0 && 
                    songs[i].getArtist().length() >= len &&
                    songs[i].getArtist().substring(0, len).
                    compareToIgnoreCase(artPrefix) == 0) {
            comparisons++;
            i--;
            }
        //that loop goes one too far
        i++;
        //we don't need to compare these records again
        while (i <= firstIndex){
            matches.add(songs[i]);
            i++;
        }
        while (i < songs.length && 
                    songs[i].getArtist().length() >= len && 
                    songs[i].getArtist().substring(0, len).
                    compareToIgnoreCase(artPrefix) == 0) {
            comparisons++;
            matches.add(songs[i]);
            i++;
            }
        System.out.println("Number of comparisons made: " + comparisons);
        Song[] output = matches.toArray(new Song[matches.size()]);
        return output;
    }

    /**
     * testing method for this unit
     * @param args  command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("usage: prog songfile [search string]");
            return;
        }

        SongCollection sc = new SongCollection(args[0]);
        SearchByArtistPrefix sbap = new SearchByArtistPrefix(sc);

        if (args.length > 1) {
            System.out.println("searching for: " + args[1]);
            Song[] byArtistResult = sbap.search(args[1]);
            System.out.println("Number of matches found: " +
                    byArtistResult.length);
            if (byArtistResult.length > 0){
                System.out.println("These are the first 10 songs:");
                for (int i = 0; i < byArtistResult.length && i < 10; i++){
                    System.out.println(byArtistResult[i]);
                }
            }
            else {
                System.out.println("No matches found");
            }
        }
    }
}
