package com.example.ehs.xmpphelper;

import java.io.File;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;

import android.widget.Toast;


public class RecFileTransferListener implements FileTransferListener {
	public void fileTransferRequest(FileTransferRequest request) {
		System.out.println("aaa");
		IncomingFileTransfer accept = request.accept();
	    String path = "/sdcard/EHS/";
        File saveFile = new File(path);
        saveFile.mkdirs();
		File file = new File(path + request.getFileName());
		try {
			System.out.println("bbb");
			accept.recieveFile(file);
			System.out.println("接收文件=====");
			//floder_send.setText("已接受！");
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
}
