package model;

// represents 1 row of the DataTable in studentTC.feature

public class StudentTC {

    private int id;
    private String name;
    private int score;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}