package com.example.adrianaalexandru.retrofitex;

import java.util.Date;

/**
 * Created by Adriana on 14/09/2016.
 */
public class Task  {
    private int id;
    private String title;
    private String description;
    private String asignee;
    private Date dueDate;


    public Task(){}
    public Task(String title, String description, String asignee, Date dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.asignee = asignee;
        this.dueDate = dueDate;
    }

    public Task(int id, String title, String description, String asignee, Date dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.asignee = asignee;
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAsignee() {
        return asignee;
    }

    public void setAsignee(String asignee) {
        this.asignee = asignee;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return " Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", asignee='" + asignee + '\'' +
                ", dueDate=" + Constants.SIMPLE_DATE_FORMAT.format(dueDate) +
                '}';
    }
}
