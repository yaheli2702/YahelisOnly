package com.example.yahelis;

public class comments {
    private String OtherUserName;// מי שכותבים עליו, מייל
    private String ThisUserName;// אני כותבת
    private String Comment;

    public comments(String otherUserName, String thisUserName, String comment) {
        OtherUserName = otherUserName;
        ThisUserName = thisUserName;
        Comment = comment;
    }
    public comments(){

    }

    public String getOtherUserName() {
        return OtherUserName;
    }

    public void setOtherUserName(String otherUserName) {
        OtherUserName = otherUserName;
    }

    public String getThisUserName() {
        return ThisUserName;
    }

    public void setThisUserName(String thisUserName) {
        ThisUserName = thisUserName;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }


    public String toString()
    {

        return ThisUserName + " : " + Comment;
    }
}
