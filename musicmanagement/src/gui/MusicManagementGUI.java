package gui;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ListSelectionModel;
import javax.swing.JSplitPane;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.Dimension;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;

import musicManagement.Artist;
import musicManagement.Genre;
import musicManagement.Playlist;
import musicManagement.Song;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
//View
public class MusicManagementGUI extends JFrame {

	private MusicManagementData data;
	
	//initialize components
	private CardLayout cardLayout;
	private JSplitPane splitPane;
	private JLabel lblSongs;
	private JLabel lblPlaylists;
	private JLabel lblSongsInPlaylist;
	private JLabel lblSongGenre;
	private JLabel lblSongArtist;
	private JLabel lblSongTitle;
	private JPanel contentPane;
	private JLabel lblSearchIcon;
	private JPanel modePanels;
	private JPanel modeSelection;
	private JPanel userModePanel;
	private JPanel managementModePanel;
	private JButton btnManagementMode;
	private JButton btnUserMode;
	private JButton btnStartSong;
	private JButton btnPauseResumeSong;
	private JButton btnRestartSong;
	private JButton btnStopSong;
	private JButton btnSkipBack;
	private JButton btnSkipForward;
	private JButton btnCreatePlaylist;
	private JButton btnDelPlaylist;
	private JButton btnAddSong;
	private JButton btnDeleteSong;
	private JButton btnSortSong;
	private JTextField searchField;
	private JScrollPane scrollPaneSongs;
	private JScrollPane scrollPanePlaylists;
	private JScrollPane scrollPaneSongsPlaylist;
	private JScrollPane musicListScrollPane;
	private JList<Song> musicList;
	private JList<Song> songList;
	private JList<Playlist> playlistList;
	private JList<Song> songsInPlaylistList;
	private DefaultListModel<Song> musicListModel;
	private DefaultListModel<Playlist> playlistModel;
	private DefaultListModel<Song> playlistSongsModel;
	private JComboBox playListCreteriaBox;
	private JComboBox artistSelectionBox;
	private JComboBox genreSelectionBox;
	private Song song;
	private Song currentlyPlayingSong;
	
	public MusicManagementGUI() {}


	public MusicManagementGUI(MusicManagementData data) {
		//init MusicManagementData
		this.data = data;
		
		
		//initialize ListModels
		musicListModel = new DefaultListModel<>();
		playlistModel = new DefaultListModel<>();
		playlistSongsModel = new DefaultListModel<>(); 
		
		//Song-libary initialization
		data.readSongs();
		data.readPlaylists();
		System.out.println("Songs loaded:" + data.getSongList());
		System.out.println("Artists loaded:" + data.getArtistList());
		System.out.println("Genre loaded:" + data.getGenreList());
		System.out.println("Playlists loaded:" + data.getPlaylistList());
		musicListModel.addAll(data.getSongList());
		playlistModel.addAll(data.getPlaylistList());
		
		//create custom Font
		Font poppinsRegular15 = createCustomFont("fonts\\poppins-Regular.ttf", 15f);
		Font poppinsRegular9 = createCustomFont("fonts\\poppins-Regular.ttf", 9f);
		
		
		//create Frame
		setTitle("SaMaMui-Administration");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 931, 650);
		
		//create contentPane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//split content Pane
		splitPane = new JSplitPane();
		splitPane.setBounds(0, 0, 917, 613);
		splitPane.setEnabled(false);
		splitPane.setDividerSize(1);
		contentPane.add(splitPane);
		
		modePanels = new JPanel();
		splitPane.setRightComponent(modePanels);
		modePanels.setLayout(new CardLayout(0, 0));
		
		modeSelection = new JPanel();
		modeSelection.setPreferredSize(new Dimension(200, 10));
		modeSelection.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		modeSelection.setBackground(new Color(99,69,138));
		splitPane.setLeftComponent(modeSelection);
		
