package com.example.ehs.utils;

import java.util.Calendar; 
import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.Roster.SubscriptionMode;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.GroupChatInvitation;
import org.jivesoftware.smackx.PrivateDataManager;
import org.jivesoftware.smackx.bytestreams.ibb.provider.CloseIQProvider;
import org.jivesoftware.smackx.bytestreams.ibb.provider.DataPacketProvider;
import org.jivesoftware.smackx.bytestreams.ibb.provider.OpenIQProvider;
import org.jivesoftware.smackx.bytestreams.socks5.provider.BytestreamsProvider;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.packet.ChatStateExtension;
import org.jivesoftware.smackx.packet.LastActivity;
import org.jivesoftware.smackx.packet.OfflineMessageInfo;
import org.jivesoftware.smackx.packet.OfflineMessageRequest;
import org.jivesoftware.smackx.packet.SharedGroupsInfo;
import org.jivesoftware.smackx.provider.DataFormProvider;
import org.jivesoftware.smackx.provider.DelayInformationProvider;
import org.jivesoftware.smackx.provider.DiscoverInfoProvider;
import org.jivesoftware.smackx.provider.DiscoverItemsProvider;
import org.jivesoftware.smackx.provider.MUCAdminProvider;
import org.jivesoftware.smackx.provider.MUCOwnerProvider;
import org.jivesoftware.smackx.provider.MUCUserProvider;
import org.jivesoftware.smackx.provider.MessageEventProvider;
import org.jivesoftware.smackx.provider.MultipleAddressesProvider;
import org.jivesoftware.smackx.provider.RosterExchangeProvider;
import org.jivesoftware.smackx.provider.StreamInitiationProvider;
import org.jivesoftware.smackx.provider.VCardProvider;
import org.jivesoftware.smackx.provider.XHTMLExtensionProvider;
import org.jivesoftware.smackx.search.UserSearch;

import android.content.Context;
import android.text.format.Time;
import android.widget.Toast;
/*
 * 即时通讯模块
 * xmpp连接方法
 * */
public class ConnectionUtils {
	private static XMPPConnection connection;
	public static String host = "222.18.162.130";//唐家乐电脑openfire 服务器地址
	//public static String host = "222.18.162.135";//黄超琼电脑openfire服务器地址
	//public static String host = "222.18.162.144";//张利电脑openfire服务器地址
	private static int port = 5222;
	private static ConnectionConfiguration connectionConfig;
	
	
	public ConnectionUtils(String host) {
		super();
		ConnectionUtils.host = host;
	}
	public static void setHost(String host){
		ConnectionUtils.host = host;
		System.out.println("host1="+host);
	}
	public static String getHost(){
		System.out.println("host2="+ConnectionUtils.host);
		return ConnectionUtils.host;
	}
	// init
	public static void init(){	
	//}
	//static {
		Connection.DEBUG_ENABLED = false;

		ProviderManager pm = ProviderManager.getInstance();
		configure(pm);

		SmackConfiguration.setKeepAliveInterval(60000 * 5); // 5 mins
		SmackConfiguration.setPacketReplyTimeout(5000); // 10 secs
		SmackConfiguration.setLocalSocks5ProxyEnabled(false);
		Roster.setDefaultSubscriptionMode(SubscriptionMode.manual);
		
		System.out.println("host_now="+host);
		connectionConfig = new ConnectionConfiguration(host, port, "");
		connectionConfig.setTruststorePath("/system/etc/security/cacerts.bks");
		connectionConfig.setTruststorePassword("changeit");
		connectionConfig.setTruststoreType("bks");
		// 允许自动连接
		connectionConfig.setReconnectionAllowed(true);
		// 允许登陆成功后更新在线状态
		//connectionConfig.setSendPresence(true);//为了接收离线消息，先设置成为离线，获取完消息之后在设置为在线
		connectionConfig.setSendPresence(false);

	}

	public static XMPPConnection getRegConnection() {
		init();
		return new XMPPConnection(connectionConfig);
	}

	public static XMPPConnection getConnection(Context context) {
		init();
		if (ConnectionUtils.connection == null) {
			ConnectionUtils.connection = new XMPPConnection(connectionConfig);
		}

		if (!ConnectionUtils.connection.isConnected()) {
			try {
				ConnectionUtils.connection.connect();
			} catch (XMPPException e) {
				Toast.makeText(context, "连接出错,请稍后重试！", Toast.LENGTH_SHORT).show();
				System.out.println("连接出错");
				e.printStackTrace();
			}
		}
		return ConnectionUtils.connection;
	}
	// 获取HttpClient
	public static HttpClient getHttpClient() {
		HttpClient client = new DefaultHttpClient();
		return client;
	}

	public static String getStringTime() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
				+ c.get(Calendar.DAY_OF_MONTH) + "-" + c.get(Calendar.HOUR_OF_DAY) + ":"
				+ c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
	}
