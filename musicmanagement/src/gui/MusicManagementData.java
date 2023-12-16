package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import musicManagement.Artist;
import musicManagement.Genre;
import musicManagement.Playlist;
import musicManagement.Song;

//Model
public class MusicManagementData {
	
	private ArrayList<Song> songList = new ArrayList <Song>();
	private ArrayList<Artist> artistList = new ArrayList <Artist>();
	private ArrayList<Genre> genreList = new ArrayList <Genre>();
	private ArrayList<Playlist> playlistList = new ArrayList <Playlist>();
	private final String[] playlistCreteria = {"Artist", "Genre"};
	
	
	public String[] getPlaylistCreteria() {
		return playlistCreteria;
	}
	
	public ArrayList<Song> getSongList() {
		return songList;
	}
	
	public ArrayList<Artist> getArtistList() {
		return artistList;
	}
	
	public ArrayList<Genre> getGenreList() {
		return genreList;
	}
	
	public ArrayList<Playlist> getPlaylistList() {
		return playlistList;
	}
	
	public void addSong(Song song) {	
		songList.add(song);
	}
	
	public void addArtist(Artist artist) {	
		artistList.add(artist);	
	}
	
	public void addGenre(Genre genre) {
		genreList.add(genre);
	}
	
	public void addPlaylist(Playlist playlist) {
		playlistList.add(playlist);
	}

	public void delSong(Song song) {
		songList.remove(song);
		delArtist(song.getArtist());
		delGenre(song.getGenre());
		
		for(Playlist p: playlistList) {
			p.deleteSongInSongList(song);
		}
		
		playlistList.removeIf(e -> e.getSongList().isEmpty());
	}
	
	public void delArtist(Artist artist) {
		boolean artistHasAnotherSong = false;
		
		for(Song s: songList) {
			if(artist.equals(s.getArtist())) {
				artistHasAnotherSong = true;
			}
		}
		
		if(!artistHasAnotherSong) {
			artistList.remove(artist);
		}
	}
	
	public void delGenre(Genre genre) {
		boolean genreHasAnotherSong = false;
		
		for(Song s: songList) {
			if(genre.equals(s.getGenre())) {
				genreHasAnotherSong = true;
			}
		}
		
		if(!genreHasAnotherSong) {
			genreList.remove(genre);
		}			
	}
	
	public void delPlaylist(Playlist playlist) {
		playlistList.remove(playlist);
	}
	
	public Artist getArtist(String artist) {
		Artist knownArtist = null;
		for(Artist a: artistList) {
			if(artist.equalsIgnoreCase(a.getName())) {
				knownArtist = a;
			}
		}
		return knownArtist;
	}
	
	public Genre getGenre(String genre) {
		Genre knownGenre = null;
		for(Genre g: genreList) {
			if(genre.equalsIgnoreCase(g.getName())) {
				knownGenre = g;
			}
		}
		return knownGenre;
	}
	
	public int checkIfSongArtistGenreExists(String titel, String artist, String genre) {
		int exists = 0;
		boolean songEx = false;
		boolean artistEx = false;
		boolean genreEx = false;
		
		for(Song s: songList) {
			if(titel.equalsIgnoreCase(s.getTitle())) {
				songEx = true;
			}
		}
		for(Artist a: artistList) {
			if(artist.equalsIgnoreCase(a.getName())) {
				artistEx = true;
			}
		}
		for(Genre g: genreList) {
			if(genre.equalsIgnoreCase(g.getName())) {
				genreEx = true;
			}
		}
		if((songEx && !artistEx && !genreEx) || (songEx && artistEx && !genreEx) || songEx && !artistEx &&genreEx ) {
			exists = 1;
		}
		    if(!songEx && artistEx && !genreEx) {
			exists = 2;
		}
		if(!songEx && artistEx && genreEx) {
			exists = 3;
		}
		if(!songEx && !artistEx && genreEx) {
			exists = 4;
		}
		if(!songEx && !artistEx && !genreEx) {
			exists = 5;
		}
		
		return exists;
	}
	
	public boolean checkIfArtistExists(String artist) {
		boolean exists = false;
		
		for (Artist a: artistList) {
			if(artist.equalsIgnoreCase(a.getName())) {
				exists = true;
			}
		}
		return exists;
	}
	
	private boolean checkIfGenreExists(String genre) {
		boolean exists = false;
		
		for (Genre g: genreList) {
			if(genre.equalsIgnoreCase(g.getName())) {
				exists = true;
			}
		}
		return exists;
	}
	
	public boolean checkIfPlaylistExists(String playlist) {
		boolean exists = false;
		
		for (Playlist p: playlistList) {
			if(playlist.equalsIgnoreCase(p.getName())) {
				exists = true;
			}
		}
		return exists;
	}
	
	public void storeSongs() {
		try {
			BufferedWriter writer = new BufferedWriter (new FileWriter("./data/Songs.txt"));
			for (Song s: songList) {
				writer.write(s.getTitle()+"\n");
				writer.write(s.getArtist().getName()+"\n");
				writer.write(s.getGenre().getName()+"\n");
				writer.write(s.getFilePath()+"\n");
			}
			writer.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readSongs() {
		try {
			BufferedReader reader = new BufferedReader (new FileReader("./data/Songs.txt"));
			String currentlyRead;
			String songTitle = "";
			String songArtist = "";
			String songGenre = "";
			String songFilePath = "";
			int i = 0;
			while ((currentlyRead = reader.readLine()) != null)  {
				switch(i) {
				case 0:
					songTitle = currentlyRead;
					i++;
					break;
				case 1:
					songArtist = currentlyRead;
					i++;
					break;
				case 2:
					songGenre = currentlyRead;
					i++;
					break;
				case 3:
					songFilePath = currentlyRead;
					Song song = new Song(songTitle, songArtist, songGenre, songFilePath);
					addSong(song);
					if(!checkIfArtistExists(song.getArtist().getName())) {
						addArtist(song.getArtist());
					}
					if(!checkIfGenreExists(song.getGenre().getName())) {
						addGenre(song.getGenre());
					}
					i = 0;
					break;
				}
			}
			reader.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void storePlaylists() {
		try {
			BufferedWriter writer = new BufferedWriter (new FileWriter("./data/Playlists.txt"));
			for (Playlist p: playlistList) {
				writer.write(p.getName()+"\n");
				writer.write(p.getArtist()+"\n");
				writer.write(p.getGenre()+"\n");
			}
			writer.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readPlaylists() {
		try {
			BufferedReader reader = new BufferedReader (new FileReader("./data/Playlists.txt"));
			String currentlyRead;
			String playlistName = "";
			String playlistArtist = "";
			String playlistGenre = "";			
			int i = 0;
			while ((currentlyRead = reader.readLine()) != null)  {
				switch(i) {
				case 0:
					playlistName = currentlyRead;
					i++;
					break;
				case 1:
					playlistArtist = currentlyRead;
					i++;
					break;
				case 2:
					playlistGenre = currentlyRead;
					if(playlistArtist.equals("null")) {
						Playlist playlist = new Playlist(playlistName, getGenre(playlistGenre));
						addPlaylist(playlist);
					}
					if(playlistGenre.equals("null")) {
						Playlist playlist = new Playlist(playlistName, getArtist(playlistArtist));
						addPlaylist(playlist);
					}
					i = 0;
					break;
				}
			}
			reader.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Song> sortSongs() {
		
		songList.sort((o1, o2)-> o1.getTitle().compareTo(o2.getTitle()));
		
		return songList;
	}


}
