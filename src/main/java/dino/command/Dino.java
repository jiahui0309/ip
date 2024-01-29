package dino.command;

import java.util.Scanner;

public class Dino {
    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    public enum TaskType {
        TODO,
        DEADLINE,
        EVENT
    }

    private static final String FILE_PATH = "./data/duke.txt";

    public Dino(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = storage.loadTasksFromFile();

    }

    public void run() {
        ui.welcome();

        Scanner sc = new Scanner(System.in);
        Parser parser = new Parser(tasks, ui, sc);

        while (true) {
            String command = sc.next();
            if (command.equals("bye")) {
                storage.saveTasksToFile(tasks.getTaskList());
                ui.goodbye();
                sc.close();
                break;
            }
            parser.parseCommand(command);
        }
    }

    public static void main(String[] args) {
        new Dino(FILE_PATH).run();
    }
}