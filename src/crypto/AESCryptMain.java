package crypto;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AESCryptMain {

	/** AES */
	public static final String AES = "AES";
	/** GCMのIVの長さ */
	public static final int GCM_IV_LENGTH = 16;
	/** AESのキーの長さ ( 128 or 192 or 256 ) */
	public static final int AES_KEY_SIZE = 256;

	// 平文
	private static String plainText = "こんにちは。この文を「AES-256」の暗号化アルゴリズムによって暗号化します。";

	public static void main(String[] args) throws GeneralSecurityException {

		// 秘密鍵の生成
		KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
		keyGenerator.init(AES_KEY_SIZE);
		SecretKey secretKey = keyGenerator.generateKey();

		// 初期化ベクトル（IV）の生成
		byte[] iv = new byte[GCM_IV_LENGTH];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(iv);

		// GCMParameterSpecインスタンス生成
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

		// 平文出力
		System.out.println("Original Text : " + plainText);
		byte[] plainTextData = plainText.getBytes(StandardCharsets.UTF_8);

		// 暗号化
		byte[] encryptedData = MyCrypto.encrypt(plainTextData, MyCrypto.AES_CBC_PKCS5PADDING, secretKey, ivParameterSpec);

		// 暗号化データ出力
		System.out.println("Encrypted Text : " + Base64.getEncoder().encodeToString(encryptedData));

		// 複合化
		byte[] decryptedData = MyCrypto.decrypt(encryptedData, MyCrypto.AES_CBC_PKCS5PADDING, secretKey, ivParameterSpec);

		// 複合化データ出力
		System.out.println("Decrypted Text : " + new String(decryptedData, StandardCharsets.UTF_8));
	}

}
