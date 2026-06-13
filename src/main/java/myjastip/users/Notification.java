package myjastip.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Notification {

    private String notificationId;
    private String title;
    private String messageBody;
    private boolean isRead;
    private ArrayList<String> notificationHistory;
    private HashMap<String, Boolean> readStatus;

    public Notification() {
        this.notificationHistory = new ArrayList<>();
        this.readStatus = new HashMap<>();
    }

    public Notification(String notificationId,
                        String title,
                        String messageBody,
                        boolean isRead) {

        this.notificationId = notificationId;
        this.title = title;
        this.messageBody = messageBody;
        this.isRead = isRead;

        this.notificationHistory = new ArrayList<>();
        this.readStatus = new HashMap<>();
    }

    public void sendPushNotification(String userId) {

        try {

            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Judul notifikasi tidak boleh kosong");
            }

            if (messageBody == null || messageBody.trim().isEmpty()) {
                throw new IllegalArgumentException("Isi notifikasi tidak boleh kosong");
            }

            notificationHistory.add("User: " + userId + " | Judul: " + title);

            readStatus.put(notificationId, false);

            System.out.println("Notifikasi berhasil dikirim.");

        } catch (IllegalArgumentException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    public void markAsRead() {

        try {

            if (notificationId == null || notificationId.trim().isEmpty()) {

                throw new IllegalArgumentException("Notification ID tidak valid");
            }

            isRead = true;
            readStatus.put(notificationId, true);

            System.out.println("Notifikasi telah dibaca.");

        } catch (IllegalArgumentException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    public void showNotificationHistory() {

        for (String history : notificationHistory) {
            System.out.println(history);
        }
    }

    public void showReadStatus() {

        for (Map.Entry<String, Boolean> entry
                : readStatus.entrySet()) {

            System.out.println("Notification ID: " + entry.getKey() + " | Dibaca: " + entry.getValue());
        }
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        try {

            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Judul tidak boleh kosong");
            }

            this.title = title;

        } catch (IllegalArgumentException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {

        try {

            if (messageBody == null || messageBody.trim().isEmpty()) {

                throw new IllegalArgumentException("Isi pesan tidak boleh kosong");
            }

            this.messageBody = messageBody;

        } catch (IllegalArgumentException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean isRead() {
        return isRead;
    }
}