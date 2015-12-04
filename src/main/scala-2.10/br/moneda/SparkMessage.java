package br.moneda;

import java.util.ArrayList;

/**
 * Created by philippe on 02/12/15.
 */
public class SparkMessage {
    private String objectName;
    private int messageSize;
    private ArrayList<BlogMessage> messages;

    public ArrayList<BlogMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<BlogMessage> messages) {
        this.messages = messages;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public int getMessageSize() {
        return messageSize;
    }

    public void setMessageSize(int messageSize) {
        this.messageSize = messageSize;
    }

    @Override
    public String toString() {
        return "SparkMessage{" +
                "objectName='" + objectName + '\'' +
                ", messageSize=" + messageSize +
                ", messages=" + messages +
                '}';
    }
}
