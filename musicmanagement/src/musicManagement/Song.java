package musicManagement;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
  
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Song {

	private String title;
	private Artist artist;
	private Genre genre;
	
	//Variables for Audio
	private Long currentFrame;
	private Clip clip;
	      
	// current status of clip
	private String status;
	      
	private AudioInputStream audioInputStream;
	private String filePath;
	
	//Help from: https://www.geeksforgeeks.org/play-audio-file-using-java/
	//constructors to initialize streams and clip
	//no Artist and Genre exist - everything has to be created
    public Song(String title, String artistName, String genreName, String filePath)
        throws UnsupportedAudioFileException,IOException, LineUnavailableException 
    {
    	this.filePath = filePath;
    	this.title = title;
    	
    	this.artist = new Artist(artistName, this);
    	this.genre = new Genre(genreName, this, this.artist);
    	
        // create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
          
        // create clip reference
        clip = AudioSystem.getClip();
          
        // open audioInputStream to the clip
        clip.open(audioInputStream);
    }
    
    //Artist exists
    public Song(String title, Artist artist, String genreName, String filePath)
            throws UnsupportedAudioFileException,IOException, LineUnavailableException 
        {
        	this.filePath = filePath;
        	this.title = title;
        	this.artist = artist;
        	
        	this.genre = new Genre(genreName, this, this.artist);
        	
        	artist.addSong(this);
        	
            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

            clip = AudioSystem.getClip();

            clip.open(audioInputStream);
        }
    
    //Artist and Genre exist
    public Song(String title, Artist artist, Genre genre, String filePath)
            throws UnsupportedAudioFileException,IOException, LineUnavailableException 
        {
        	this.filePath = filePath;
        	this.title = title;
        	this.artist = artist;
        	this.genre = genre;
        	
        	artist.addSong(this);
        	genre.addSong(this);

            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

            clip = AudioSystem.getClip();
     
            clip.open(audioInputStream);
        }
    
    //Genre exists
    public Song(String title, String artistName, Genre genre, String filePath)
            throws UnsupportedAudioFileException,IOException, LineUnavailableException 
        {
        	this.filePath = filePath;
        	this.title = title;
        	this.genre = genre;
        	
        	this.artist = new Artist(artistName, this);
        	
        	genre.addSong(this);

            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

            clip = AudioSystem.getClip();
   
            clip.open(audioInputStream);
        }

	public String getFilePath() {
		return filePath;
	}

	public Long getCurrentFrame() {
		return currentFrame;
	}

	public String getTitle() {
		return title;
	}
	
	public Artist getArtist() {
		return artist;
	}

	public Genre getGenre() {
		return genre;
	}
	
	 public String getStatus() {
			return status;
		}

	public void setStatus(String status) {
		this.status = status;
	}

	
	@Override
	public String toString() {
		String songString = title + " - " + artist.toString() + " - " + genre.toString();
		return songString;
	}
	  
	public void play() 
    {
        clip.start();
          
        status = "play";
    }
      
    public void pause() 
    {
        this.currentFrame = this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }
     
    public void resume() 
    		throws UnsupportedAudioFileException, IOException, LineUnavailableException 
    {
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }

	//Clip has to be stopped and closed before it can be played again
    public void restart() 
    		throws IOException, LineUnavailableException,UnsupportedAudioFileException 
    {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }
      
    public void stop() 
    		throws UnsupportedAudioFileException,IOException, LineUnavailableException 
    {
        currentFrame = 0L;
        clip.stop();
        clip.close();
        resetAudioStream();
    }
     
    //Clip jumps 10 seconds forwards
    public void jumpTenSecForward() 
    		throws UnsupportedAudioFileException, IOException,LineUnavailableException 
    {
        	this.currentFrame = this.clip.getMicrosecondPosition();
        	if (currentFrame < clip.getMicrosecondLength()) {
        		clip.stop();
        		clip.close();
        		resetAudioStream();
            	clip.setMicrosecondPosition(currentFrame+10000000);
                this.play();
            }
    }
    
    //Clip jumps 10 seconds backwards
    public void jumpTenSecBackward() 
    		throws UnsupportedAudioFileException, IOException,LineUnavailableException 
    {
        	this.currentFrame = this.clip.getMicrosecondPosition();
        	if (currentFrame > 10000000) {
        		clip.stop();
        		clip.close();
        		resetAudioStream();
            	clip.setMicrosecondPosition(currentFrame-10000000);
                this.play();
            }
    }
      
    public void resetAudioStream() 
    		throws UnsupportedAudioFileException, IOException,LineUnavailableException 
    {
        audioInputStream = AudioSystem.getAudioInputStream(
        new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
    }	    
	
}
