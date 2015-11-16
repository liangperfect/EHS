package com.bairuitech.anychat;		

import java.lang.ref.WeakReference;

import android.view.Surface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;


public class AnyChatCoreSDK
{
	AnyChatBaseEvent		baseEvent;
	AnyChatStateChgEvent	stateChgEvent;
	AnyChatPrivateChatEvent	privateChatEvent;
	AnyChatTextMsgEvent		textMsgEvent;
	AnyChatTransDataEvent	transDataEvent;
	AnyChatVideoCallEvent	videoCallEvent;
	AnyChatUserInfoEvent	userInfoEvent;
	
	static MainHandler mHandler;
	static AnyChatAudioHelper	mAudioHelper;
	public static AnyChatCameraHelper	mCameraHelper = new AnyChatCameraHelper();
	public AnyChatSensorHelper	mSensorHelper = new AnyChatSensorHelper();
	public AnyChatVideoHelper	mVideoHelper = new AnyChatVideoHelper();
	
	private static int HANDLE_TYPE_NOTIFYMSG 	= 1;	
	private static int HANDLE_TYPE_TEXTMSG 		= 2;	
	private static int HANDLE_TYPE_TRANSFILE 	= 3;	
	private static int HANDLE_TYPE_TRANSBUF		= 4;	
	private static int HANDLE_TYPE_TRANSBUFEX	= 5;	
	private static int HANDLE_TYPE_SDKFILTER	= 6;	
	private static int HANDLE_TYPE_VIDEOCALL	= 7;	
	
	public void SetBaseEvent(AnyChatBaseEvent e)
	{
		mHandler = new MainHandler(this);
		RegisterNotify();
		this.baseEvent = e;
	}
	public void SetStateChgEvent(AnyChatStateChgEvent e)
	{
		RegisterNotify();
		this.stateChgEvent = e;
	}
	public void SetPrivateChatEvent(AnyChatPrivateChatEvent e)
	{
		RegisterNotify();
		this.privateChatEvent = e;
	}
	public void SetTextMessageEvent(AnyChatTextMsgEvent e)
	{
		RegisterNotify();
		this.textMsgEvent = e;
	}
	public void SetTransDataEvent(AnyChatTransDataEvent e)
	{
		RegisterNotify();
		this.transDataEvent = e;
	}
	public void SetVideoCallEvent(AnyChatVideoCallEvent e)
	{
		RegisterNotify();
		this.videoCallEvent = e;
	}
	public void SetUserInfoEvent(AnyChatUserInfoEvent e)
	{
		RegisterNotify();
		this.userInfoEvent = e;
	}
	
	public int GetSDKMainVersion()
	{
		return GetSDKOptionInt(AnyChatDefine.BRAC_SO_CORESDK_MAINVERSION);
	}
	public int GetSDKSubVersion()
	{
		return GetSDKOptionInt(AnyChatDefine.BRAC_SO_CORESDK_SUBVERSION);
	}
	public String GetSDKBuildTime()
	{
		return GetSDKOptionString(AnyChatDefine.BRAC_SO_CORESDK_BUILDTIME);
	}
    
    public native int RegisterNotify();
    
    public native int InitSDK(int osver, int flags);
    public native int Connect(String serverip, int port);
    public native int Login(String username, String password);
    public native int EnterRoom(int roomid, String password);
    public native int EnterRoomEx(String roomname, String password);
    
    public native int LeaveRoom(int roomid);
    public native int Logout();
    public native int Release();
    
    public native int[] GetOnlineUser();
    public native int SetVideoPos(int userid, Surface s, int lef, int top, int right, int bottom);
    public native int UserCameraControl(int userid, int bopen);
    public native int UserSpeakControl(int userid, int bopen);
	public native int StreamRecordCtrl(int userid, int bstartrecord, int flags, int param);
	
	public native int AudioGetVolume(int device);
	public native int AudioSetVolume(int device, int volume);
    
    public native String QueryUserStateString(int userid, int infoname);
	public native int QueryUserStateInt(int userid, int infoname);
  
    public native int GetUserSpeakVolume(int userid);
    public native int GetCameraState(int userid);
    public native int GetSpeakState(int userid);
	public native int GetUserVideoWidth(int userid);
	public native int GetUserVideoHeight(int userid);

