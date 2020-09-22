package edu.escuelaing.arep.entities;

public class Code {


    private String content;

    private String date;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Code(String code, String date){
        this.content = code;
        this.date = date;
    }

    public Code(){

    }

    @Override
    public String toString() {
        return content + " at:  " + date;
    }
}
