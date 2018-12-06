/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;
import java.io.File;
import javax.swing.*;

/**
 *
 * @author aaron
 */
public class SongSearch extends javax.swing.JFrame {
    SongCollection sc = null;
    SearchByArtistPrefix sbap = null;
    SearchByTitlePrefix sbtp = null;
    SearchByLyricsWords sblw = null;
    SearchByLyricsPhrase sblp = null;
    class MyCustomFilter extends javax.swing.filechooser.FileFilter {
            @Override
            public boolean accept(File file) {
                // Allow only directories, or files with ".txt" extension
                return file.isDirectory() || file.getAbsolutePath().endsWith(".txt");
            }
            @Override
            public String getDescription() {
                // This description will be displayed in the dialog,
                // hard-coded = ugly, should be done via I18N
                return "Text documents (*.txt)";
            }
        } 
    /**
     * Creates new form SongSearch
     */
    public SongSearch() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        searchGroup = new javax.swing.ButtonGroup();
        browseButton = new javax.swing.JButton();
        BrowseLabel = new javax.swing.JLabel();
        searchText = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lyricsOutput = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        songList = new javax.swing.JList<>();
        numSongs = new javax.swing.JLabel();
        lyricsWordsRadioButton = new javax.swing.JRadioButton();
        lyricsPhraseRadioButton = new javax.swing.JRadioButton();
        artistRadioButton = new javax.swing.JRadioButton();
        titleRadioButton = new javax.swing.JRadioButton();
        songFile1 = new javax.swing.JLabel();
        songFile2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        Open = new javax.swing.JMenuItem();
        Exit = new javax.swing.JMenuItem();

        fileChooser.setDialogTitle("Select a Song File");
        fileChooser.setFileFilter(new MyCustomFilter());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Song Search");

        browseButton.setText("Browse");
        browseButton.setEnabled(false);
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        BrowseLabel.setText("Browse all songs:");

        searchText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextActionPerformed(evt);
            }
        });

        searchButton.setText("Search");
        searchButton.setEnabled(false);
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        lyricsOutput.setColumns(20);
        lyricsOutput.setRows(5);
        jScrollPane2.setViewportView(lyricsOutput);

        songList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        songList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                songListValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(songList);

        numSongs.setText("No songs found");

        searchGroup.add(lyricsWordsRadioButton);
        lyricsWordsRadioButton.setText("Search by Lyrics Words");

        searchGroup.add(lyricsPhraseRadioButton);
        lyricsPhraseRadioButton.setText("Search by Lyrics Phrase");

        searchGroup.add(artistRadioButton);
        artistRadioButton.setSelected(true);
        artistRadioButton.setText("Search by Artist Prefix");

        searchGroup.add(titleRadioButton);
        titleRadioButton.setText("Search by Title Prefix");
        titleRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleRadioButtonActionPerformed(evt);
            }
        });

        songFile1.setText("Current song file:");

        songFile2.setText("None");

        jMenu1.setText("File");

        Open.setText("Open Song File");
        Open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenActionPerformed(evt);
            }
        });
        jMenu1.add(Open);

        Exit.setText("Exit");
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });
        jMenu1.add(Exit);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(numSongs, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(searchText)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(artistRadioButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(lyricsWordsRadioButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(BrowseLabel)
                                .addGap(27, 27, 27)
                                .addComponent(browseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(titleRadioButton)
                                        .addGap(24, 24, 24)
                                        .addComponent(lyricsPhraseRadioButton))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(songFile1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(songFile2)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(songFile1)
                            .addComponent(songFile2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(browseButton)
                            .addComponent(BrowseLabel))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(artistRadioButton)
                            .addComponent(lyricsWordsRadioButton))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(titleRadioButton)
                            .addComponent(lyricsPhraseRadioButton))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchButton)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(numSongs, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenActionPerformed
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            sc = new SongCollection(file);
            sbap = new SearchByArtistPrefix(sc);
            sbtp = new SearchByTitlePrefix(sc);
            sblw = new SearchByLyricsWords(sc);
            sblp = new SearchByLyricsPhrase(sc);
            browseButton.setEnabled(true);
            artistRadioButton.setEnabled(true);
            searchButton.setEnabled(true);
            songFile2.setText(file.getName());
        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_OpenActionPerformed

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_ExitActionPerformed

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        DefaultListModel<Song> listModel = new DefaultListModel<>();
        Song[] songs = sc.getAllSongs();
        for (Song song : songs){
            listModel.addElement(song);
        }
        if (songs.length == 0){
            numSongs.setText("No songs found");
        }
        else{
            numSongs.setText(songs.length+" songs found");
        }
        songList.setModel(listModel);
    }//GEN-LAST:event_browseButtonActionPerformed

    private void searchTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextActionPerformed
        if (searchButton.isEnabled()){
            searchButtonActionPerformed(evt);
        }
    }//GEN-LAST:event_searchTextActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        Song[] songs;
        String search = searchText.getText();
        if (artistRadioButton.isSelected()){
            songs = sbap.search(search);
        }
        else if (titleRadioButton.isSelected()){
            songs = sbtp.search(search);
        }
        else if (lyricsWordsRadioButton.isSelected()){
            songs = sblw.search(search);
        }
        else{
            songs = sblp.search(search);
        }
        if (songs.length == 0){
            numSongs.setText("No songs found");
        }
        else{
            numSongs.setText(songs.length+" songs found");
        }
        DefaultListModel<Song> listModel = new DefaultListModel<>();
        for (Song song : songs){
            listModel.addElement(song);
        }
        songList.setModel(listModel);                             
    }//GEN-LAST:event_searchButtonActionPerformed

    private void songListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_songListValueChanged
        if(!evt.getValueIsAdjusting()) {
            if (songList.getSelectedValue() == null){
                lyricsOutput.setText("");
            }
            else{
                final String lyrics = songList.getSelectedValue().getLyrics();
                lyricsOutput.setText(lyrics);
                lyricsOutput.setCaretPosition(0);
            }
        }
    }//GEN-LAST:event_songListValueChanged

    private void titleRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_titleRadioButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SongSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SongSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SongSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SongSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SongSearch().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BrowseLabel;
    private javax.swing.JMenuItem Exit;
    private javax.swing.JMenuItem Open;
    private javax.swing.JRadioButton artistRadioButton;
    private javax.swing.JButton browseButton;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea lyricsOutput;
    private javax.swing.JRadioButton lyricsPhraseRadioButton;
    private javax.swing.JRadioButton lyricsWordsRadioButton;
    private javax.swing.JLabel numSongs;
    private javax.swing.JButton searchButton;
    private javax.swing.ButtonGroup searchGroup;
    private javax.swing.JTextField searchText;
    private javax.swing.JLabel songFile1;
    private javax.swing.JLabel songFile2;
    private javax.swing.JList<Song> songList;
    private javax.swing.JRadioButton titleRadioButton;
    // End of variables declaration//GEN-END:variables
}
