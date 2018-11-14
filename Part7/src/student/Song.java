/***************************************************************************
 * Revision History (newest first)
 ***************************************************************************
 * 2018 Vichey Sun adds CmpTitle
 * 2018 Aaron Spencer Completed code and comments
 * 2016 Anne Applin formatting and JavaDoc added 
 * 2015 Starting code by Prof. Boothe 
 **************************************************************************/
package student;

import java.util.*;


/**
 * Song class to hold strings for a song's artist, title, and lyrics
 * Do not add any methods, just implement the ones that are here.
 * @author boothe
 */
public class Song implements Comparable<Song> {
    /**
     * document each field
     */
    private String artist;
    private String title;
    private String lyrics;
    
    /**
     * Parameterized constructor
     * @param artist the author of the song
     * @param title the title of the song
     * @param lyrics the lyrics as a string with linefeeds embedded
     */
    public Song(String artist, String title, String lyrics) {
        this.artist = artist;
        this.title = title;
        this.lyrics = lyrics;
        
    }
    /**
     * /Comparator class for ordering artists in two Song objects
     * @param song1 the first song to compare
     * @param song2 the second song to compare
    */
    public static class CmpArtist extends CmpCnt implements Comparator<Song>{
        @Override
        public int compare(Song song1, Song song2){
            cmpCnt++;
            return song1.artist.compareToIgnoreCase(song2.artist);
        }
    }
    /**
     * @author Vichey Sun
     * Comparator class for ordering Song objects by title
     * @param song1 the first Song to compare
     * @param song2 the second Song to compare
     */
    public static class CmpTitle extends CmpCnt implements Comparator<Song>{
        @Override
        public int compare(Song song1, Song song2){
            cmpCnt++;
            return song1.title.compareToIgnoreCase(song2.title);
        }
    }
    /**
     *
     * @return artist the author of the song
     */
    public String getArtist() {
        return this.artist;
    }

    /**
     *
     * @return lyrics the lyrics of the song
     */
    public String getLyrics() {
        return this.lyrics;
    }

    /**
     *
     * @return title the title of the song
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Artist and Title only.
     * @return a formatted string with artist and title 
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.artist).append(", \"").append(this.title).append("\"");
        return str.toString();
    }

    /**
     * the default comparison of songs
     * primary key: artist, secondary key: title
     * used for sorting and searching the song array
     * if two songs have the same artist and title they are considered the same
     * @param song2
     * @return a negative number, positive number or 0 depending on whether 
     *    this song should be  before, after or is the same.  Used for a
     *    "natural" sorting order.  In this case first by author then by 
     *    title so that the all of an artist's songs are together, 
     *    but in alpha order.  Follow the given example.
     */
    @Override
    public int compareTo(Song song2) {
        //the title is used only if the artists are the same
        if (this.getArtist().compareToIgnoreCase(song2.getArtist()) != 0){
            return this.getArtist().compareToIgnoreCase(song2.getArtist());
        }
        else{
            return this.getTitle().compareToIgnoreCase(song2.getTitle());
        }
    }

 
    /**
     * testing method to unit test this class
     * @param args
     */
    public static void main(String[] args) {
        Song s1 = new Song("Professor B",
                "Small Steps",
                "Write your programs in small steps\n"
                + "small steps, small steps\n"
                + "Write your programs in small steps\n"
                + "Test and debug every step of the way.\n");

        Song s2 = new Song("Brian Dill",
                "Ode to Bobby B",
                "Professor Bobby B., can't you see,\n"
                + "sometimes your data structures mystify me,\n"
                + "the biggest algorithm pro since Donald Knuth,\n"
                + "here he is, he's Robert Boothe!\n");

        Song s3 = new Song("Professor B",
                "Debugger Love",
                "I didn't used to like her\n"
                + "I stuck with what I knew\n"
                + "She was waiting there to help me,\n"
                + "but I always thought print would do\n\n"
                + "Debugger love .........\n"
                + "Now I'm so in love with you\n");

        System.out.println("testing getArtist: " + s1.getArtist());
        System.out.println("testing getTitle: " + s1.getTitle());
        System.out.println("testing getLyrics:\n" + s1.getLyrics());

        System.out.println("testing toString:\n");
        System.out.println("Song 1: " + s1);
        System.out.println("Song 2: " + s2);
        System.out.println("Song 3: " + s3);

        System.out.println("testing compareTo:");
        System.out.println("Song1 vs Song2 = " + s1.compareTo(s2));
        System.out.println("Song2 vs Song1 = " + s2.compareTo(s1));
        System.out.println("Song1 vs Song3 = " + s1.compareTo(s3));
        System.out.println("Song3 vs Song1 = " + s3.compareTo(s1));
        System.out.println("Song1 vs Song1 = " + s1.compareTo(s1));
        Comparator<Song> cmp = new Song.CmpArtist();
        System.out.println("testing Comparator:");
        System.out.println("Song1 vs Song2 = " + cmp.compare(s1,s2));
        System.out.println("Song2 vs Song1 = " + cmp.compare(s2,s1));
        System.out.println("Song1 vs Song3 = " + cmp.compare(s1,s3));
        System.out.println("Song3 vs Song1 = " + cmp.compare(s3,s1));
        System.out.println("Song1 vs Song1 = " + cmp.compare(s1,s1));
    }
}
