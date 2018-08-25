/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idrive;

import java.util.ArrayList;

/**
 *
 * @author Tsuka
 */
public class MusicCollection {
	ArrayList<MusicRecord> contents;

	public MusicCollection() {
		contents = new ArrayList(0);
	}

	public void add(MusicRecord rec) {
		contents.add(rec);
	}

	public void remove(MusicRecord rec) {
		contents.remove(rec);
	}

	public void clear() {
		contents.clear();
	}
}
