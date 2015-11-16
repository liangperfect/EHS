package com.bairuitech.anychat;


public interface AnyChatBaseEvent {

    public void OnAnyChatConnectMessage(boolean bSuccess);
    public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode);
    public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode);
    public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId);
    public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter); 
    public void OnAnyChatLinkCloseMessage(int dwErrorCode);	
	
}
