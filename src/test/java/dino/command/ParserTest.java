package dino.command;

import dino.task.Task;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {

    @Test
    public void testCreateTaskFromInput() throws DinoException {
        Parser parser = new Parser(new TaskList(), new Ui(), null);

        Task todoTask = parser.createTaskFromInput(Dino.TaskType.TODO, "Buy groceries");
        assertEquals(" T | 0 | Buy groceries", todoTask.toString());

        Task deadlineTask = parser.createTaskFromInput(Dino.TaskType.DEADLINE, "Finish assignment /by 2022-02-28 1800");
        assertEquals(" D | 0 | Finish assignment | by: Feb 28 2022 18:00", deadlineTask.toString());

        Task eventTask = parser.createTaskFromInput(Dino.TaskType.EVENT, "Birthday party /from 2022-03-01 1500 /to 2022-03-01 1800");
        assertEquals(" E | 0 | Birthday party | from: Mar 01 2022 15:00 to: Mar 01 2022 18:00", eventTask.toString());
    }

    @Test
    public void testCreateTaskFromInputWithInvalidDeadlineFormat() {
        TaskList taskList = new TaskList();

        Parser parser = new Parser(taskList, new Ui(), new Scanner(System.in));

        String inputTaskDetails = "InvalidDeadlineFormat\n";
        Dino.TaskType taskType = Dino.TaskType.DEADLINE;
        System.setIn(new java.io.ByteArrayInputStream(inputTaskDetails.getBytes()));

        try {
            Task createdTask = parser.createTaskFromInput(taskType, new Scanner(System.in).nextLine().trim());

            fail("Expected DinoException, but no exception was thrown.");
        } catch (DinoException e) {
            System.setIn(System.in);

            assertEquals("Invalid input format for deadline. Please use: deadline <deadline name> /by <time>", e.getMessage());
        }
    }
}

