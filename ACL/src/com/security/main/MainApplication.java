package com.security.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.security.model.AccessControl;
import com.security.util.ACL;

/**
 * Main application to be executed. Please take note of the input folder in the
 * code.
 * 
 * @author Max
 *
 */
public class MainApplication {

	private static final int NUMBER_OF_USERS = 4;
	private static String currentUser = null;
	private static final String[] COMMANDS = new String[] { "switch", "r", "w", "x", "quit" };

	public static void main(String[] args) {
		ACL acl = new ACL(NUMBER_OF_USERS, "filesFolder");
		// Printing the initialized content
		printAll(acl);

		// Printing the instruction
		System.out.println("\nWelcome to Mahdi's file explorer!");
		System.out.println("Here is the list of the commands available: ");
		System.out.println("quit: Exists the application");
		System.out.println("switch <username>: Switches the current username to <username>");
		System.out.println("x: prints all the files which the current user has EXECUTE right for.");
		System.out.println("w: prints all the files which the current user has WRITE right for.");
		System.out.println("r: prints all the files which the current user has READ right for.");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String input;
			do {
				input = br.readLine();
				if (validCommand(input)) {
					executeCommand(input, br, acl);
				} else {
					System.out.println("Invalid command!");
				}
			} while (!COMMANDS[4].equalsIgnoreCase(input));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Validates to make sure the command given is valid.
	 * 
	 * @param input
	 * @return
	 */
	private static boolean validCommand(String input) {
		for (String cmd : COMMANDS) {
			if (input.startsWith(cmd)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Prints all the files and user access rights generated.
	 * 
	 * @param acl
	 */
	private static void printAll(ACL acl) {
		List<AccessControl> accessControls = acl.getAccessControls();
		for (AccessControl accessControl : accessControls) {
			System.out.println("File: [" + accessControl.getFileName() + "]");
			for (String user : accessControl.getUserAccesses().keySet()) {
				System.out.println("USER:" + user + " RIGHT: " + printRight(accessControl.getUserAccesses().get(user)));
			}
		}
	}

	/**
	 * Uses bit operations to figure out the rights
	 * 
	 * @param right
	 * @return
	 */
	private static String printRight(Integer right) {
		int intValue = right.intValue();
		String result = "";
		if (isCorretRight(intValue, 1)) {
			result += "READ ";
		}
		if (isCorretRight(intValue, 2)) {
			result += "WRITE ";
		}
		if (isCorretRight(intValue, 4)) {
			result += "EXECUTE ";
		}
		return result;
	}

	/**
	 * Executes the commands and takes care of the validations.
	 * 
	 * @param input
	 * @param br
	 * @param acl
	 */
	private static void executeCommand(String input, BufferedReader br, ACL acl) {
		if (input.startsWith(COMMANDS[0])) {
			String newUser = input.substring(7, input.length());
			if (acl.getUsers().contains(newUser)) {
				System.out.println("User switched to [" + newUser + "].");
				currentUser = newUser;
			} else {
				System.out.println("User [" + newUser + "] does not exist. Try again.");
			}
		}
		listCommand(COMMANDS[1], input, acl, 1);
		listCommand(COMMANDS[2], input, acl, 2);
		listCommand(COMMANDS[3], input, acl, 4);
	}

	/**
	 * List command takes care of the printing of files based on the input
	 * right.
	 * 
	 * @param expectedCommand
	 * @param input
	 * @param acl
	 * @param inputRight
	 */
	private static void listCommand(String expectedCommand, String input, ACL acl, int inputRight) {
		if (expectedCommand.equalsIgnoreCase(input)) {
			if (currentUser == null) {
				System.out.println("Please set the user first by switching to a valid user.");
			} else {
				list(acl, inputRight);
			}
		}
	}

	/**
	 * Lists the files for the current user which inputRight matching.
	 * 
	 * @param acl
	 * @param inputRight
	 */
	private static void list(ACL acl, int inputRight) {
		int resultCounter = 0;
		for (AccessControl ac : acl.getAccessControls()) {
			Integer right = ac.getUserAccesses().get(currentUser);
			if (right != null && isCorretRight(right, inputRight)) {
				System.out.println("File: [" + ac.getFileName() + "]");
				System.out.println("USER:" + currentUser + " RIGHT: " + printRight(ac.getUserAccesses().get(currentUser)));
				resultCounter++;
			}
		}
		if (resultCounter == 0) {
			System.out.println("No result found!");
		}
	}

	/**
	 * Validates the right given to see if it matches the expectedRight passed.
	 * 
	 * @param right
	 * @param expectedRight
	 * @return
	 */
	private static boolean isCorretRight(Integer right, int expectedRight) {
		return (right & expectedRight) == expectedRight;
	}

}
