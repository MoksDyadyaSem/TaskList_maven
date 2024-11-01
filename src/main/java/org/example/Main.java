package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        User user = new User();
        TaskManager taskManager = new TaskManager(user);
        taskManager.start();
    }
}
