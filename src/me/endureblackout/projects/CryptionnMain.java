package me.endureblackout.projects;

import java.util.List;
import java.util.Scanner;

import me.endureblackout.projects.resources.SQLCommandHandler;

//TODO: Fix padding when entering messages into database... otherwise can't decrypt properly.
public class CryptionnMain {
	public static void main(String[] args) {
		SQLCommandHandler sqlH = new SQLCommandHandler();

		Scanner scan = new Scanner(System.in);

		System.out.println("Encrypt or decrypt?");

		switch (scan.nextLine().toLowerCase()) {
		case "encrypt":

			System.out.println("Enter your message you would like to encrypt: ");
			String stringMessage = scan.nextLine();

			System.out.println("Enter a code to lock your message: ");
			int code = scan.nextInt();

			Message message = new Message(stringMessage, code);

			System.out.println(
					"Enter the 16 character secret key to use while encrypting (REMEMBER THIS OR YOU WILL NOT BE ABLE TO DECRYPT):");
			String key = scan.next();
			Encryptor encryptor = new Encryptor(key, message);

			System.out.println("Would you like to start encrypting? (Y/N):");

			String response = scan.next();

			switch (response.toLowerCase()) {
			case "y":
				System.out.println("Encrypting message...");
				String encryptedMessage = encryptor.encryptMessage();
				System.out.println("Your message now looks like: " + encryptedMessage);
				System.out.println("You will want to cycle keys after use... this is for your safety!");
				System.out.println("Uploading code and message to database...");

				if (sqlH.executeSQLUpdate("INSERT INTO `messages` (`code`, `message`) VALUES ('" + message.code + "', '"
						+ encryptedMessage + "');")) {
					System.out.println("Uploading to database sucessful.\n Closing connection...");
					sqlH.connectionClose();
				}

				break;
			case "n":
				System.out.println("For security reasons, program is ending...");
				break;
			}

			break;
		case "decrypt":

			System.out.println("Enter the code for the message you want to decrypt: ");
			code = scan.nextInt();

			System.out.println("Enter the secrect password for the message to decrypt it: ");
			String password = scan.next();

			Decryptor decryptor = new Decryptor(password, code);

			List<String> messages = decryptor.deryptMessage();

			for (int i = 0; i < messages.size(); i++) {
				System.out.println(messages.get(i));
			}

			break;
		}

		scan.close();
	}
}
