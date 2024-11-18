package com.assignment.transaction;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;

@SpringBootTest
class TransactionApplicationTests {

	@Test
	void generateHMACKey() throws NoSuchAlgorithmException {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
			keyGenerator.init(256);  // 256-bit key for HS256
			SecretKey secretKey = keyGenerator.generateKey();
			// Convert SecretKey to byte array
			byte[] keyBytes = secretKey.getEncoded();

			// Encode the byte array to a Base64 string
			String encodedKey = Base64.getEncoder().encodeToString(keyBytes);
			System.out.println(encodedKey);
		} catch (Exception e) {
			throw new RuntimeException("Error generating secret key", e);
		}

	}

}
