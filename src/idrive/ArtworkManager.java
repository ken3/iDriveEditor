/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idrive;

import java.net.URL;
import java.awt.Image;
import java.awt.MediaTracker;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 機能: アイコン画像を更新する
 * 制約: JPG,PNG,GIFに対応。BPMには未対応。
 * @author Tsuka
 */
public class ArtworkManager {
	private JLabel label;
	private ImageIcon icon;

	public ArtworkManager(JLabel target) {
		label = target;
	}
	
	public void update(URL location) {
		System.out.printf("ArtworkManager.update(%s)\n", location);
		// 画像を読み込む
		ImageIcon newicon = new ImageIcon(location);
		if (newicon == null) {
			System.err.printf("イメージファイル(%s)の読み込みが失敗しました。\n", location);
			return;
		}
		update(newicon);
	}
	
	public void update(String file) {
		System.out.printf("ArtworkManager.update(%s)\n", file);
		// 画像を読み込む
		ImageIcon newicon = new ImageIcon(file);
		if (newicon == null) {
			System.err.printf("イメージファイル(%s)の読み込みが失敗しました。\n", file);
			return;
		}
		update(newicon);
	}

	public void update(ImageIcon source) {
		// ラベルの大きさに合わせて画像をリサイズする
		icon = source;
		int width = label.getWidth();
		int height = label.getHeight();
		Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

		// リサイズ処理の完了を待つ
		MediaTracker tracker = new MediaTracker(label);
		tracker.addImage(image, 1);
		ImageIcon smallIcon = new ImageIcon(image);
		try {
			tracker.waitForAll();
			System.err.println("MediaTracker 処理完了。");
		} catch (InterruptedException e) {
			System.err.println("なんかエラーでた。");
		}
 
		// ラベルのアイコンを更新する
		label.setIcon(smallIcon);
	}
}