		btnManagementMode = new JButton("Manage Songs");
		btnManagementMode.setForeground(Color.BLACK);
		btnManagementMode.setIcon(new ImageIcon(".\\icons\\management.png"));
		btnManagementMode.setBorderPainted(false);
		btnManagementMode.setFocusPainted(false);
		btnManagementMode.setBackground(new Color(204, 153, 255));
		btnManagementMode.setFont(new Font("Century", Font.PLAIN, 17));
		btnManagementMode.setBounds(0, 228, 200, 65);
		btnManagementMode.setBorder(null);
		modeSelection.setLayout(null);
		modeSelection.add(btnManagementMode);
		
		btnUserMode = new JButton("Listen");
		btnUserMode.setForeground(Color.BLACK);
		btnUserMode.setIcon(new ImageIcon(".\\icons\\headphones.png"));
		btnUserMode.setFocusPainted(false);
		btnUserMode.setBackground(new Color(204, 153, 255));
		btnUserMode.setFont(new Font("Century", Font.PLAIN, 17));
		btnUserMode.setBorder(null);
		btnUserMode.setBounds(0, 303, 200, 65);
		modeSelection.add(btnUserMode);
		
		cardLayout = (CardLayout)(modePanels.getLayout());
	
		managementModePanel = new JPanel();
		managementModePanel.setBackground(new Color(126,90,155));
		modePanels.add(managementModePanel, "managementModePanel");
		managementModePanel.setLayout(null);
		
		userModePanel = new JPanel();
		userModePanel.setBackground(new Color(126,90,155));
		modePanels.add(userModePanel, "userModePanel");
		userModePanel.setLayout(null);
		
		//UI for User Mode
		btnStartSong = new JButton("");
		btnStartSong.setBorder(null);
		btnStartSong.setBackground(new Color(126,90,155));
		btnStartSong.setBounds(170, 240, 20, 21);
		btnStartSong.setIcon(new ImageIcon(".\\icons\\play-button 20x20.png"));
		userModePanel.add(btnStartSong);
		
		btnPauseResumeSong = new JButton("");
		btnPauseResumeSong.setBackground(new Color(216, 191, 216));
		btnPauseResumeSong.setBounds(344, 51, 58, 49);
		btnPauseResumeSong.setIcon(new ImageIcon(".\\icons\\playPause.png"));
		userModePanel.add(btnPauseResumeSong);
		
		btnRestartSong = new JButton("");
		btnRestartSong.setBackground(new Color(216, 191, 216));
		btnRestartSong.setBorder(null);
		btnRestartSong.setIcon(new ImageIcon(".\\icons\\refresh.png"));
		btnRestartSong.setBounds(552, 51, 58, 49);
		userModePanel.add(btnRestartSong);
		
		btnStopSong = new JButton("");
		btnStopSong.setBackground(new Color(216, 191, 216));
		btnStopSong.setBorder(null);
		btnStopSong.setIcon(new ImageIcon(".\\icons\\stop-button.png"));
		btnStopSong.setBounds(132, 51, 58, 49);
		userModePanel.add(btnStopSong);
		
		btnSkipBack = new JButton("");
		btnSkipBack.setBackground(new Color(216, 191, 216));
		btnSkipBack.setBorder(null);
		btnSkipBack.setIcon(new ImageIcon(".\\icons\\back10sec.png"));
		btnSkipBack.setBounds(229, 51, 45, 49);
		userModePanel.add(btnSkipBack);
		
		btnSkipForward = new JButton("");
		btnSkipForward.setBackground(new Color(216, 191, 216));
		btnSkipForward.setBorder(null);
		btnSkipForward.setIcon(new ImageIcon(".\\icons\\for10sec.png"));
		btnSkipForward.setBounds(462, 51, 45, 49);
		userModePanel.add(btnSkipForward);
		
