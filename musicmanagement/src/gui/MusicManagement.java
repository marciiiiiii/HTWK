package gui;

import java.awt.EventQueue;
//Controller
public class MusicManagement {
	
	private MusicManagementGUI view;
	private MusicManagementData data;
	
	 public MusicManagement() {
	        this.data = new MusicManagementData();
	        this.view = new MusicManagementGUI(data);
	    }
	 
	 public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						MusicManagement controller = new MusicManagement();
						controller.view.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

}


