Global.java中:
private static @ChatType int chatType;
/**
 * 设置聊天类型
 * @param chatType 0:音频 1:视频 2:约会
 */
public static void setChatType(@ChatType int chatType) {
	Global.chatType = chatType;
}
/**
 * 获取拒绝上麦类型申请, 拒绝申请
 */
public static String getRefuseTypeApply() {
	switch (chatType) {
		case ChatType.ChatType_AUDIO:
			return AUDIO_REFUSE_APPLY;
		case ChatType.ChatType_VIDEO:
			return VIDEO_REFUSE_APPLY;
		default:
			return DATE_REFUSE_APPLY;
	}
}



Activity中:
Global.setChatType(ChatType.ChatType_AUDIO);//设置值, 可设置其它值
Global.getRefuseTypeApply();//获取值, 可设置其它值
