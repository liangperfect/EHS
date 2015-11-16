package com.example.ehs.xmpphelper;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteOrder;

import com.example.ehs.im.ChatActivity;

import android.util.Base64;
import android.util.Log;

public class AudioEncoder {

	static {
		System.loadLibrary("audiowrapper");
	}

	byte[] audiobyte;// 存放音频数据
	int audiolenth;// 音频数据的大小
	int encodeSize = 0;// 编码后的数据大小
	byte[] encodedAudio = new byte[256];// 存放编码后的数据
	DatagramPacket dataPacket;
	InetAddress ip;
	int port;
	DatagramSocket socket;

	/**
	 * 对数据进行编码
	 */
	public void startEncoder(String path) {
		System.out.println("录制文件的路径" + path);

		try {
			File file = new File(path);
			FileInputStream fileinput = new FileInputStream(file);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int lenth = 0;
			try {
				while ((lenth = fileinput.read(b)) != -1) {
					output.write(b, 0, lenth);
				}
				audiobyte = output.toByteArray();
				audiolenth = audiobyte.length;// 音频数据的大小
				output.close();
				fileinput.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (audiolenth > 0) {
			encodeAudio();// 对数据进行编码
		}

	}

	/**
	 * 对音频数据进行编码
	 */
	public void encodeAudio() {
	

		AudioCodec.audio_codec_init(30);
		encodedAudio = new byte[audiolenth];
		// 编码语句
		encodeSize = AudioCodec.audio_encode(audiobyte, 0, audiolenth,
				encodedAudio, 0);
		if (encodeSize > 0) {
			// clear data
			System.out.println("解码后的大小："+encodedAudio.length);
			String messageStr = Base64.encodeToString(encodedAudio, Base64.DEFAULT);
			System.out.println("messageStr="+messageStr);
			//ChatActivity.sendMessage("3"+messageStr, 3);
		//	sendAudio();//发送数据
		//	encodedAudio = new byte[encodedAudio.length];

		}
	}

	/**
	 * 发送编码后的音频数据
	 */
	/*public void sendAudio() {
		try {
			ip = InetAddress.getByName(NetConfig.SERVER_HOST);
			port = NetConfig.SERVER_PORT;
			socket = new DatagramSocket();
			dataPacket = new DatagramPacket(encodedAudio, encodeSize, ip, port);
			dataPacket.setData(encodedAudio);
			socket.send(dataPacket);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}*/
	
	
	

}
