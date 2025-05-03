package dao;

import model.Song;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SongDAO {
    private static Connection conn;

    public SongDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertSong(Song song) {
        String sql = "INSERT INTO song (s_title, s_duration, s_albumId, s_genreId, s_artistId) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, song.getTitle());
            stmt.setLong(2, song.getDuration());
            stmt.setObject(3, song.getAlbumId(), java.sql.Types.INTEGER);
            stmt.setObject(4, song.getGenreId(), java.sql.Types.INTEGER);
            stmt.setInt(5, song.getArtistId());

            stmt.executeUpdate();
            System.out.println("Song inserted: " + song.getTitle());
        } catch (SQLException e) {
            System.err.println("Failed to insert song: " + song.getTitle());
            e.printStackTrace();
        }
    }

    public static void listAllSongs() {
        String sql = "SELECT s.s_songId, s.s_title, s.s_duration, a.al_name, ar.ar_name, g.g_name " +
                "FROM song s " +
                "LEFT JOIN album a ON s.s_albumId = a.al_albumId " +
                "LEFT JOIN artist ar ON s.s_artistId = ar.ar_artistId " +
                "LEFT JOIN genre g ON s.s_genreId = g.g_genreId";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-5s %-30s %-10s %-20s %-20s %-20s\n",
                    "ID", "Title", "Duration", "Album", "Artist", "Genre");

            while (rs.next()) {
                int id = rs.getInt("s_songId");
                String title = rs.getString("s_title");
                long duration = rs.getLong("s_duration");
                String album = rs.getString("al_name");
                String artist = rs.getString("ar_name");
                String genre = rs.getString("g_name");

                System.out.printf("%-5d %-30s %-10d %-20s %-20s %-20s\n",
                        id, title, duration, album, artist, genre);
            }

        } catch (SQLException e) {
            System.err.println("Error listing songs.");
            e.printStackTrace();
        }
    }

    public void listSongsByArtist() {
        String sql = "SELECT ar.ar_name, s.s_title FROM song s " +
                "JOIN artist ar ON s.s_artistId = ar.ar_artistId " +
                "ORDER BY ar.ar_name, s.s_title";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Songs grouped by artist:");
            String currentArtist = "";
            while (rs.next()) {
                String artist = rs.getString("ar_name");
                String title = rs.getString("s_title");

                if (!artist.equals(currentArtist)) {
                    currentArtist = artist;
                    System.out.println("\n🎤 " + artist);
                }

                System.out.println("   ♪ " + title);
            }
        } catch (SQLException e) {
            System.err.println("Error listing songs by artist.");
            e.printStackTrace();
        }
    }

    public void listSongsByAlbum() {
        String sql = "SELECT a.al_name, s.s_title FROM song s " +
                "LEFT JOIN album a ON s.s_albumId = a.al_albumId " +
                "ORDER BY a.al_name, s.s_title";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Songs grouped by album:");
            String currentAlbum = "";
            while (rs.next()) {
                String album = rs.getString("al_name");
                String title = rs.getString("s_title");

                if (!Objects.equals(album, currentAlbum)) {
                    currentAlbum = album;
                    System.out.println("\n💿 " + (album != null ? album : "Unknown Album"));
                }

                System.out.println("   ♪ " + title);
            }
        } catch (SQLException e) {
            System.err.println("Error listing songs by album.");
            e.printStackTrace();
        }
    }

    public void listSongsByGenre() {
        String sql = "SELECT g.g_name, s.s_title FROM song s " +
                "LEFT JOIN genre g ON s.s_genreId = g.g_genreId " +
                "ORDER BY g.g_name, s.s_title";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Songs grouped by genre:");
            String currentGenre = "";
            while (rs.next()) {
                String genre = rs.getString("g_name");
                String title = rs.getString("s_title");

                if (!Objects.equals(genre, currentGenre)) {
                    currentGenre = genre;
                    System.out.println("\n🎶 " + (genre != null ? genre : "Unknown Genre"));
                }

                System.out.println("   ♪ " + title);
            }
        } catch (SQLException e) {
            System.err.println("Error listing songs by genre.");
            e.printStackTrace();
        }
    }


}
