package com.doubleclick.marktinhome.Model;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created By Eslam Ghazy on 3/17/2022
 */
@Entity
public class Chat implements Serializable {


    @PrimaryKey()
    @NonNull
    private String id;
    @NonNull
    private String message;
    @NonNull
    private String type;
    @NonNull
    private String sender;
    @NonNull
    private String receiver;
    private long date;
    @NonNull
    private String StatusMessage;
    private boolean seen;
    @NonNull
    private String uri;

    public Chat() {
        uri = "";
        id = "";
        message = "";
        type = "";
        sender = "";
        seen = false;
        receiver = "";
        date = 0;
        StatusMessage = "";
    }

    public Chat(String message, String uri, String type, String sender, String receiver, long date, String id, String statusMessage, boolean seen) {
        this.message = message;
        this.uri = uri;
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.id = id;
        StatusMessage = statusMessage;
        this.seen = seen;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        StatusMessage = statusMessage;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", date=" + date +
                ", StatusMessage='" + StatusMessage + '\'' +
                ", seen=" + seen +
                ", uri='" + uri + '\'' +
                '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Chat)) return false;
        Chat chat = (Chat) obj;
        return chat.getId().equals(this.getId());
    }
}
