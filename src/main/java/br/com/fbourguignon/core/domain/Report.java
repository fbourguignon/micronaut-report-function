package br.com.fbourguignon.core.domain;

import io.micronaut.core.annotation.Introspected;

@Introspected
public abstract class Report {

    private String fileName;
    private byte[] fileData;
    private String emailTo;
    private String emailReply;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailReply() {
        return emailReply;
    }

    public void setEmailReply(String emailReply) {
        this.emailReply = emailReply;
    }
}
