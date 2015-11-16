package com.bairuitech.anychat;

public interface AnyChatPrivateChatEvent {
    public void OnAnyChatPrivateRequestMessage(int dwUserId, int dwRequestId);
    public void OnAnyChatPrivateEchoMessage(int dwUserId, int dwErrorCode);
    public void OnAnyChatPrivateExitMessage(int dwUserId, int dwErrorCode);	
}