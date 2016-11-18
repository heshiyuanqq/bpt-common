/**
 * 
 */
package org.androidpn.server.service;

import java.sql.SQLException;
import java.util.List;

import org.androidpn.server.model.NotificationPO;

/**
 * @author chengqiang.liu
 *
 */
public interface NotificationService {	
	public void saveNotification(NotificationPO notificationPO) throws SQLException;
	public void updateNotification(NotificationPO notificationPO) throws SQLException;
	public NotificationPO queryNotificationById(Long id) throws SQLException;	
	public void createNotifications(List<NotificationPO> notificationPOs) throws SQLException;	
	public NotificationPO queryNotificationByUserName(String userName,String messageId) throws SQLException;	
	public List<NotificationPO> queryNotification(String username) throws SQLException;
	
	public Integer queryNotificationInfoCount(String username) throws SQLException;
	public Integer queryNotificationBusInfoCount(String username) throws SQLException;
	
}