	public native int SetServerAuthPass(String Password);
	public static native int SetSDKOptionInt(int optname, int optvalue);
	public native int SetSDKOptionString(int optname, String optvalue);
	public static native int GetSDKOptionInt(int optname);
	public native String GetSDKOptionString(int optname);
	
	public native int SendTextMessage(int userid, int secret, String message);
	public native int TransFile(int userid, String filepath, int wparam, int lparam, int flags, AnyChatOutParam outParam);
	public native int TransBuffer(int userid, byte[] buf, int len);
	public native int TransBufferEx(int userid, byte[] buf, int len, int wparam, int lparam, int flags, AnyChatOutParam outParam);
	public native int CancelTransTask(int userid, int taskid);
	public native int QueryTransTaskInfo(int userid, int taskid, int infoname, AnyChatOutParam outParam);
	public native int SendSDKFilterData(byte[] buf, int len);
	
	public static native byte[] FetchAudioPlayBuffer(int size);
	
	public void CameraAutoFocus()
	{
		SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_FOCUSCTRL, 1);
	}
	public String GetUserName(int userid)
	{
		return QueryUserStateString(userid, AnyChatDefine.BRAC_USERSTATE_NICKNAME);
	}
	public String GetUserIPAddr(int userid)
	{
		return QueryUserStateString(userid, AnyChatDefine.BRAC_USERSTATE_INTERNETIP);
	}
	
	public native String[] EnumVideoCapture();
	public native int SelectVideoCapture(String devicename);
	public native String GetCurVideoCapture();
	public native String[] EnumAudioCapture();
	public native int SelectAudioCapture(String devicename);
	public native String GetCurAudioCapture();
	public native String[] EnumAudioPlayback();
	public native int SelectAudioPlayback(String devicename);
	public native String GetCurAudioPlayback();	
	
	public native int ChangeChatMode(int chatmode);
	public native int GetUserChatMode(int userid);
	public native int PrivateChatRequest(int userid);
	public native int PrivateChatEcho(int userid, int requestid, int baccept);
	public native int PrivateChatEchoEx(int userid, int requestid, int errorcode);
	public native int PrivateChatExit(int userid);
	
	public static native int SetInputVideoFormat(int pixFmt, int dwWidth, int dwHeight, int dwFps, int dwFlags);
	public static native int InputVideoData(byte[] lpVideoFrame, int dwSize, int dwTimeStamp);
	public static native int SetInputAudioFormat(int dwChannels, int dwSamplesPerSec, int dwBitsPerSample, int dwFlags);
	public static native int InputAudioData(byte[] lpSamples, int dwSize, int dwTimeStamp);
	
	public native int VideoCallControl(int dwEventType, int dwUserId, int dwErrorCode, int dwFlags, int dwParam, String szUserStr);
	
	public native int[] GetUserFriends();
	public native int GetFriendStatus(int dwFriendUserId);
	public native int[] GetUserGroups();
	public native int[] GetGroupFriends(int dwGroupId);
	public native String GetUserInfo(int dwUserId, int dwInfoId);
	public native String GetGroupName(int dwGroupId);
	public native int UserInfoControl(int dwUserId, int dwCtrlCode, int wParam, int lParam, String szStrValue);
    
    public void OnNotifyMsg(int dwNotifyMsg, int wParam, int lParam)
    {
    	switch(dwNotifyMsg)
    	{
		case AnyChatDefine.WM_GV_CONNECT:			
			if(baseEvent != null)
				baseEvent.OnAnyChatConnectMessage(wParam>=1?true:false);
			break;
		case AnyChatDefine.WM_GV_LOGINSYSTEM:
			if(baseEvent != null)
				baseEvent.OnAnyChatLoginMessage(wParam,lParam);
			break;
		case AnyChatDefine.WM_GV_ENTERROOM:
			if(baseEvent != null)
				baseEvent.OnAnyChatEnterRoomMessage(wParam, lParam);
			break;
		case AnyChatDefine.WM_GV_USERATROOM:
			if(baseEvent != null)
				baseEvent.OnAnyChatUserAtRoomMessage(wParam,lParam>=1?true:false);
			break;
		case AnyChatDefine.WM_GV_LINKCLOSE:
			if(baseEvent != null)
				baseEvent.OnAnyChatLinkCloseMessage(lParam);
			break;
		case AnyChatDefine.WM_GV_ONLINEUSER:
			if(baseEvent != null)
				baseEvent.OnAnyChatOnlineUserMessage(wParam,lParam);
			break;
		case AnyChatDefine.WM_GV_MICSTATECHANGE:
			if(stateChgEvent != null)
				stateChgEvent.OnAnyChatMicStateChgMessage(wParam,lParam==0?false:true);
			break;			
		case AnyChatDefine.WM_GV_CAMERASTATE:
			if(stateChgEvent != null)
				stateChgEvent.OnAnyChatCameraStateChgMessage(wParam,lParam);
			break;
		case AnyChatDefine.WM_GV_CHATMODECHG:
			if(stateChgEvent != null)
				stateChgEvent.OnAnyChatChatModeChgMessage(wParam,lParam==0?true:false);
			break;
		case AnyChatDefine.WM_GV_ACTIVESTATE:
			if(stateChgEvent != null)
				stateChgEvent.OnAnyChatActiveStateChgMessage(wParam,lParam);
			break;
		case AnyChatDefine.WM_GV_P2PCONNECTSTATE:
			if(stateChgEvent != null)
				stateChgEvent.OnAnyChatP2PConnectStateMessage(wParam,lParam);
			break;
		case AnyChatDefine.WM_GV_VIDEOSIZECHG:
//			OnAnyChatVideoSizeChgMessage(wParam, LOWORD(lParam), HIWORD(lParam));
			break;
		case AnyChatDefine.WM_GV_PRIVATEREQUEST:
			if(privateChatEvent != null)
				privateChatEvent.OnAnyChatPrivateRequestMessage(wParam,lParam);
			break;
		case AnyChatDefine.WM_GV_PRIVATEECHO:
			if(privateChatEvent != null)
				privateChatEvent.OnAnyChatPrivateEchoMessage(wParam,lParam);
			break;
		case AnyChatDefine.WM_GV_PRIVATEEXIT:
			if(privateChatEvent != null)
				privateChatEvent.OnAnyChatPrivateExitMessage(wParam,lParam);
			break;
		case AnyChatDefine.WM_GV_USERINFOUPDATE:
			if(userInfoEvent != null)
				userInfoEvent.OnAnyChatUserInfoUpdate(wParam, lParam);
			break;
		case AnyChatDefine.WM_GV_FRIENDSTATUS:
			if(userInfoEvent != null)
				userInfoEvent.OnAnyChatFriendStatus(wParam, lParam);
			break;
		case AnyChatDefine.WM_GV_AUDIOPLAYCTRL:
			if(wParam == 1) {
				if(mAudioHelper == null)
					mAudioHelper = new AnyChatAudioHelper();
				mAudioHelper.InitAudioPlayer(lParam);
			}
			else {
				if(mAudioHelper != null)
					mAudioHelper.ReleaseAudioPlayer();
			}
			break;
		case AnyChatDefine.WM_GV_AUDIORECCTRL:
			if(wParam == 1) {
				if(mAudioHelper == null)
					mAudioHelper = new AnyChatAudioHelper();
				mAudioHelper.InitAudioRecorder(lParam);
			}
			else {
				if(mAudioHelper != null)
					mAudioHelper.ReleaseAudioRecorder();
			}
			break;
		case AnyChatDefine.WM_GV_VIDEOCAPCTRL:
			mCameraHelper.CaptureControl(wParam==0 ? false : true);
			break;
		default:
			break;
		}
    }
  
    static class MainHandler extends Handler
    {
    	WeakReference<AnyChatCoreSDK> mAnyChat;
    	
    	
         public MainHandler(AnyChatCoreSDK anychat){
        	 mAnyChat = new WeakReference<AnyChatCoreSDK>(anychat);
         }
         public MainHandler(Looper L)
         {
             super(L);
         }
         public void handleMessage(Message nMsg)
         {
        	 AnyChatCoreSDK anychat = mAnyChat.get();
        	 if(anychat == null)
        		 return;
             super.handleMessage(nMsg);
             Bundle tBundle=nMsg.getData();
             int type = tBundle.getInt("HANDLETYPE");
             if(type == HANDLE_TYPE_NOTIFYMSG)
             {
            	 int msg = tBundle.getInt("MSG");
            	 int wParam=tBundle.getInt("WPARAM");
            	 int lParam=tBundle.getInt("LPARAM");
            	 anychat.OnNotifyMsg(msg,wParam,lParam);
             }
             else if(type == HANDLE_TYPE_TEXTMSG)
             {
            	 int fromid = tBundle.getInt("FROMUSERID");
                 int toid = tBundle.getInt("TOUSERID");
                 int secret = tBundle.getInt("SECRET");
                 String message = tBundle.getString("MESSAGE");
                 if(anychat.textMsgEvent != null)
                	 anychat.textMsgEvent.OnAnyChatTextMessage(fromid, toid, secret!=0?true:false, message);
             }
             else if(type == HANDLE_TYPE_TRANSFILE)
             {
                 int userid = tBundle.getInt("USERID");
                 String filename = tBundle.getString("FILENAME");
                 String tempfile = tBundle.getString("TEMPFILE");
                 int length = tBundle.getInt("LENGTH");
                 int wparam = tBundle.getInt("WPARAM");
                 int lparam = tBundle.getInt("LPARAM");
                 int taskid = tBundle.getInt("TASKID");
                 if(anychat.transDataEvent != null)
                	 anychat.transDataEvent.OnAnyChatTransFile(userid, filename, tempfile, length, wparam, lparam, taskid);
             }
             else if(type == HANDLE_TYPE_TRANSBUF)
             {
            	 int userid = tBundle.getInt("USERID");
            	 int length = tBundle.getInt("LENGTH");
            	 byte[] buf = tBundle.getByteArray("BUF");
            	 if(anychat.transDataEvent != null)
            		 anychat.transDataEvent.OnAnyChatTransBuffer(userid, buf, length);
             }
             else if(type == HANDLE_TYPE_TRANSBUFEX)
             {
            	 int userid = tBundle.getInt("USERID");
            	 int length = tBundle.getInt("LENGTH");
            	 byte[] buf = tBundle.getByteArray("BUF");
            	 int wparam = tBundle.getInt("WPARAM");
            	 int lparam = tBundle.getInt("LPARAM");
            	 int taskid = tBundle.getInt("TASKID");
            	 if(anychat.transDataEvent != null)
            		 anychat.transDataEvent.OnAnyChatTransBufferEx(userid, buf, length, wparam, lparam, taskid);
             }
             else if(type == HANDLE_TYPE_SDKFILTER)
             {
            	 int length = tBundle.getInt("LENGTH");
            	 byte[] buf = tBundle.getByteArray("BUF");
            	 if(anychat.transDataEvent != null)
            		 anychat.transDataEvent.OnAnyChatSDKFilterData(buf, length); 
             }
             else if(type == HANDLE_TYPE_VIDEOCALL)
             {
            	 int dwEventType = tBundle.getInt("EVENTTYPE");
            	 int dwUserId = tBundle.getInt("USERID");
            	 int dwErrorCode = tBundle.getInt("ERRORCODE");
            	 int dwFlags = tBundle.getInt("FLAGS");
            	 int dwParam = tBundle.getInt("PARAM");
            	 String userStr = tBundle.getString("USERSTR");
            	 if(anychat.videoCallEvent != null)
            		 anychat.videoCallEvent.OnAnyChatVideoCallEvent(dwEventType, dwUserId, dwErrorCode, dwFlags, dwParam, userStr);
             }
        }
     }
   
   private void OnAnyChatNotifyMsg(int dwNotifyMsg, int wParam, int lParam)
    {
    	Message tMsg=new Message();
        Bundle tBundle=new Bundle();
        tBundle.putInt("HANDLETYPE", HANDLE_TYPE_NOTIFYMSG);       
        tBundle.putInt("MSG", dwNotifyMsg);
        tBundle.putInt("WPARAM", wParam);
        tBundle.putInt("LPARAM", lParam);
        tMsg.setData(tBundle);
        mHandler.sendMessage(tMsg);
    }
    private void OnTextMessageCallBack(int dwFromUserid, int dwToUserid, int bSecret, String message)
    {
    	Message tMsg=new Message();
        Bundle tBundle=new Bundle();
        tBundle.putInt("HANDLETYPE", HANDLE_TYPE_TEXTMSG);       
        tBundle.putInt("FROMUSERID", dwFromUserid);
        tBundle.putInt("TOUSERID", dwToUserid);
        tBundle.putInt("SECRET", bSecret);
        tBundle.putString("MESSAGE", message);
        tMsg.setData(tBundle);
        mHandler.sendMessage(tMsg);
    }
	private void OnTransFileCallBack(int userid, String filename, String tempfilepath, int filelength, int wparam, int lparam, int taskid)
    {
    	Message tMsg=new Message();
        Bundle tBundle=new Bundle();
        tBundle.putInt("HANDLETYPE", HANDLE_TYPE_TRANSFILE);       
        tBundle.putInt("USERID", userid);
        tBundle.putString("FILENAME", filename);
        tBundle.putString("TEMPFILE", tempfilepath);
        tBundle.putInt("LENGTH", filelength);
        tBundle.putInt("WPARAM", wparam);
        tBundle.putInt("LPARAM", lparam);
        tBundle.putInt("TASKID", taskid);
        tMsg.setData(tBundle);
        mHandler.sendMessage(tMsg);
    }
    private void OnTransBufferCallBack(int userid, byte[] buf, int len)
    {
    	Message tMsg=new Message();
        Bundle tBundle=new Bundle();
        tBundle.putInt("HANDLETYPE", HANDLE_TYPE_TRANSBUF);       
        tBundle.putInt("USERID", userid);
        tBundle.putByteArray("BUF", buf);
        tBundle.putInt("LENGTH", len);
         tMsg.setData(tBundle);
        mHandler.sendMessage(tMsg);
    }
   private void OnTransBufferExCallBack(int userid, byte[] buf, int len, int wparam, int lparam, int taskid)
    {
    	Message tMsg=new Message();
        Bundle tBundle=new Bundle();
        tBundle.putInt("HANDLETYPE", HANDLE_TYPE_TRANSBUFEX);       
        tBundle.putInt("USERID", userid);
        tBundle.putByteArray("BUF", buf);
        tBundle.putInt("LENGTH", len);
        tBundle.putInt("WPARAM", wparam);
        tBundle.putInt("LPARAM", lparam);
        tBundle.putInt("TASKID", taskid);
         tMsg.setData(tBundle);
        mHandler.sendMessage(tMsg);
    }
	private void OnSDKFilterDataCallBack(byte[] buf, int len)
    {
    	Message tMsg=new Message();
        Bundle tBundle=new Bundle();
        tBundle.putInt("HANDLETYPE", HANDLE_TYPE_SDKFILTER);       
        tBundle.putByteArray("BUF", buf);
        tBundle.putInt("LENGTH", len);
         tMsg.setData(tBundle);
        mHandler.sendMessage(tMsg);
    }
	
	private void OnVideoDataCallBack(int userid, byte[] buf, int len, int width, int height)
	{
		mVideoHelper.SetVideoFmt(userid, width, height);
		int degree = QueryUserStateInt(userid, AnyChatDefine.BRAC_USERSTATE_VIDEOROTATION);
		int mirror = QueryUserStateInt(userid, AnyChatDefine.BRAC_USERSTATE_VIDEOMIRRORED);
		mVideoHelper.ShowVideo(userid, buf, degree, mirror);
	}
	
	private void OnVideoCallEventCallBack(int eventtype, int userid, int errorcode, int flags, int param, String userStr)
	{
		Message tMsg=new Message();
        Bundle tBundle=new Bundle();
        tBundle.putInt("HANDLETYPE", HANDLE_TYPE_VIDEOCALL);
        tBundle.putInt("EVENTTYPE", eventtype);
        tBundle.putInt("USERID", userid);
        tBundle.putInt("ERRORCODE", errorcode);
        tBundle.putInt("FLAGS", flags);
        tBundle.putInt("PARAM", param);
        tBundle.putString("USERSTR", userStr);
        tMsg.setData(tBundle);
        mHandler.sendMessage(tMsg);
	}
	
    static {
    	System.loadLibrary("anychatcore");
    }
    
}

