package me.endureblackout.projects;

import java.util.Scanner;

import me.endureblackout.projects.resources.SQLCommandHandler;

//TODO: Add security code to be attached with the Message object.
//TODO: Setup database to store ONLY the message and security code attached to the message.
public class CryptionnMain {
	public static void main(String[] args) {
		SQLCommandHandler sqlH = new SQLCommandHandler();

		Scanner scan = new Scanner(System.in);

		System.out.println("Encrypt or decrypt?");

		switch (scan.nextLine().toLowerCase()) {
		case "encrypt":
			
			System.out.println("Enter your message you would like to encrypt: ");
			String stringMessage = scan.nextLine();

			/*
			 * System.out.println("Enter a code to lock your message: "); int code =
			 * scan.nextInt();
			 */
			Message message = new Message(stringMessage, 55378);

			System.out.println(
					"Enter the 16 character secret key to use while encrypting (REMEMBER THIS OR YOU WILL NOT BE ABLE TO DECRYPT):");
			String key = scan.nextLine();
			Encryptor encryptor = new Encryptor(key, message);

			System.out.println("Would you like to start encrypting? (Y/N):");

			String response = scan.nextLine();

			switch (response.toLowerCase()) {
			case "y":
				System.out.println("Encrypting message...");
				String encryptedMessage = encryptor.encryptMessage();
				System.out.println("Your message now looks like: " + encryptedMessage);
				System.out.println("You will want to cycle keys after use... this is for your safety!");
				System.out.println("Uploading code and message to database...");

				if (sqlH.executeSQLUpdate("INSERT INTO `messages` (`code`, `message`) VALUES ('" + message.code + "', '"
						+ encryptedMessage + "')")) {
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
			int code = scan.nextInt();
			
			break;
		}

		scan.close();
	}
}
