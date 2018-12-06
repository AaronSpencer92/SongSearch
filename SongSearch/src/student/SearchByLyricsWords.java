/*****************************************************************************
 * Revision History
 *****************************************************************************
 * 2018 Alexander Millet added code and documentation
 * 2018 Aaron Spencer created code and documentation
 *****************************************************************************/
package student;
import java.util.*;

/**
 *This class will search a Song Collection by words found in the lyrics.
 * @author Aaron Spencer
 */
public class SearchByLyricsWords {
    private final TreeMap<String, TreeSet<Song>> words;
    //this set of common words will not be stored in our map
    //this maps every unique word to every song that uses the word
    private static final String[] COMMON = {"the", "of", "and", "a", "to", "in", "is", "you",
        "that", "it", "he", "for", "was", "on", "are", "as", "with", "his",
        "they", "at", "be", "this", "from", "I", "have", "or", "by", "one",
        "had", "not", "but", "what", "all", "were", "when", "we", "there",
        "can", "an", "your", "which", "their", "if", "do", "will", "each",
        "how", "them", "then", "she", "many", "some", "so", "these", "would",
        "into", "has", "more", "her", "two", "him", "see", "could", "no",
        "make", "than", "been", "its", "now", "my", "made", "did", "get", "our",
        "me", "too"};
    private static final Set<String> commonWords = 
                new HashSet<>(Arrays.asList(COMMON));
    public SearchByLyricsWords(SongCollection sc){
        words = new TreeMap();
        Song[] songArray = sc.getAllSongs();
        //we iterate through every song, and then break up the lyrics into words
        for (Song song : songArray){
            //this regular expression matches any number of non-alphabet
            //characters, and single letter words. So the substrings are all
            //the two letter or more words, ignoring numbers and puncuation
            String[] lyrics = song.getLyrics().split(
                    "((?<![A-Za-z])[A-Za-z](?![A-Za-z])|[^A-Za-z])+");
            //we iterate through every word in every song
            for (String word : lyrics){
                //we convert each word to lower case
                word = word.toLowerCase();
                //making sure to exclude the common words
                if (!commonWords.contains(word)){
                    //only make a new word/TreeSet mapping if one does not exist
                    if (!words.keySet().contains(word)){
                        words.put(word, new TreeSet());
                    }
                    //add a reference to that song to the TreeSet mapped to the word
                    words.get(word).add(song);
                }
            }
                    
        }
        //when the beginning of a String matches the delimeter, String.split 
        //gives a blank string as the first element in the returned String array.
        //this code removes the first mapping if the key is an empty string
        if (words.firstKey().length() == 0){
            words.pollFirstEntry();
        }
    }
    /**
     * statistics simply prints information about the size of our data structure.
     * The number of keys and song references are given, then the size of the 
     * structure in bytes.
     * @author Alexander Millet
     * 
     */
    public void statistics(){
        System.out.println("Number of keys: "+words.size());
        int i = 0;
        for (String word : words.keySet()){
            i += words.get(word).size();
        }
        System.out.println("Number of Song references: "+i);
        //all objects are stored as 8 byte references
        System.out.println("Total size of keys in bytes: "+words.size()*8);
        System.out.println("Total size of Song references in bytes: "+i*8);
        System.out.println("Total size of data structure in bytes: "+
                ((words.size()*8) + (i*8)));
        System.out.println("Space usage in terms of N where N is the total"
        +" number of Song references and K is the number of words mapped:");
        System.out.println("Space = 8N + 8K");
    }
    /**
     * find all songs that contain all of the words in lyricWords. Return an
     * array of Songs.
     * 
     * @param lyricWords words found in song lyrics
     * @return an array of Songs that match all lyricWords
     */
    public Song[] search(String lyricWords){
        Set<String> searchWords = new HashSet<>();
        //we use a shallow copy of our map, and remove keys that don't match
        TreeMap<String, TreeSet<Song>> mapCopy = (TreeMap)words.clone();
        //split lyricWords into strings of words at least 2 letters long
        String[] search = lyricWords.split(
                    "((?<![A-Za-z])[A-Za-z](?![A-Za-z])|[^A-Za-z])+");
        //make sure all search words are lower case(because the search is case
        //insensitive) and add them to a hashSet for comparisons
        for (String word : search){
            word = word.toLowerCase();
            searchWords.add(word);
        }
        //remove the common words from our search term
        searchWords.removeAll(commonWords);
        //returns an empty array when searches are only common words or single 
        //letter searches, or when one of the search words isn't in the map
        if (!searchWords.isEmpty() && mapCopy.keySet().containsAll(searchWords)){
            //removes all map entries not included in the search
            mapCopy.keySet().retainAll(searchWords);
            //result is where we store the songs that match all search words. 
            //we start with a copy of the first word in the search set, then
            //iterate through the set
            Set<Song> result = (TreeSet)mapCopy.pollFirstEntry().getValue().clone();
            for (String word : mapCopy.keySet()){
                //only words that match every set will be retained by the end
                result.retainAll(mapCopy.get(word));
            }
            Song[] arrResult = new Song[result.size()];
        return result.toArray(arrResult);
        }
        //return an empty array
        else{
            return new Song[0];
        }
    }
    /**
     * top10Words finds the words referenced in the greatest number of songs, 
     * and returns a String array of the top 10 of these words in sorted order.
     * @author Aaron Spencer
     */
    public String[] top10Words(){
        //we use parallel arrays for the top 10 words and the number of 
        //references for each word, and keep them in sorted order
        String[] topWords = new String[10];
        int[] timesUsed = new int[10];
        //we iterate through each word in our keyset, and add it to our arrays
        //in sorted order if they are in the top 10 so far
        for (String word : words.keySet()){
            for (int i = 0; i < topWords.length; i++){
                //we start at the top of the array, and insert a word if it's
                //used more often than the word currently at that index
                if (words.get(word).size() > timesUsed[i]){
                    int j = timesUsed.length-1;
                    //before we insert the new word, we move down all of the 
                    //words below it
                    while (j > i){
                        timesUsed[j] = timesUsed[j-1];
                        topWords[j] = topWords[j-1];
                        j--;
                    }
                    //insert the new word
                    topWords[i] = word;
                    timesUsed[i] = words.get(word).size();
                    //after we insert a word we don't want to insert it again
                    //next index, so we move on to the next word
                    break;
                }
            }
        }
        //print the words
        for (int i = 0; i < topWords.length; i++){
            System.out.println(topWords[i]+ " used: "+timesUsed[i]+ " times.");
        }
        //return the array of top words in case we need to do something with them
        return topWords;
    }
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("usage: prog songfile [search string]");
            return;
        }
        // create a new songCollection, and make our SearchByTitlePrefix from it
        SongCollection sc = new SongCollection(args[0]);
        SearchByLyricsWords sblw = new SearchByLyricsWords(sc);
        if (args.length > 1){
            Song[] searcher = sblw.search(args[1]);
            System.out.println(args[1]);
            System.out.println("Number of matches: "+ searcher.length);
            System.out.println("First 10 matches:");
            for (int i = 0; i < 10 && i < searcher.length; i++){
                System.out.println(searcher[i]);
            }
        }
        //print the top 10 words if the -top10words flag is used
        if (args.length > 1 && args[1].equals("-top10words")){
           System.out.println("These are the top 10 most referenced words in our structure:");
           sblw.top10Words();
        }
        
    }
    
}
