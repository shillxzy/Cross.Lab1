package com.skalsky;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main
{
    public static List<Task> tasks = new ArrayList<>();
    public static int taskCounter = 1;
    public static String FILE_NAME = "tasks.json";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    public static void main(String[] args)
    {
       // readTasksFromFile();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n\u001B[34m--- To-Do List Menu ---\u001B[0m");
            System.out.println("\u001B[32m-----------------------------\u001B[0m");
            System.out.println("\u001B[33m1. Create Task\u001B[0m");
            System.out.println("\u001B[33m2. Delete Task\u001B[0m");
            System.out.println("\u001B[33m3. View Tasks\u001B[0m");
            System.out.println("\u001B[33m4. Update Task\u001B[0m");
            System.out.println("\u001B[33m5. Search Task\u001B[0m");
            System.out.println("\u001B[33m6. Sort Tasks by Due Date\u001B[0m");
            System.out.println("\u001B[33m7. Sort Tasks by Status\u001B[0m");
            System.out.println("\u001B[33m8. Save\u001B[0m");
            System.out.println("\u001B[33mexit. Exit Application\u001B[0m");
            System.out.println("\u001B[32m-----------------------------\u001B[0m");
            System.out.print("\u001B[36mEnter choice: \u001B[0m");

            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                    createTask(scanner);
                    break;
                case "2":
                    deleteTask(scanner);
                    break;
                case "3":
                    readTasksFromFile();
                    viewTasks();
                    break;
                case "4":
                    updateTask(scanner);
                    break;
                case "5":
                    searchTask(scanner);
                    break;
                case "6":
                    Sort.sortTasksByDueDate(tasks);
                    viewTasks();
                    break;
                case "7":
                    Sort.sortTasksByStatus(tasks);
                    viewTasks();
                    break;
                case "8":
                    saveTasksToFile();
                    break;
                case "exit":
                    System.out.println("\u001B[31mApplication Closed.\u001B[0m");
                    running = false;
                    break;
                default:
                    System.out.println("\u001B[31mInvalid option! Try again.\u001B[0m");
                    break;
            }
        }
        scanner.close();
    }

    // Створення нового завдання
    private static void createTask(Scanner scanner) {
        System.out.print("\n\u001B[36mEnter Task Title: \u001B[0m");
        String title = scanner.nextLine();
        System.out.print("\u001B[36mEnter Task Details: \u001B[0m");
        String description = scanner.nextLine();
        System.out.print("\u001B[36mEnter Task Due Date (DD-MM-YYYY): \u001B[0m");
        String dateString = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dueDate = LocalDate.parse(dateString, formatter);

        System.out.print("\u001B[36mEnter Task Status (done / not done): \u001B[0m");
        String status = scanner.nextLine().trim().toLowerCase();

        Task newTask = new Task(taskCounter++, title, description, dueDate, status);
        tasks.add(newTask);
        System.out.println("\u001B[32mTask Created: " + newTask + "\u001B[0m");
    }

    // Видалення завдання
    private static void deleteTask(Scanner scanner) {
        System.out.print("\n\u001B[36mEnter Task ID to Delete: \u001B[0m");
        int id = Integer.parseInt(scanner.nextLine());
        boolean removed = tasks.removeIf(task -> task.id == id);
        if (removed) {
            System.out.println("\u001B[32mTask with ID " + id + " deleted.\u001B[0m");
        } else {
            System.out.println("\u001B[31mTask with ID " + id + " not found.\u001B[0m");
        }
    }

    // Перегляд всіх завдань
    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\n\u001B[31mNo tasks available.\u001B[0m");
        } else {
            System.out.println("\n\u001B[34m--- Task List ---\u001B[0m");
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    // Оновлення завдання
    private static void updateTask(Scanner scanner) {
        System.out.print("\n\u001B[36mEnter Task ID to Update: \u001B[0m");
        int id = Integer.parseInt(scanner.nextLine());
        boolean found = false;

        for (Task task : tasks) {
            if (task.id == id) {
                System.out.print("\u001B[36mEnter new Title: \u001B[0m");
                task.title = scanner.nextLine();
                System.out.print("\u001B[36mEnter new Description: \u001B[0m");
                task.description = scanner.nextLine();
                System.out.print("\u001B[36mEnter new Due Date (DD-MM-YYYY): \u001B[0m");
                String dateString = scanner.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                System.out.print("\u001B[36mEnter new Status (done / not done): \u001B[0m");
                task.status = scanner.nextLine().trim().toLowerCase();

                System.out.println("\u001B[32mTask Updated: " + task + "\u001B[0m");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("\u001B[31mTask with ID " + id + " not found.\u001B[0m");
        }
    }

    // Пошук завдання
    private static void searchTask(Scanner scanner) {
        System.out.print("\n\u001B[36mEnter Task Title to Search: \u001B[0m");
        String title = scanner.nextLine();
        boolean found = false;

        for (Task task : tasks) {
            if (task.title.toLowerCase().contains(title.toLowerCase())) {
                System.out.println(task);
                found = true;
            }
        }
        if (!found) {
            System.out.println("\u001B[31mNo tasks found with that title.\u001B[0m");
        }
    }


    private static void saveTasksToFile()
    {
        try (Writer writer = new FileWriter(FILE_NAME))
        {
            var gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            gson.toJson(tasks, writer);
            System.out.println("✅ Tasks saved to " + FILE_NAME);
        } catch (IOException e)
        {
            System.out.println("❌ Error saving tasks: " + e.getMessage());
        }
    }

    private static void readTasksFromFile()
    {
        try (Reader reader = new FileReader("tasks.json"))
        {
            var gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            Type taskListType = new TypeToken<List<Task>>() {}.getType();
            tasks = gson.fromJson(reader, taskListType);
            System.out.println("✅ Synchronized.");

        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }



}





