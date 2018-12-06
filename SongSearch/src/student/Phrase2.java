/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

/**
 *
 * @author aaron
 */
public class Phrase2 {
    public static int rankPhrase(String lyrics, String search){
        
        String[] lyricArray = lyrics.toLowerCase().split("\\s");
        String[] searchArray = search.toLowerCase().split("\\s");
        int rank = lyrics.length();
        boolean match = false;
        int currentIndex;
        for (int i = 0; i < lyricArray.length; i++){
            if (lyricArray.equals(searchArray[0])){
                currentIndex = i;
                for (int j = 1; j < searchArray.length; j++){
                    match = false;
                    for (int k = currentIndex; k < lyricArray.length; k++){
                        if (lyricArray[k].equals(searchArray[j])){
                            currentIndex = k;
                            match = true;
                            if (j == searchArray.length - 1){
                                int len = 0;
                                for (int l = i; l <= k; l++){
                                    len += lyricArray.length;
                                }
                                if (rank > len){
                                    rank = len;
                                }
                            }
                            break;
                        }
                    }
                    
                }
            }
        }
        if (rank == lyrics.length()){
            return 0;
        }
        return rank;
    }
    public static void main(String args[]){
        SongCollection sc = new SongCollection("../allSongs.txt");
        Song[] songArray = sc.getAllSongs();
        for(Song song : songArray){
            int rank = Phrase2.rankPhrase(song.getLyrics(), "she loves you");
            if (rank > 0){
                System.out.println(song.toString()+rank);
            }
        }
    }
    
}
