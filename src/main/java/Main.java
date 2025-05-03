import dao.DBConnection;

import java.sql.SQLException;
import java.util.*;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Connect to database
        try (var connection =  DBConnection.getConnection()){
            System.out.println("Connected to the PostgreSQL database.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("🎵 Welcome to the Local Music Library 🎵");
        System.out.println("Type 'help' to view available commands.");

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
                        //String path = parts[1];
                        //MusicImporter.importFromDirectory(path, songDAO);
                    }
                    break;

                case "list":
                    //songDAO.listAllSongs();
                    break;

                case "exit":
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Unknown command. Type 'help' for available commands.");
            }
        }
    }

    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  import [folder_path]   - Import music files from a folder");
        System.out.println("  list                   - List all songs in the database");
        System.out.println("  help                   - Show available commands");
        System.out.println("  exit                   - Exit the application");
    }
}
