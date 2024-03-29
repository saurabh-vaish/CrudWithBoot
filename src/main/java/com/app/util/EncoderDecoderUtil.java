package com.app.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class EncoderDecoderUtil   {

	 private static final String SECRET_KEY_1 = "E3F4F5116C46A6EB";  // for iv
//	    private static final String SECRET_KEY_2 = "weJiSEvR5yAC5ftB";
	    private static final String SECRET_KEY_2 = "F7BB97D6707E448CCA96F1BDCAE88113"; // for key

	    private IvParameterSpec ivParameterSpec;
	    private SecretKeySpec secretKeySpec;
	    private Cipher cipher;
	    
	    
	    EncoderDecoderUtil() throws Exception{
	    	ivParameterSpec = new IvParameterSpec(SECRET_KEY_1.getBytes("UTF-8"));
	        secretKeySpec = new SecretKeySpec(SECRET_KEY_2.getBytes("UTF-8"), "AES");
	        cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//	        cipher = Cipher.getInstance("AES/CBC/NoPadding");
	    }
	 
	    /**
	     * Encrypt the string with this internal algorithm.
	     *
	     * @param toBeEncrypt string object to be encrypt.
	     * @return returns encrypted string.
	     * @throws NoSuchPaddingException
	     * @throws NoSuchAlgorithmException
	     * @throws InvalidAlgorithmParameterException
	     * @throws InvalidKeyException
	     * @throws BadPaddingException
	     * @throws IllegalBlockSizeException
	     */
	    public String encrypt(String toBeEncrypt) throws Exception {
	        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
	        byte[] encrypted = cipher.doFinal(toBeEncrypt.getBytes());
	        return Base64.encodeBase64String(encrypted);
	    }
	 
	    /**
	     * Decrypt this string with the internal algorithm. The passed argument should be encrypted using
	     * {@link #encrypt(String) encrypt} method of this class.
	     *
	     * @param encrypted encrypted string that was encrypted using {@link #encrypt(String) encrypt} method.
	     * @return decrypted string.
	     * @throws InvalidAlgorithmParameterException
	     * @throws InvalidKeyException
	     * @throws BadPaddingException
	     * @throws IllegalBlockSizeException
	     */
	    public String decrypt(String encrypted) throws Exception {
	    	
	        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
	        byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(encrypted));
	        return new String(decryptedBytes);
	    }
	} 