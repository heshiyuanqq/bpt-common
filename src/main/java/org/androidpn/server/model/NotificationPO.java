/**
 * 
 */
package org.androidpn.server.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chengqiang.liu
 *	通知内容实体
 */

public class NotificationPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int intId;// 自生成ID
	private String strFromUser;// 发送人
	private String strToUser;// 接收人
	private String strContent;// 消息内容(文件原名称或者文本消息内容)
	private String filePath; // 文件存储路径
	private String strContentType;// 消息类型
	private Date dateInsertTime;// 发送时间
	private String strReaded;// 已读状态
	private String strIsGroupInformation;//是否是群发消息
	private String apiKey;
	private String uri;

	public NotificationPO(){
	}
	
	public NotificationPO(String apiKey,String title,String message,String uri){
		this.apiKey = apiKey;
		this.strFromUser = title;
		this.strContent = message;
		this.uri = uri;
	}

	public int getIntId() {
		return intId;
	}

	public void setIntId(int intId) {
		this.intId = intId;
	}

	public String getStrFromUser() {
		return strFromUser;
	}

	public void setStrFromUser(String strFromUser) {
		this.strFromUser = strFromUser;
	}

	public String getStrToUser() {
		return strToUser;
	}

	public void setStrToUser(String strToUser) {
		this.strToUser = strToUser;
	}

	public String getStrContent() {
		return strContent;
	}

	public void setStrContent(String strContent) {
		this.strContent = strContent;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getStrContentType() {
		return strContentType;
	}

	public void setStrContentType(String strContentType) {
		this.strContentType = strContentType;
	}

	public Date getDateInsertTime() {
		return dateInsertTime;
	}

	public void setDateInsertTime(Date dateInsertTime) {
		this.dateInsertTime = dateInsertTime;
	}

	public String getStrReaded() {
		return strReaded;
	}

	public void setStrReaded(String strReaded) {
		this.strReaded = strReaded;
	}

	public String getStrIsGroupInformation() {
		return strIsGroupInformation;
	}

	public void setStrIsGroupInformation(String strIsGroupInformation) {
		this.strIsGroupInformation = strIsGroupInformation;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	
	
	/**
	private static final long serialVersionUID = 1845362556725768545L;
	public static final String STATUS_NOT_SEND = "0";
	public static final String STATUS_SEND = "1";
	public static final String STATUS_RECEIVE = "2";
	public static final String STATUS_READ = "3";
    private Long id;
    private String username;
    private String clientIp;
    private String resource;
    private String messageId;  
    private String apiKey;
    private String title;
    private String message; 
    private String uri;    
    private String status;  
    private Timestamp createTime ;   
    private Timestamp updateTime ;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	**/
}
