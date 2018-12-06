/***************************************************************************
 * Revision History (newest first)
 ***************************************************************************
 * 2018 Aaron Spencer Completed code and comments
 * 2016 Anne Applin formatting and JavaDoc added 
 * 2015 Starting code by Prof. Boothe 
 **************************************************************************/

package student;

import java.util.*;
import java.io.*;
/**
 * A class that parses the data file and builds an array of Songs objects.
 * 
 * @author bboothe
 */
public class SongCollection {
    /**
     *  an array of Song objects 
     */
    private Song[] songs;

    /**
     * @author Aaron Spencer
     * 
     * Song collection reads data from a file of the form:
     * ARIST="Boston"
     * TITLE="More than a Feeling"
     * LYRICS="I woke up this morning,
     * and the sun was gone...(etc)
     * "
     * The data is stored in array of Songs called songs
     *
     * @param filename The path and filename to the datafile that we are using.
     */
    
    public SongCollection(File filename) {

	// Catches exceptions FileNotFound and NoSuchElement
        try{
            // Songs are initially stored in an ArrayList<Song> before being
            // converted to an array. Data is read in and parsed by a Scanner.
            // We use three strings to temporarily store the data before 
            // creating the Songs.
            List<Song> songList;
            songList = new ArrayList<Song>();
            String artist;
            String title;
            String lyrics;
            Scanner sc = new Scanner(new BufferedReader(
                    new FileReader(filename)));
            //tokens are broken up by " because of the song file format
            sc.useDelimiter("\"");
            while (sc.hasNext()){
                // ensures the file being read is of the correct format. 
                // Otherwise an empty array is stored.
                if (!sc.next().equals("ARTIST=")){
                    System.out.println("Invalid file format");
                    songList.clear();
                    break;
                }
                // stores the data
                artist = sc.next();
                sc.nextLine();
                if (!sc.next().equals("TITLE=")){
                    System.out.println("Invalid file format");
                    songList.clear();
                    break;
                }
                title = sc.next();
                sc.nextLine();
                if (!sc.next().equals("LYRICS=")){
                    System.out.println("Invalid file format");
                    songList.clear();
                    break;
                }
                lyrics = sc.next();
                //creates the Song and adds it to the ArrayList
                songList.add(new Song(artist, title, lyrics));
                if (sc.hasNext()){
                    sc.nextLine();
                }
            }
            // converts to an array and sorts it by natural ordering
            songs = songList.toArray(new Song[songList.size()]);
            Arrays.sort(songs);
        }
        
        // Handles bad filenames. songs is assigned an empty array
        catch (FileNotFoundException e){
            this.songs = new Song[0];
            System.out.println("File Not Found");
        }
        
        // This exception only needs to be caught if the file is not in the
        // expected format. songs is assigned as empty
        catch (NoSuchElementException n){
            this.songs = new Song[0];
            System.out.println("Invalid file type");
        }
    }
    
    public SongCollection(String filename) {

	// Catches exceptions FileNotFound and NoSuchElement
        try{
            // Songs are initially stored in an ArrayList<Song> before being
            // converted to an array. Data is read in and parsed by a Scanner.
            // We use three strings to temporarily store the data before 
            // creating the Songs.
            List<Song> songList;
            songList = new ArrayList<Song>();
            String artist;
            String title;
            String lyrics;
            Scanner sc = new Scanner(new BufferedReader(
                    new FileReader(filename)));
            //tokens are broken up by " because of the song file format
            sc.useDelimiter("\"");
            while (sc.hasNext()){
                // ensures the file being read is of the correct format. 
                // Otherwise an empty array is stored.
                if (!sc.next().equals("ARTIST=")){
                    System.out.println("Invalid file format");
                    songList.clear();
                    break;
                }
                // stores the data
                artist = sc.next();
                sc.nextLine();
                if (!sc.next().equals("TITLE=")){
                    System.out.println("Invalid file format");
                    songList.clear();
                    break;
                }
                title = sc.next();
                sc.nextLine();
                if (!sc.next().equals("LYRICS=")){
                    System.out.println("Invalid file format");
                    songList.clear();
                    break;
                }
                lyrics = sc.next();
                //creates the Song and adds it to the ArrayList
                songList.add(new Song(artist, title, lyrics));
                if (sc.hasNext()){
                    sc.nextLine();
                }
            }
            // converts to an array and sorts it by natural ordering
            songs = songList.toArray(new Song[songList.size()]);
            Arrays.sort(songs);
        }
        
        // Handles bad filenames. songs is assigned an empty array
        catch (FileNotFoundException e){
            this.songs = new Song[0];
            System.out.println("File Not Found");
        }
        
        // This exception only needs to be caught if the file is not in the
        // expected format. songs is assigned as empty
        catch (NoSuchElementException n){
            this.songs = new Song[0];
            System.out.println("Invalid file type");
        }
    }
 
    /**
     * this is used as the data source for building other data structures
     * @return the songs array
     */
    public Song[] getAllSongs() {
        return songs;
    }
 
    /**
     * unit testing method Start by setting shortSongs.txt as the argument
     * in the Project Properties.  
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("usage: prog songfile");
            return;
        }
        SongCollection sc = new SongCollection(args[0]);
        //print first 10 songs
        System.out.println("Total songs = " + sc.getAllSongs().length + 
                ", first songs:");
        for (int i = 0; i < sc.getAllSongs().length && i < 10; i++){
            System.out.println(sc.getAllSongs()[i]);
        }
    }
}
