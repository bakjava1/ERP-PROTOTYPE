package at.ac.tuwien.sepm.assignment.group02.server.dao;

//TODO access these DAO from Server and Client??

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Lumber {
    private int id;
    private String content;

    public Lumber(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Lumber{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
