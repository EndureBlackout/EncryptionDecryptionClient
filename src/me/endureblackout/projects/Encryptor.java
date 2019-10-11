package me.endureblackout.projects;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {
	protected Message message;
	protected String key;
	
	protected Key aesKey;
	
	public Encryptor(String key, Message message) {
		this.key = key;
		this.message = message;
		
		aesKey = new SecretKeySpec(key.getBytes(), "AES");
	}
	
	public String encryptMessage() {
		String eMessage = "";
		String stringMessage = message.getMessage();
		
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encrypted = cipher.doFinal(stringMessage.getBytes());
			eMessage = new String(encrypted);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return eMessage;
	}
	
	
	
}
