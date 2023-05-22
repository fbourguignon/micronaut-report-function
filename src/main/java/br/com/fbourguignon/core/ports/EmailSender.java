package br.com.fbourguignon.core.ports;

public interface EmailSender {
    void sendEmail(String emailTo, String emailReply,String subject, String body, byte[] attachment, String filename);
}