		btnCreatePlaylist = new JButton("");
		btnCreatePlaylist.setBorder(null);
		btnCreatePlaylist.setIcon(new ImageIcon(".\\icons\\playlist 20x20.png"));
		btnCreatePlaylist.setBackground(new Color(126,90,155));
		btnCreatePlaylist.setFocusPainted(false);
		btnCreatePlaylist.setBounds(407, 240, 20, 21);
		userModePanel.add(btnCreatePlaylist);
		
		btnDelPlaylist = new JButton("");
		btnDelPlaylist.setBorder(null);
		btnDelPlaylist.setIcon(new ImageIcon(".\\icons\\bin 20x20.png"));
		btnDelPlaylist.setBackground(new Color(126,90,155));
		btnDelPlaylist.setBounds(377, 240, 20, 21);
		userModePanel.add(btnDelPlaylist);
		
		songList = new JList<>(musicListModel);
		songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		songList.setBackground(new Color(99,69,138));
		songList.setSelectionBackground(new Color(178, 136, 192));
		songList.setSelectionForeground(new Color(255,255,255));
		songList.setForeground(Color.BLACK);
		songList.setFont(poppinsRegular9);
		
		playlistList = new JList<>(playlistModel); 
		playlistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playlistList.setBackground(new Color(99,69,138));
		playlistList.setSelectionBackground(new Color(178, 136, 192));
		playlistList.setSelectionForeground(new Color(255,255,255));
		playlistList.setForeground(Color.BLACK);
		playlistList.setFont(poppinsRegular9);
		
		songsInPlaylistList = new JList<>(playlistSongsModel); 
		songsInPlaylistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		songsInPlaylistList.setBackground(new Color(99,69,138));
		songsInPlaylistList.setSelectionBackground(new Color(178, 136, 192));
		songsInPlaylistList.setSelectionForeground(new Color(255,255,255));
		songsInPlaylistList.setForeground(Color.BLACK);
		songsInPlaylistList.setFont(poppinsRegular9);
		
		scrollPaneSongs = new JScrollPane(songList);
		scrollPaneSongs.setBorder(null);
		scrollPaneSongs.setBackground(new Color(99,69,138));
		scrollPaneSongs.setBounds(51, 271, 151, 277);
		userModePanel.add(scrollPaneSongs);
		
		scrollPanePlaylists = new JScrollPane(playlistList);
		scrollPanePlaylists.setBorder(null);
		scrollPanePlaylists.setBackground(new Color(99,69,138));
		scrollPanePlaylists.setBounds(288, 271, 151, 277);
		userModePanel.add(scrollPanePlaylists);
		
		scrollPaneSongsPlaylist = new JScrollPane(songsInPlaylistList);
		scrollPaneSongsPlaylist.setBorder(null);
		scrollPaneSongsPlaylist.setBackground(new Color(99,69,138));
		scrollPaneSongsPlaylist.setBounds(525, 271, 151, 277);
		userModePanel.add(scrollPaneSongsPlaylist);
		
		lblSongs = new JLabel("Songs");
		lblSongs.setBounds(51, 248, 45, 13);
		userModePanel.add(lblSongs);
		
		lblPlaylists = new JLabel("Playlists");
		lblPlaylists.setBounds(288, 248, 58, 13);
		userModePanel.add(lblPlaylists);
		
		lblSongsInPlaylist = new JLabel("Songs in Playlist");
		lblSongsInPlaylist.setBounds(525, 248, 151, 13);
		userModePanel.add(lblSongsInPlaylist);
		
		
		//UI for Management Mode
		lblSongGenre = new JLabel("Genre");
		lblSongGenre.setBounds(574, 574, 45, 13);
		managementModePanel.add(lblSongGenre);
		
		lblSongArtist = new JLabel("Artist");
		lblSongArtist.setBounds(348, 574, 45, 13);
		managementModePanel.add(lblSongArtist);
		
		lblSongTitle = new JLabel("Title");
		lblSongTitle.setBounds(101, 574, 45, 13);
		managementModePanel.add(lblSongTitle);
		
