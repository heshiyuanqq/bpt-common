package org.androidpn.server.xmpp.push;

import java.sql.SQLException;

import org.androidpn.server.model.NotificationPO;
import org.xmpp.packet.IQ;


/**
 * This class is to manage sending the notifcations to the users.
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public interface AbstractNotificationManager {
	public void sendBroadcast(String apiKey, String title, String message,String uri);

	public void sendAllBroadcast(String apiKey, String title, String message,String uri)throws SQLException;
	public void sendNotifcationToUser(String apiKey, String username,String title, String message, String uri, IQ notificationIQ);
	
	public void sendNotifcationToUser(String username,IQ notificationIQ);
	public IQ createNotificationIQ(String apiKey, String title,String message, String uri);

	public void sendNotifications(String apiKey, String username, String title,String message, String uri);

	public void sendOfflineNotification(NotificationPO notificationMO);
}
