/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idrive;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.TransferHandler;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.TagException;

/**
 *
 * @author Tsuka
 */
/**
 * ドロップ操作の処理を行うクラス
 */
public class DropMusicFileHandler extends TransferHandler {
	private MusicCollection list;
	private JTable playlist;

	public DropMusicFileHandler(MusicCollection collection, JTable view) {
		list = collection;
		playlist = view;
	}
	
	/**
	 * ドロップされたものを受け取るか判断 (ファイルのときだけ受け取る)
	 */
	@Override
	public boolean canImport(TransferHandler.TransferSupport support) {
		if (!support.isDrop()) {
			// ドロップ操作でない場合は受け取らない
			return false;
		}
		if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			// ドロップされたのがファイルでない場合は受け取らない
			return false;
		}
		return true;
	}

	/**
	 * ドロップされたファイルを受け取る
	 */
	@Override
	public boolean importData(TransferHandler.TransferSupport support) {
		// 受け取っていいものか確認する
		if (!canImport(support)) {
			return false;
		}
		// ドロップ処理
		Transferable t = support.getTransferable();
		try {
			// ファイルを受け取る
			List<File> files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);

			// ファイルを変換する
			ArrayList<File> target = new ArrayList();
			for (File file : files) {
				String infile = new String(file.getAbsolutePath());
				// String outfile = infile + ".m4a";
				String outfile = infile + ".mp3";
				if (infile.endsWith(".BR25")) {
					System.out.printf("Creating %s ...\n", outfile);
					byte[] contents = Files.readAllBytes(Paths.get(infile));
					for (int i = 0; i < contents.length; i++) {
						byte x = contents[i];
						contents[i] = (byte)~x;
					}
					Files.write(Paths.get(outfile), contents);
					target.add(new File(outfile));
				} else {
					System.out.printf("Skip %s ...\n", infile);
					target.add(file);
				}
			}

			// 対象ファイルを処理する
			DefaultTableModel model = (DefaultTableModel)playlist.getModel();
			int row = playlist.getRowCount();
			for (File file : target) {
				String infile = file.getAbsolutePath();
				String filename = file.getName();
				System.out.printf("checkin: %s\n", infile);

				// ID3TAG情報を取得する
				try {
					AudioFile f = AudioFileIO.read(new File(infile));
					Tag tag = f.getTag();
					Iterator<TagField> i = tag.getFields();
					while (i.hasNext()) {
						final TagField tf = i.next();
						System.out.printf("[TagField %s] %s\n", tf.getId(), tf.toString());
					}
				} catch (TagException e) {
					System.err.println("[TagException]");
				} catch (ReadOnlyFileException e) {
					System.err.println("[ReadOnlyFileException]");
				} catch (InvalidAudioFrameException e) {
					System.err.println("[InvalidAudioFrameException]");
				} catch (CannotReadException e) {
					System.err.println("[CannotReadException]");
				}

				// テーブルにファイル名のリストを追加する
				MusicRecord music = new MusicRecord(row+1, filename);
				list.add(music);
				model.addRow(music.getRow());
				row++;
			}
		} catch (UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
