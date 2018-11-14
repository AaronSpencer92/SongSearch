/*****************************************************************************
 * Revision History
 *****************************************************************************
 * 2018 Revisions by Alexander Millet
 * 2018 Aaron Spencer created code and documentation
 *****************************************************************************/
package student;
import java.util.*;
/**
 * Search By Title Prefix searches songs in the song database by title, matching
 * with the entire, or the first part, of the title String.
 * @author Aaron Spencer
 */
public class SearchByTitlePrefix {
    private RaggedArrayList songs;
    Song.CmpTitle cmp;
   
    /**
     * Constructs a RaggedArrayList from the SongCollection
     * @param songCollection builds a raggedArray from this songCollection
     */
    public SearchByTitlePrefix(SongCollection songCollection){
        cmp = new Song.CmpTitle();
        songs = new RaggedArrayList(cmp);
        //we cannot access songs directly from songCollections, so we use an array
        Song[] songArray = songCollection.getAllSongs();
        //iterate through each song and add it to the RaggedArray
        for (Song song : songArray) {
            songs.add(song);
        }
    }
    /**
     * Searches the RaggedArrayList for all matches to the title prefix, uses
     * sublist to keep only the matches, and uses toArray to return the expected
     * array of songs
     * @author Aaron Spencer
     * @param titlePrefix
     * @return an array of Songs that match the title prefix
     */
    public Song[] search(String titlePrefix){
        //we want to include all titles that contain the prefix at the beginning,
        //and stop immediately after that. So we increment the last character
        //of the prefix, and use that as the end parameter in sublist
        String endTitle = titlePrefix.substring(0,titlePrefix.length()-1);
        endTitle = endTitle + (char)(titlePrefix.charAt(titlePrefix.length()-1)+1);
        //we make empty Songs to search only the title
        Song fromSong = new Song("",titlePrefix,"");
        Song toSong = new Song("",endTitle,"");
        //generate a new RaggedArrayList with only the matches
        RaggedArrayList result = songs.subList(fromSong,toSong);
        //toArray requires an array of the correct size
        Song[] resultArray = new Song[result.size()];
        return (Song[])result.toArray(resultArray);
    }
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("usage: prog songfile [search string]");
            return;
        }
        // create a new songCollection, and make our SearchByTitlePrefix from it
        SongCollection sc = new SongCollection(args[0]);
        SearchByTitlePrefix sbtp = new SearchByTitlePrefix(sc);
        //Now that the RaggedArrayList is built, we can see how many comparisons it took
        System.out.println("Comparisons: "+sbtp.cmp.cmpCnt);

        if (args.length > 1) {
            System.out.println("searching for: " + args[1]);
            Song[] byTitleResult = sbtp.search(args[1]);
            System.out.println("Number of matches found: " +
                    byTitleResult.length);
            if (byTitleResult.length > 0){
                //prints the first 10 songs
                System.out.println("These are the first 10 songs:");
                for (int i = 0; i < byTitleResult.length && i < 10; i++){
                    System.out.println(byTitleResult[i]);
                }
            }
            else {
                System.out.println("No matches found");
            }
        }
    }
}
