package com.andruszkow.message_passing;

import com.andruszkow.message_passing.data.Message;
import org.w3c.dom.ls.LSResourceResolver;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class Main {
	private final static Path workingDirectory = Paths.get("");
	private final static String messageFilename = "message.xml";
	private final static Path messageFilePath = workingDirectory.resolve(messageFilename);
	private final static MessageParser parser = new MessageParser();
	private final static MessageProcessor processor = new MessageProcessor(10, 50);

	public static void main(String[] args) throws IOException {
		System.out.println("Message passing v1.0");
		System.out.println("Ready to receive messages named " + messageFilename
				+ " in the working directory");

		while (true) {
			Message msg = null;
			try {
				File msgFile = pollUntilMessageFound(messageFilePath);
				msg = parser.parse(msgFile);
				msgFile.delete(); //consume message file if parsed successfully
			} catch (FileNotFoundException e) {
				System.err.println("Main: tried to parse message file, but file not found");
				e.printStackTrace(System.err);
			} catch (JAXBException e) {
				System.err.println("Main: tried to parse message file, but XML contents are invalid");
				e.printStackTrace(System.err);
			}

			try {
				processor.accept(msg);
			} catch (IllegalArgumentException e) {
				System.err.println("Error while parsing transaction message, skipping transaction");
			}
		}
	}

	private static File pollUntilMessageFound(Path messageFilePath) {
		while (Files.notExists(messageFilePath)) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				System.err.println("New message poll: sleep interrupted");
			}
		}
		return messageFilePath.toFile();
	}
}