//加入聊天室
	public static MultiUserChat joinMultiChat(String n, String account) {
		init();
		MultiUserChat multiUserChat = new MultiUserChat(ConnectionUtils.connection, n);
		if (!multiUserChat.isJoined()) {
			try {
				//进入聊天室的名字
				multiUserChat.join(account);
			} catch (XMPPException e) {
				e.printStackTrace();
			}
		}
		
		return multiUserChat;
	}	
	
	public static void configure(ProviderManager pm) {

		// Private Data Storage
		pm.addIQProvider("query", "jabber:iq:private",
				new PrivateDataManager.PrivateDataIQProvider());

		// Time
		try {
			pm.addIQProvider("query", "jabber:iq:time",
					Class.forName("org.jivesoftware.smackx.packet.Time"));
		} catch (ClassNotFoundException e) {
		}

		// XHTML
		pm.addExtensionProvider("html", "http://jabber.org/protocol/xhtml-im",
				new XHTMLExtensionProvider());

		// Roster Exchange
		pm.addExtensionProvider("x", "jabber:x:roster",
				new RosterExchangeProvider());
		// Message Events
		pm.addExtensionProvider("x", "jabber:x:event",
				new MessageEventProvider());
		// Chat State
		pm.addExtensionProvider("active",
				"http://jabber.org/protocol/chatstates",
				new ChatStateExtension.Provider());
		pm.addExtensionProvider("composing",
				"http://jabber.org/protocol/chatstates",
				new ChatStateExtension.Provider());
		pm.addExtensionProvider("paused",
				"http://jabber.org/protocol/chatstates",
				new ChatStateExtension.Provider());
		pm.addExtensionProvider("inactive",
				"http://jabber.org/protocol/chatstates",
				new ChatStateExtension.Provider());
		pm.addExtensionProvider("gone",
				"http://jabber.org/protocol/chatstates",
				new ChatStateExtension.Provider());

		// FileTransfer
		pm.addIQProvider("si", "http://jabber.org/protocol/si",
				new StreamInitiationProvider());
		pm.addIQProvider("query", "http://jabber.org/protocol/bytestreams",
				new BytestreamsProvider());
		pm.addIQProvider("open", "http://jabber.org/protocol/ibb",
				new OpenIQProvider());
		pm.addIQProvider("close", "http://jabber.org/protocol/ibb",
				new CloseIQProvider());
		pm.addExtensionProvider("data", "http://jabber.org/protocol/ibb",
				new DataPacketProvider());

		// Group Chat Invitations
		pm.addExtensionProvider("x", "jabber:x:conference",
				new GroupChatInvitation.Provider());
		// Service Discovery # Items
		pm.addIQProvider("query", "http://jabber.org/protocol/disco#items",
				new DiscoverItemsProvider());
		// Service Discovery # Info
		pm.addIQProvider("query", "http://jabber.org/protocol/disco#info",
				new DiscoverInfoProvider());
		// Data Forms
		pm.addExtensionProvider("x", "jabber:x:data", new DataFormProvider());
		// MUC User
		pm.addExtensionProvider("x", "http://jabber.org/protocol/muc#user",
				new MUCUserProvider());
		// MUC Admin
		pm.addIQProvider("query", "http://jabber.org/protocol/muc#admin",
				new MUCAdminProvider());
		// MUC Owner
		pm.addIQProvider("query", "http://jabber.org/protocol/muc#owner",
				new MUCOwnerProvider());
		// Delayed Delivery
		pm.addExtensionProvider("x", "jabber:x:delay",
				new DelayInformationProvider());
		// Version
		try {
			pm.addIQProvider("query", "jabber:iq:version",
					Class.forName("org.jivesoftware.smackx.packet.Version"));
		} catch (ClassNotFoundException e) {
		}
		// VCard
		pm.addIQProvider("vCard", "vcard-temp", new VCardProvider());
		// Offline Message Requests
		pm.addIQProvider("offline", "http://jabber.org/protocol/offline",
				new OfflineMessageRequest.Provider());
		// Offline Message Indicator
		pm.addExtensionProvider("offline",
				"http://jabber.org/protocol/offline",
				new OfflineMessageInfo.Provider());
		// Last Activity
		pm.addIQProvider("query", "jabber:iq:last", new LastActivity.Provider());
		// User Search
		pm.addIQProvider("query", "jabber:iq:search", new UserSearch.Provider());
		// SharedGroupsInfo
		pm.addIQProvider("sharedgroup",
				"http://www.jivesoftware.org/protocol/sharedgroup",
				new SharedGroupsInfo.Provider());
		// JEP-33: Extended Stanza Addressing
		pm.addExtensionProvider("addresses",
				"http://jabber.org/protocol/address",
				new MultipleAddressesProvider());

	}
	
	
}
