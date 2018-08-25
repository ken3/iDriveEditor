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
import java.util.List;
import javax.swing.TransferHandler;

/**
 *
 * @author Tsuka
 */
public class DropImageFileHandler extends TransferHandler {
	private ArtworkManager  manager;
	public DropImageFileHandler(ArtworkManager m) {
		manager = m;
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

			// 受け取ったイメージでアートワークを更新する
			for (File file : files){
				// String filename = file.getName();
				String infile = new String(file.getAbsolutePath());
				System.out.printf("artwork: %s\n", infile);
               			manager.update(infile);
			}
		} catch (UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
