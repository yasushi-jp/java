package crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class TestCrypto {

	public SecretKey getKeyFromPassword(String pass) throws NoSuchAlgorithmException {
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw e;
		}
		sha.update(pass.getBytes(StandardCharsets.UTF_8));
		byte[] shakey = sha.digest();
		SecretKey ret = new SecretKeySpec(shakey, "AES");
		return ret;
	}
	
	public SecretKey generateRandomKey() throws NoSuchAlgorithmException {
		
		KeyGenerator keygen;
		try {
			keygen = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw e;
		}
		SecretKey ret = keygen.generateKey();
		return ret;
	}
	
	public SecretKey getKeyFromBytes(byte[] src) {
		SecretKey ret = new SecretKeySpec(src, "AES");
		return ret;
	}
}
