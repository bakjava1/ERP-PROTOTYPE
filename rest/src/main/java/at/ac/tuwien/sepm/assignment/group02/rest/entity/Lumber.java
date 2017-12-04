package at.ac.tuwien.sepm.assignment.group02.rest.entity;

public class Lumber {
    private int id;
    private String content;

    /**
     * Default constructor
     */
    public Lumber() {
    }

    /**
     * Constructor with parameter, initializes with instance variable
     * @param id
     * @param content
     */
    public Lumber(int id, String content) {
        this.id = id;
        this.content = content;
    }

    /**
     * Method to retrieve the id
     * @return a value of id to caller
     */
    public int getId() {

        return id;
    }

    /**
     * Methode to set the id
     * @param id
     */
    public void setId(int id) {

        this.id = id;  // store the id
    }

    /**
     * Method to retrieve the content
     * @return a value of content to caller
     */
    public String getContent() {

        return content;
    }

    /**
     * Method to set a content
     * @param content
     */
    public void setContent(String content)
    {
        this.content = content; //store the content
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "LumberDTO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
