package model;

public class Song {
    private String title;
    private long duration;
    private Integer albumId;
    private Integer genreId;
    private int artistId;

    public Song(String title, long duration, Integer albumId, Integer genreId, int artistId) {
        this.title = title;
        this.duration = duration;
        this.albumId = albumId;
        this.genreId = genreId;
        this.artistId = artistId;
    }

    public String getTitle() { return title; }
    public long getDuration() { return duration; }
    public Integer getAlbumId() { return albumId; }
    public Integer getGenreId() { return genreId; }
    public int getArtistId() { return artistId; }
}
