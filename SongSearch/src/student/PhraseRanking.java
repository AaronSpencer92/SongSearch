/*****************************************************************************
 * Revision History
 *****************************************************************************
 * 2018 Aaron Spencer created code and documentation
 *****************************************************************************/
package student;
import java.util.regex.*;

/**
 *The PhraseRanking class at the moment exists to contain the static method 
 * rankPhrase
 * @author Aaron Spencer
 */
public abstract class PhraseRanking {
    
    /**
     * rankPhrase evaluates a song's lyrics based on how well it matches the 
     * search term lyricsPhrase. All of the words in the search term must be 
     * present in order, but any number of words may come between them. The 
     * rank is given base on how long the string of the actual match is, with 
     * lower ranks being a better score. If there are multiple matches, the best
     * one is the rank returned. If no matches are found, or the search is
     * invalid, -1 is returned.
     * @param lyrics The lyrics of the Song to be searched
     * @param lyricsPhrase The search term applied to lyrics
     * @return a rank of how well a song matches a search term, or -1 on failure
     */
    public static int rankPhrase(String lyrics, String lyricsPhrase){
        //remove leading and trailing whitespace from the search terms, and 
        //split the words by whitespace
        String[] searchPhrase = lyricsPhrase.trim().split("\\W+");
        //check for special cases where the search is one or fewer words
        if (searchPhrase.length <= 0){
            return -1;
        }
        //the search with one word is trivial; if there is a match, it is the length of the word
        else if (searchPhrase.length == 1){
            Pattern pattern = Pattern.compile("\\b"+searchPhrase[0]+"\\b");
            Matcher matcher = pattern.matcher(lyrics);
            if (matcher.find()){
                return searchPhrase[0].length();
            }
            else{
                return -1;
            }  
        }
        //This is the main search algorithm
        else{
            int rank = lyrics.length();
            //we build a regular expression from the search words.
            StringBuilder regex = new StringBuilder();
            //The first word is surrounded by word boundaries. This first word
            //is the only thing in the regex that is actually matched; everything
            //else is in a lookahead. This guarantees that we don't skip the
            //shortest match in the instance that it is within a larger match
            regex.append("\\b").append(searchPhrase[0]).append("\\b");
            regex.append("(?=");
            //this loop executes for the middle words. The last word we treat specially
            for (int i = 1; i < searchPhrase.length - 1; i++){
                //the rest of the words can have any number of characters between
                //words, as long as the words match in order
                regex.append(".*?\\b").append(searchPhrase[i]).append("\\b");
            }
            regex.append(".*?\\b");
            //we enclose the last word in parentheses, putting it into a capture
            //group. This allows us to get the position of the end of the match
            //even though the regex engine doesn't match with the rest of the words
            regex.append("(").append(searchPhrase[searchPhrase.length-1]).append(")\\b)");
            boolean match = false;
            //we use the case insensitive and dotall flags; dotall allows the 
            //dot metacharacter to be applied to line breaks so the search spans
            //multiple lines
            Pattern pattern = Pattern.compile(regex.toString(),
                    Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(lyrics);
            while (matcher.find()){
                match = true;
                if (matcher.end(1) - matcher.start() < rank){
                    //this way rank finds the smallest possible match
                    rank = matcher.end(1) - matcher.start();
                }
            }
            if (match){
                return rank;
            }
            else{
                return -1;
            }
        }
    }
   
 
    public static void main(String[] args){
        if (args.length < 2){
            System.out.println("Error: Too Few Arguments");
        }
        SongCollection sc = new SongCollection(args[0]);
        Song[] songs = sc.getAllSongs();
        int rank;
        int numRanks = 0;
        for (Song i : songs){
            rank = PhraseRanking.rankPhrase(i.getLyrics(), args[1]);
            if (rank > 0){
                numRanks++;
                System.out.println(i+" "+rank);
            }
        }
        System.out.println("Number of matches: "+numRanks);
    }
}
