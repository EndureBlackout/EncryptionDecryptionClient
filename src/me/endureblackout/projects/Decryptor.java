package me.endureblackout.projects;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import me.endureblackout.projects.resources.SQLCommandHandler;

public class Decryptor {
	String key;
	int code;
	
	
	
	public Decryptor(String key, int code) {
		this.key = key;
		this.code = code;
	}
	
	public List<String> deryptMessage() {
		List<String> message = new ArrayList<>();
		
		SQLCommandHandler sqlH = new SQLCommandHandler();
		ResultSet results;
		results = sqlH.executeSQLQuery("SELECT * FROM `messages` WHERE `code`='" + code + "'");
		
		try {
			while(results.next()) {
				try {
					results.getString("message");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				try {
					Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
					Cipher cipher = Cipher.getInstance("AES");
					cipher.init(Cipher.DECRYPT_MODE, aesKey);
					String encrypted = results.getString("message");
					encrypted = new String(cipher.doFinal(encrypted.getBytes()));
					
					message.add(encrypted);
				} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
					e.printStackTrace();
				}
			}
		} catch (IllegalBlockSizeException | BadPaddingException | SQLException e) {
			e.printStackTrace();
		}
		
		
		return message;
	}
	
	
}
