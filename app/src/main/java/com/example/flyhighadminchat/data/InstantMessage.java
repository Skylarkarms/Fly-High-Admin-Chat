package com.example.flyhighadminchat.data;

import com.google.firebase.database.Exclude;

public class InstantMessage {

    private String message;
    private String author;
    private Object sent;
    private Object seen;


    public InstantMessage(String message, String author, Object sent, Object seen) {
        this.message = message;
        this.author = author;
        this.sent = sent;
        this.seen = seen;
    }

    public InstantMessage() {
    }

//    public InstantMessage(String message, String author) {
//        this.message = message;
//        this.author = author;
//    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public Object getSent() {
        return sent;
    }

    public Object getSeen() {
        return seen;
    }

    @Exclude
    public long getSentLong(){
        return (long)sent;
    }

    @Exclude
    public long getSeenLong(){
        return (long)seen;
    }
}
