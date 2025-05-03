import dao.DBConnection;
import dao.SongDAO;
import dao.MusicImporter;

import java.sql.SQLException;
import java.util.*;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DBConnection.getConnection()) {
            System.out.println("Connected to the PostgreSQL database.");

            // Initialize SongDAO with connection
            SongDAO songDAO = new SongDAO(connection);

            System.out.println("🎵 Welcome to the Local Music Library 🎵");
            System.out.println("Type 'help' to view available commands.");

            // loop to get user input
            boolean running = true;
            while (running) {
                System.out.print("\n> ");
                String input = scanner.nextLine().trim();
                String[] parts = input.split(" ", 2);
                String command = parts[0].toLowerCase();

                switch (command) {
                    case "help":
                        printHelp();
                        break;

                    case "import":
                        if (parts.length < 2) {
                            System.out.println("Usage: import [folder_path]");
                        } else {
                             String path = parts[1];
                             MusicImporter.importFromDirectory(path, songDAO);
                        }
                        break;

                    case "list":
                        if (parts.length == 1) {
                            songDAO.listAllSongs();
                        } else {
                            String option = parts[1].toLowerCase();
                            switch (option) {
                                case "artist":
                                    songDAO.listSongsByArtist();
                                    break;
                                case "album":
                                    songDAO.listSongsByAlbum();
                                    break;
                                case "genre":
                                    songDAO.listSongsByGenre();
                                    break;
                                default:
                                    System.out.println("Invalid option for list. Try: list, list artist, list album, or list genre.");
                            }
                        }
                        break;

                    case "exit":
                        running = false;
                        System.out.println("Goodbye!");
                        break;

                    default:
                        System.out.println("Unknown command. Type 'help' for available commands.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  import [folder_path]   - Import music files from a folder");
        System.out.println("  list                   - List all songs in the database");
        System.out.println("  list artist            - List songs grouped by artist");
        System.out.println("  list album             - List songs grouped by album");
        System.out.println("  list genre             - List songs grouped by genre");
        System.out.println("  help                   - Show available commands");
        System.out.println("  exit                   - Exit the application");

    }
}
