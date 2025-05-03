package dao;

import model.Song;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import com.mpatric.mp3agic.Mp3File;

public class MusicImporter {
    public static void importFromDirectory(String directoryPath, SongDAO songDAO) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid directory path.");
            return;
        }

        // Get all files in the directory and subdirectories
        List<File> musicFiles = getMusicFiles(directory);
        if (musicFiles.isEmpty()) {
            System.out.println("No music files found in the directory.");
            return;
        }

        // Process each music file
        for (File musicFile : musicFiles) {
            try {
                // Extract metadata from MP3
                Mp3File mp3File = new Mp3File(musicFile);
                String title = mp3File.getId3v2Tag() != null ? mp3File.getId3v2Tag().getTitle() : "Unknown Title";
                String album = mp3File.getId3v2Tag() != null ? mp3File.getId3v2Tag().getAlbum() : "Unknown Album";
                String artist = mp3File.getId3v2Tag() != null ? mp3File.getId3v2Tag().getArtist() : "Unknown Artist";
                String genre = mp3File.getId3v2Tag() != null ? mp3File.getId3v2Tag().getGenreDescription() : "Unknown Genre";
                long duration = mp3File.getLengthInMilliseconds() / 1000; // Convert to seconds

                int artistId = getArtistId(artist);
                int albumId = getAlbumId(album);
                int genreId = getGenreId(genre);

                // Create a Song object
                Song song = new Song(title, duration, albumId, genreId, artistId);

                // Insert the song into the database
                songDAO.insertSong(song);

                System.out.println("Imported: " + title + " by " + artist);
            } catch (Exception e) {
                System.err.println("Failed to import file: " + musicFile.getName());
                e.printStackTrace();
            }
        }
    }

    private static List<File> getMusicFiles(File directory) {
        List<File> musicFiles = new ArrayList<>();
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    musicFiles.addAll(getMusicFiles(file)); // Recurse into subdirectories
                } else {
                    musicFiles.add(file);
                }
            }
        }
        return musicFiles;
    }

    private static int getArtistId(String artist) {
        return 1; // Placeholder
    }

    private static int getAlbumId(String album) {
        return 1; // Placeholder
    }

    private static int getGenreId(String genre) {
        return 1; // Placeholder
    }
}
