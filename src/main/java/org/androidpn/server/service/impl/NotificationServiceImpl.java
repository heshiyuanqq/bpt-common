/**
 * 
 */
package org.androidpn.server.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.androidpn.server.model.NotificationPO;
import org.androidpn.server.service.NotificationService;

public class NotificationServiceImpl implements NotificationService {

	@Override
	public void saveNotification(NotificationPO notificationPO)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNotification(NotificationPO notificationPO)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public NotificationPO queryNotificationById(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createNotifications(List<NotificationPO> notificationPOs)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public NotificationPO queryNotificationByUserName(String userName,
			String messageId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NotificationPO> queryNotification(String username)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer queryNotificationInfoCount(String username)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer queryNotificationBusInfoCount(String username)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
