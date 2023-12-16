package musicManagement;

import java.util.ArrayList;
import java.util.UUID;

public class Genre {

	private String name;
	private ArrayList<Song> songs = new ArrayList<Song>();
	private ArrayList<Artist> artists = new ArrayList<Artist>();
	
	Genre() {
	}
	
	Genre(String name, Song song, Artist artist) {
		this.name = name;
		songs.add(song);
		artists.add(artist);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Song> getSongs() {
		return songs;
	}

	public void setSongs(ArrayList<Song> songs) {
		this.songs = songs;
	}
	
	public void addSong (Song song) {
			songs.add(song);
	}
	
	public void addArtist(Artist artist) {
		try {
			artists.add(artist);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return name;
	}

}
