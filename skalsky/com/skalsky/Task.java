package com.skalsky;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task
{
    int id;
    String title;
    String description;
    LocalDate dueDate;
    String status;

    public Task(int id, String title, String description, LocalDate dueDate, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Task() {}

    @Override
    public String toString() {
        return id + ". " + title + " (" + dueDate + ") - " + status;
    }
}