		searchField = new JTextField();
		searchField.setBorder(null);
		searchField.setBounds(131, 92, 489, 21);
		searchField.setBackground(new Color(99,69,138));
		managementModePanel.add(searchField);
		searchField.setColumns(10);
		
		lblSearchIcon = new JLabel("");
		lblSearchIcon.setIcon(new ImageIcon(".\\icons\\loupe.png"));
		lblSearchIcon.setBounds(97, 85, 49, 39);
		managementModePanel.add(lblSearchIcon);
		
		btnAddSong = new JButton("   Add song");
		btnAddSong.setBorder(null);
		btnAddSong.setBackground(new Color(216, 191, 216));
		btnAddSong.setIcon(new ImageIcon(".\\icons\\plus 20x20.png"));
		btnAddSong.setBounds(97, 33, 140, 25);
		managementModePanel.add(btnAddSong);
		
		btnDeleteSong = new JButton("   Delete Song");
		btnDeleteSong.setBorder(null);
		btnDeleteSong.setBackground(new Color(216, 191, 216));
		btnDeleteSong.setIcon(new ImageIcon(".\\icons\\bin 20x20.png"));
		btnDeleteSong.setBounds(480, 33, 140, 25);
		managementModePanel.add(btnDeleteSong);
		
		btnSortSong = new JButton("   Sort");
		btnSortSong.setBorder(null);
		btnSortSong.setBackground(new Color(216, 191, 216));
		btnSortSong.setIcon(new ImageIcon(".\\icons\\sort 20x20.png"));
		btnSortSong.setBounds(292, 33, 130, 25);
		managementModePanel.add(btnSortSong);
		
		musicList = new JList<>(musicListModel);
		musicList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		musicList.setBackground(new Color(99,69,138));
		musicList.setSelectionBackground(new Color(178, 136, 192));
		musicList.setSelectionForeground(new Color(255,255,255));
		musicList.setForeground(Color.BLACK);
		musicList.setFont(poppinsRegular15);
		musicList.setBounds(100, 100, 75, 75);
		
		musicListScrollPane = new JScrollPane(musicList);
		musicListScrollPane.setBorder(null);
		musicListScrollPane.setBounds(97, 123, 523, 467);
		musicListScrollPane.setBackground(new Color(99,69,138));
		managementModePanel.add(musicListScrollPane);
		
		//all Listeners
		btnAddSong.addActionListener(e -> openFile());
		btnDeleteSong.addActionListener(e -> deleteSong());
		btnSortSong.addActionListener(e -> sortSongs());
		btnManagementMode.addActionListener(e -> switchPanel(false, modePanels));
		btnUserMode.addActionListener(e -> switchPanel(true, modePanels));
		btnCreatePlaylist.addActionListener(e -> createPlaylist());
		btnDelPlaylist.addActionListener(e -> deletePlaylist());
		btnStartSong.addActionListener(e -> playSong());
		btnPauseResumeSong.addActionListener(e -> pauseResumeSong());
		btnRestartSong.addActionListener(e -> restartSong());
		btnStopSong.addActionListener(e -> stopSong());
		btnSkipBack.addActionListener(e -> skipBackSong());
		btnSkipForward.addActionListener(e -> skipForwardSong());
		
		//shows songs when click on playlist
		playlistList.addListSelectionListener(e -> showSongsInPlaylist());
		
		//Help from: https://stackoverflow.com/questions/26271760/filtering-a-jlist-from-text-field-input
		//Implements dynamic search
		//couldn't find a way to wrap all of it in an extra method
		searchField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void insertUpdate(DocumentEvent e) {search();}
			@Override
			public void removeUpdate(DocumentEvent e) {search();}
			@Override
			public void changedUpdate(DocumentEvent e) {}
			
