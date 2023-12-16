package musicManagement;

import java.util.ArrayList;

public class Playlist {
	
	private String name;
	private String creteria;
	private ArrayList<Song> songList;
	private Artist artist;
	private Genre genre;
	
	public Playlist(){}
	
	public Playlist(String name, Artist artist) {
		this.name = name;
		this.creteria  = "Artist";
		this.artist = artist;
		this.genre = null;
		songList = artist.getSongs();
		
	}
	
	public Playlist(String name, Genre genre) {
		this.name = name;
		this.creteria  = "Genre";
		this.artist = null;
		this.genre = genre;
		songList = genre.getSongs();
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Song> getSongList() {
		return songList;
	}
	
	public String getCreteria() {
		return creteria;
	}

	public Artist getArtist() {
		return artist;
	}

	public Genre getGenre() {
		return genre;
	}
	
	public void deleteSongInSongList(Song song) {
		songList.remove(song);
	}
	
	@Override
	public String toString() {
		String outputString;
		if(this.genre == null) {
			outputString =  name + " - " + creteria + " - " + artist ;
		}
		else {
			outputString = name + " - " + creteria + " - " + genre;
		}
		return outputString;
	}

}
