package io;

import fundamentals.CommandInterpreter;
import static_data.SessionData;

import java.io.IOException;
import java.util.Scanner;

public class InputReader {

    private static final String EXIT_COMMAND = "quit";

    public static void readCommands() throws IOException {
        OutputWriter.writeMessage(String.format("%s > ", SessionData.currentPath));
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();

        while (!input.equals(EXIT_COMMAND)) {
            CommandInterpreter.interpretCommand(input);
            OutputWriter.writeMessage(String.format("%s > ", SessionData.currentPath));
            input = scanner.nextLine();
        }
    }
}