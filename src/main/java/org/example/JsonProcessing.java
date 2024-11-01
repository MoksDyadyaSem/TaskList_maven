package org.example;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JsonProcessing {
    /*public static void saveToJSON(List<Task> list, String fileName) throws IOException {
        String directoryPath = "C:\\Users\\Maxim\\IdeaProjects\\MavenTest\\src\\main\\resources\\";
        try (FileWriter fileWriter = new FileWriter(directoryPath + fileName)) {
            fileWriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(list));
        }
    }*/

    public static void saveToJSON(List<Task> list, String fileName) throws IOException {
        String directoryPath = "C:\\Users\\Maxim\\IdeaProjects\\MavenTest\\src\\main\\resources\\";
        File file = new File(directoryPath + fileName);

        List<Task> tasksToSave;

        // Загружаем существующие задачи, если файл уже существует и не пустой
        if (file.exists() && file.length() > 0) {
            tasksToSave = loadFromJSON(fileName);
            if (tasksToSave == null) {
                tasksToSave = new ArrayList<>();
            }
            tasksToSave.addAll(list);  // Добавляем новые задачи к загруженным
        } else {
            tasksToSave = list;  // Если файла нет, используем текущий список задач
        }

        try (FileWriter fileWriter = new FileWriter(file, false)) {
            fileWriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(tasksToSave));
        }
    }


    public static List<Task> loadFromJSON(String fileName) throws IOException {
        List<Task> tasks = new ArrayList<>();

        File file = new File("src/main/resources/" + fileName);
        if (!file.exists() || file.length() == 0) {
            System.out.println("Создаем файл по адресу " + file.getAbsolutePath());
            file.createNewFile();
            Task.setCount(0);
            return tasks;
        }

        int maxID = Integer.MIN_VALUE;
        Scanner scanner = new Scanner(file);
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine() + '\n');
        }
        List<Task> test = new Gson().fromJson(stringBuilder.toString(), new TypeToken<List<Task>>() {
        }.getType());
        for (Task task : test) {
            if (task.getId() > maxID) {
                maxID = task.getId();
            }
        }
        Task.setCount(maxID);
        return test;
    }
}