			private void search() {
			String searchString = searchField.getText();
			searchMusicList(musicListModel, searchString.toLowerCase());
			
			}
		});
		
		//changes button color while mouse is pressed
		//couldn't find a way to wrap all of it in an extra method
		btnAddSong.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnAddSong.setBackground(new Color(99,69,138));
				btnAddSong.setContentAreaFilled(false);
				btnAddSong.setOpaque(true);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnAddSong.setBackground(new Color(216, 191, 216));
			}
		});
		
		btnDeleteSong.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnDeleteSong.setBackground(new Color(99,69,138));
				btnDeleteSong.setContentAreaFilled(false);
				btnDeleteSong.setOpaque(true);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnDeleteSong.setBackground(new Color(216, 191, 216));
			}
		});

		btnSortSong.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnSortSong.setBackground(new Color(99,69,138));
				btnSortSong.setContentAreaFilled(false);
				btnSortSong.setOpaque(true);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnSortSong.setBackground(new Color(216, 191, 216));
			}
		});	
		
		btnManagementMode.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnManagementMode.setBackground(new Color(126,90,155));
				btnManagementMode.setContentAreaFilled(false);
				btnManagementMode.setOpaque(true);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnManagementMode.setBackground(new Color(204, 153, 255));
			}
		});		
		btnUserMode.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnUserMode.setBackground(new Color(126,90,155));
				btnUserMode.setContentAreaFilled(false);
				btnUserMode.setOpaque(true);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnUserMode.setBackground(new Color(204, 153, 255));
			}
		});
		
		//hilfe von: https://stackoverflow.com/questions/6084039/create-custom-operation-for-setdefaultcloseoperation
		WindowListener exitListener = new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	data.storeSongs();
		    	data.storePlaylists();
		    	System.exit(0);     
		    }
		};
		this.addWindowListener(exitListener);
		
		
		
		
	}
	
	private void openFile() {
		JFileChooser fileChooser = new JFileChooser();
		//Haben folgende Line Code nicht verallgemeinern können -> zum setzen eines Startpfades 
		//fileChooser.setCurrentDirectory(new File("C:\\Users\\marcn\\OneDrive\\HTWK\\Module\\2.Semester\\Programmierung\\Exam_MusicManagement\\SaMaMui\\res"));
		int response = fileChooser.showOpenDialog(null);
		
		if (response == JFileChooser.APPROVE_OPTION) {
			String filePath = new String(fileChooser.getSelectedFile().getAbsolutePath());
			String songTitle = (String)JOptionPane.showInputDialog(
		               							contentPane,
		               							"Please enter a Song Title", 
		               							"Enter Title",            
		               							JOptionPane.PLAIN_MESSAGE);
			String artistName = (String)JOptionPane.showInputDialog(
												contentPane,
												"Now please enter the name of the artist", 
												"Enter Artist Name",            
												JOptionPane.PLAIN_MESSAGE);
			String genreName = (String)JOptionPane.showInputDialog(
												contentPane,
												"And finnaly please enter the name of the genre", 
												"Enter Genre Name",            
												JOptionPane.PLAIN_MESSAGE);
			if(songTitle == null || songTitle.equals("")) {
				songTitle = "unnamed";
			}
			if (artistName == null || artistName.equals("")){
				artistName = "unnamed";
			}
			if (genreName == null || genreName.equals("")){
				genreName = "unnamed";
			}
			
			switch(data.checkIfSongArtistGenreExists(songTitle, artistName, genreName))
			{
			//song already exists
			case 1:
				JOptionPane.showMessageDialog(contentPane, 
						"Error song already exists! Change the songtitle if you still want to add it.",
						"Song already exists", 
						JOptionPane.WARNING_MESSAGE);
				break;
			
			//song and genre have to be created - artist exists
			case 2:
				try {
					song = new Song(songTitle, data.getArtist(artistName), genreName, filePath);
					musicListModel.addElement(song);
					
					data.addSong(song);
					data.addGenre(song.getGenre());
					
					System.out.println("Song successfully added. Have a look at the Database:");
					System.out.println("Songs: " + data.getSongList());
					System.out.println("Artists: " + data.getArtistList());
					System.out.println("Genre: " + data.getGenreList());
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(contentPane, 
								"Error with playing Sound! Ensure you are using one of the following formats: AIFC, AIFF, AU, SND, WAVE",
								"Error with playing Sound", 
								JOptionPane.WARNING_MESSAGE);
				}
				break;
			
			//song has to be created - artist and genre exist	
			case 3:
				try {
					song = new Song(songTitle, data.getArtist(artistName), data.getGenre(genreName), filePath);
					musicListModel.addElement(song);
						
					data.addSong(song);
					
					System.out.println("Song successfully added. Have a look at the Database:");
					System.out.println("Songs: " + data.getSongList());
					System.out.println("Artists: " + data.getArtistList());
					System.out.println("Genre: " + data.getGenreList());
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(contentPane, 
								"Error with playing Sound! Ensure you are using one of the following formats: AIFC, AIFF, AU, SND, WAVE",
								"Error with playing Sound", 
								JOptionPane.WARNING_MESSAGE);
				}
				break;
			
			//song and artist have to be created - genre exists	
			case 4:
				try {
					song = new Song(songTitle, artistName, data.getGenre(genreName), filePath);
					musicListModel.addElement(song);
						
					data.addSong(song);
					data.addArtist(song.getArtist());
					
					System.out.println("Song successfully added. Have a look at the Database:");
					System.out.println("Songs: " + data.getSongList());
					System.out.println("Artists: " + data.getArtistList());
					System.out.println("Genre: " + data.getGenreList());
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(contentPane, 
								"Error with playing Sound! Ensure you are using one of the following formats: AIFC, AIFF, AU, SND, WAVE",
								"Error with playing Sound", 
								JOptionPane.WARNING_MESSAGE);
				}
				break;
			
			//song, artist and genre have to be created - nothing exists	
			case 5: 
				try {
					song = new Song(songTitle, artistName, genreName, filePath);
					musicListModel.addElement(song);
						
					data.addSong(song);
					data.addArtist(song.getArtist());
					data.addGenre(song.getGenre());
					
					System.out.println("Song successfully added. Have a look at the Database:");
					System.out.println("Songs: " + data.getSongList());
					System.out.println("Artists: " + data.getArtistList());
					System.out.println("Genre: " + data.getGenreList());
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(contentPane, 
								"Error with playing Sound! Ensure you are using one of the following formats: AIFC, AIFF, AU, SND, WAVE",
								"Error with playing Sound", 
								JOptionPane.WARNING_MESSAGE);
				}
					
			}
			
		}
	}
	
	private void deleteSong() {
		
		int index = musicList.getSelectedIndex();
		
		if (index == -1) {
	    	return;    
	    }
		
		try {
		    Song song = musicListModel.elementAt(index);
		    data.delSong(song);
		    musicListModel.remove(index);
		    playlistModel.clear();
		    playlistModel.addAll(data.getPlaylistList());
		    playlistSongsModel.clear();
		    System.out.println("Song successfully deleted. Have a look at the Database:");
			System.out.println("Songs: " + data.getSongList());
			System.out.println("Artists: " + data.getArtistList());
			System.out.println("Genre: " + data.getGenreList());
			System.out.println("Playlists: " + data.getPlaylistList());
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, 
						"Please select a Song to deleate or the List is empty",
						"No Song selected", 
						JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	    
	    if (index == musicListModel.getSize()) {
	    	index--;
	    	musicList.setSelectedIndex(index);
	        musicList.ensureIndexIsVisible(index);
	    }
		
	}
	
	private void switchPanel(boolean adminPanelActive, JPanel modePanels) {
		
		if (adminPanelActive == false) {
			cardLayout.show(modePanels, "managementModePanel");
		}
		else {
			cardLayout.show(modePanels, "userModePanel");
			songsInPlaylistList.repaint();
		}
		
	}
	
	private Font createCustomFont(String filePath, float size) {
		Font font = null;
		try {
		    //creates font with certain size from file
		    font = Font.createFont(Font.TRUETYPE_FONT, new File(filePath)).deriveFont(size);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //register the font
		    ge.registerFont(font);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		return font;
		
	}
	
	private void createPlaylist() {
		playListCreteriaBox = new JComboBox(data.getPlaylistCreteria());
		artistSelectionBox = new JComboBox(data.getArtistList().toArray());
		genreSelectionBox = new JComboBox(data.getGenreList().toArray());
		
		JOptionPane.showMessageDialog(
					contentPane, 
					playListCreteriaBox, 
					"What kind of Playlist do you want to create?", 
					JOptionPane.QUESTION_MESSAGE);
		
		int selection = playListCreteriaBox.getSelectedIndex();
		
		switch(selection) {

		//Artist
		case 0: 
			JOptionPane.showMessageDialog(
						contentPane, 
						artistSelectionBox, 
						"Which artist do you want to create a playlist of?", 
						JOptionPane.QUESTION_MESSAGE);
			Artist artist = (Artist) artistSelectionBox.getSelectedItem();
			String playlistName =(String)JOptionPane.showInputDialog(
													contentPane,
													"Please enter a name for your playlist", 
													"Enter Name",            
													JOptionPane.PLAIN_MESSAGE);
			if(playlistName == null || playlistName.equals("")) {
				playlistName = "unnamed";
			}
			if(!data.checkIfPlaylistExists(playlistName)) {
				Playlist playlist = new Playlist(playlistName, artist);
				data.addPlaylist(playlist);
				playlistModel.addElement(playlist);
				
				System.out.println("You successfully created a playlist of type artist. Have a look at the playlists:");
				System.out.println("Playlists: " + data.getPlaylistList());	
			}
			else {
				JOptionPane.showMessageDialog(contentPane, 
						"Error your playlist name is already taken.",
						"Error with playlist name", 
						JOptionPane.WARNING_MESSAGE);
			}
			break;
		
		//Genre
		case 1:
			JOptionPane.showMessageDialog(
						contentPane, 
						genreSelectionBox, 
						"What genre do you want to create a playlist of?", 
						JOptionPane.QUESTION_MESSAGE);
			Genre genre = (Genre) genreSelectionBox.getSelectedItem();
			playlistName =(String)JOptionPane.showInputDialog(
													contentPane,
													"Please enter a name for your playlist", 
													"Enter Name",            
													JOptionPane.PLAIN_MESSAGE);
			if(playlistName == null || playlistName.equals("")) {
				playlistName = "unnamed";
			}
			if(!data.checkIfPlaylistExists(playlistName)) {
				Playlist playlist = new Playlist(playlistName, genre);
				data.addPlaylist(playlist);
				playlistModel.addElement(playlist);
				
				System.out.println("You successfully created a playlist of type genre. Have a look at the playlists:");
				System.out.println("Playlists: " + data.getPlaylistList());	
			}
			else {
				JOptionPane.showMessageDialog(contentPane, 
						"Error your playlist name is already taken.",
						"Error with playlist name", 
						JOptionPane.WARNING_MESSAGE);
			}
			System.out.println(data.getPlaylistList());
			break;
		}
		
	}
	
	private void deletePlaylist() {
		int index = playlistList.getSelectedIndex();
		
		if (index == -1) {
	    	return;    
	    }
		
		try {
		    Playlist playlist = playlistModel.elementAt(index);
		    data.delPlaylist(playlist);
		    playlistModel.remove(index);
		    
		    System.out.println("You successfully deleted a playlist. Have a look at the playlists:");
			System.out.println("Playlists: " + data.getPlaylistList());	
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, 
						"Please select a Playlist to deleate or the List is empty",
						"No Playlist selected", 
						JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}
	
	private void showSongsInPlaylist() {
		int index = playlistList.getSelectedIndex();
		
		if (index == -1) {
	    	return;    
	    }
		
		Playlist playlist = playlistModel.elementAt(index);
		playlistSongsModel.clear();
		
		for(Song s: playlist.getSongList()) {
			playlistSongsModel.addElement(s);	
		}
		
	}
	
	private void playSong() {
		int index = songList.getSelectedIndex();
		
		if(currentlyPlayingSong != null) {
			try {
				currentlyPlayingSong.stop();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		if (index == -1 || (currentlyPlayingSong == null && songList.getSelectedIndex() == -1)) {
			JOptionPane.showMessageDialog(contentPane, 
					"Please select a song to play",
					"No Song selected", 
					JOptionPane.WARNING_MESSAGE);
	    	return;    
	    }
		
		Song song = musicListModel.elementAt(index);
		currentlyPlayingSong = song;
		song.play();
		
		System.out.println("Status of Song: " + currentlyPlayingSong.getStatus());	
	}
	
	private void pauseResumeSong() {
		if (currentlyPlayingSong == null ) 
        {
			JOptionPane.showMessageDialog(contentPane, 
					"No Song selected",
					"", 
					JOptionPane.WARNING_MESSAGE);
            return;
        }
		else {
			if(currentlyPlayingSong.getStatus().equals("paused")) {
				try {
					currentlyPlayingSong.resume();
					System.out.println("Status of Song: " + currentlyPlayingSong.getStatus());
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			else  {
				currentlyPlayingSong.pause();
				System.out.println("Status of Song: " + currentlyPlayingSong.getStatus());
			}
		}
	}
	
	private void restartSong() {
		if (currentlyPlayingSong == null ) 
        {
			JOptionPane.showMessageDialog(contentPane, 
					"No Song selected",
					"", 
					JOptionPane.WARNING_MESSAGE);
            return;
        }
		else {
			try {
			 currentlyPlayingSong.restart();
			 System.out.println("Status of Song: " + currentlyPlayingSong.getStatus());
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void stopSong() {
		if (currentlyPlayingSong == null ) 
        {
			JOptionPane.showMessageDialog(contentPane, 
					"No Song selected",
					"", 
					JOptionPane.WARNING_MESSAGE);
            return;
        }
		else {
			try {
			 currentlyPlayingSong.stop();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//skips Back 10 sec
	private void skipBackSong() {
		if (currentlyPlayingSong == null ) 
        {
			JOptionPane.showMessageDialog(contentPane, 
					"No Song selected",
					"", 
					JOptionPane.WARNING_MESSAGE);
            return;
        }
		else {
			try {
			 currentlyPlayingSong.jumpTenSecBackward();
			 System.out.println("You skipped back 10 sec, you are now at position " + currentlyPlayingSong.getCurrentFrame());
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//skips forward 10 sec
	private void skipForwardSong() {
		if (currentlyPlayingSong == null ) 
        {
			JOptionPane.showMessageDialog(contentPane, 
					"No Song selected",
					"", 
					JOptionPane.WARNING_MESSAGE);
            return;
        }
		else {
			try {
			 currentlyPlayingSong.jumpTenSecForward();
			 System.out.println("You skipped forward 10 sec, you are now at position " + currentlyPlayingSong.getCurrentFrame());
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void sortSongs() {
		data.sortSongs();
		musicListModel.clear();
		musicListModel.addAll(data.getSongList());
		System.out.println("Songs are now sorted alphabetically.");
	}
	
	private void searchMusicList(DefaultListModel<Song> model, String searchString) {
		for(Song s: data.getSongList()) {
			if(!(s.getTitle().toLowerCase().startsWith(searchString) || s.getArtist().getName().toLowerCase().startsWith(searchString) || s.getGenre().getName().toLowerCase().startsWith(searchString))) {
				if(model.contains(s)) {
					model.removeElement(s);
				}
			}
			else  {
				if(!model.contains(s)) {
					model.addElement(s);
				}
			}
		}
	}
}
