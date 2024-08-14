package crypto;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AesCbc {

	/** AES */
	public static final String AES = "AES";
	/** AES/CBC/PKCS5Padding */
	public static final String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";
	/** IVの長さ */
	public static final int GCM_IV_LENGTH = 16;
	/** AESのキーの長さ ( 128 or 192 or 256 ) */
	public static final int AES_KEY_SIZE = 256;

	// 平文
	private static String plainText = "こんにちは。この文を「AES-CBC」の暗号化アルゴリズムによって暗号化します。";

	public static void main(String[] args) throws GeneralSecurityException {

		// 共通鍵の生成
		KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
		keyGenerator.init(AES_KEY_SIZE);
		SecretKey secretKey = keyGenerator.generateKey();

		// 初期化ベクトル（IV）の生成
		byte[] iv = new byte[GCM_IV_LENGTH];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(iv);

		// IvParameterSpecインスタンス生成
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

		// 平文出力
		System.out.println("Original Text : " + plainText);
		byte[] plainTextData = plainText.getBytes(StandardCharsets.UTF_8);

		// 暗号化
		byte[] encryptedData = encrypt(plainTextData, secretKey, ivParameterSpec);

		// 暗号化データ出力
		System.out.println("Encrypted Text : " + Base64.getEncoder().encodeToString(encryptedData));

		// 複合化
		byte[] decryptedData = decrypt(encryptedData, secretKey, ivParameterSpec);

		// 複合化データ出力
		System.out.println("Decrypted Text : " + new String(decryptedData, StandardCharsets.UTF_8));
	}

	/**
	 * 暗号化
	 * 
	 * @param plainData 平文データ
	 * @param key       暗号化キー
	 * @param params    暗号パラメータ
	 * @return 暗号化データ
	 * @throws GeneralSecurityException
	 */
	public static byte[] encrypt(byte[] plainData, SecretKey secretKey, AlgorithmParameterSpec params)
			throws GeneralSecurityException {

		// Cipherインスタンス生成
		Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);

		// Cipherに暗号モードを設定
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, params);

		// 暗号化
		byte[] cipherData = cipher.doFinal(plainData);

		return cipherData;
	}

	/**
	 * 複合化
	 * 
	 * @param cipherData 暗号化データ
	 * @param key        暗号キー
	 * @param params     暗号パラメータ
	 * @return 複合化データ
	 * @throws GeneralSecurityException
	 */
	public static byte[] decrypt(byte[] cipherData, SecretKey key, AlgorithmParameterSpec params)
			throws GeneralSecurityException {
		// Cipherインスタンス生成
		Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);

		// Cipherに複合モードを設定
		cipher.init(Cipher.DECRYPT_MODE, key, params);

		// 複合化
		byte[] decryptedData = cipher.doFinal(cipherData);

		return decryptedData;
	}

}
