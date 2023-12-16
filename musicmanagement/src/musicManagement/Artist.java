package musicManagement;

import java.util.ArrayList;
import java.util.UUID;


public class Artist {

	private String name;
	private ArrayList<Song> songs = new ArrayList<Song>();

	Artist(){
	}
	
	Artist(String name, Song song){
		this.name = name;
		songs.add(song);
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


	public void addSong(Song song) {
		try {
			songs.add(song);
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
