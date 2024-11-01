package org.example;

import java.io.IOException;
import java.util.Scanner;

public class TaskManager {
    private final User user;

    public TaskManager(User user) {
        this.user = user;
    }

    public void start() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the name of the file: ");
        String fileName = sc.nextLine();

        this.user.taskList = JsonProcessing.loadFromJSON(fileName);

        while (true) {
            listMenu();
            switch (sc.nextLine()) {
                case "1" -> {
                    System.out.print("Enter name of the task: ");
                    String name = sc.nextLine();
                    System.out.print("Enter description of the task: ");
                    String description = sc.nextLine();
                    this.addTask(new Task(name, description));
                    System.out.println("""
                            -----------------------------
                            | Task was successfully added |
                            -----------------------------
                            """);
                }
                case "2" -> {
                    System.out.print("Enter task ID to remove: ");
                    this.removeTask(sc.nextInt());
                    sc.nextLine(); // Чтобы "съесть" символ новой строки
                    System.out.println("""
                            -------------------------------
                            | Task was successfully removed |
                            -------------------------------
                            """);
                }
                case "3" -> {
                    System.out.println("\nList of your tasks:");
                    this.printTaskList();
                }
                case "4" -> {
                    System.out.println("""
                            -------------------
                            | End of the work |
                            -------------------
                            """);
                    JsonProcessing.saveToJSON(this.user.taskList, fileName);
                    return;
                }
                default -> System.out.println("""
                        -------------
                        | WRONG INPUT |
                        -------------
                        """);
            }
        }
    }

    public void listMenu() {
        System.out.print("""
                -----------------------------
                |       Menu Options        |
                -----------------------------
                | 1. Add Task               |
                | 2. Delete Task            |
                | 3. List Your Tasks        |
                | 4. Exit                   |
                -----------------------------
                Enter your choice:\s""");
    }

    public void addTask(Task task) {
        this.user.taskList.add(task);
    }

    public void removeTask(int id) {
        for (int i = 0; i < this.user.taskList.size(); i++) {
            if (this.user.taskList.get(i).getId().equals(id)) {
                this.user.taskList.remove(i);
                break;
            }
        }
    }

    public void printTaskList() {
        if (this.user.taskList.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            for (Task task : this.user.taskList) {
                System.out.println(task);
            }
        }
    }
}