/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myjastip.payment;

/**
 *
 * @author ACER
 */
public class Notification {
    private String notificationId;
    private String title;
    private String messageBody;
    private boolean isRead;
    
    public void sendPushNotification(String userId){
        System.out.println("=== NOTIFIKASI ===");
        System.out.println("Dikirim ke User : " + userId);
        System.out.println("Judul : " + title);
        System.out.println("Pesan : " + messageBody);
    }
    
    public void markAsRead(){
        isRead = true;

        System.out.println("Notifikasi " + notificationId + " telah dibaca.");
    }
}
