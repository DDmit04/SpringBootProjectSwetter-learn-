package com.example.sweater.service;

import java.math.BigInteger;
import java.security.SecureRandom;

public class ServiceUtils {
	
	 static String generateRandomKey(int byteLength) {
	    SecureRandom secureRandom = new SecureRandom();
	    byte[] token = new byte[byteLength];
	    secureRandom.nextBytes(token);
	    return new BigInteger(1, token).toString(16);
	}


}
