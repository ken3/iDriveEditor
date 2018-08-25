/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idrive;

import java.awt.image.BufferedImage;

/**
 *
 * @author Tsuka
 */
public class MusicRecord {
	private int no;
	private String title;
	private String artist;
	private String album;
	private BufferedImage artwork;

	public MusicRecord() {
		no = 0;
		title = null;
		artist = null;
		album = null;
		artwork = null;
	}

	public MusicRecord(int no, String title) {
		this.no = no;
		this.title = title;
		artist = "<None>";
		album = "<None>";
		artwork = null;
	}

	public int getNo() { return no; }
	public void setNo(int no) { this.no = no; }

	public String getTitle() { return title; }
	void setTitle(String title) { this.title = title; }
	
	String getArtist() { return artist; }
	void setArtist(String artist) { this.artist = artist; }
	
	String getAlbum() { return album; }
	void setAlbum(String album) { this.album = album; }
	
	BufferedImage getArtwork() { return artwork; }
	void setArtwork(BufferedImage artwork) { this.artwork = artwork; }

	Object[] getRow() {
		Object[] row = { no, title, artist, album };
		return row;
	}
}
