package crypto;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class GCMCryptMain {

	/** AES */
	public static final String AES = "AES";
	/** IVの長さ（AES-GCMの場合は12が最適らしい） */
	public static final int GCM_IV_LENGTH = 12;
	/** AESのキーの長さ ( 128 or 192 or 256 ) */
	public static final int AES_KEY_SIZE = 256;
	/** GCMのタグの長さ ( 96 or 104 or 112 or 120 or 128 ) */
	private static final int GCM_TAG_LENGTH = 128;

	// 平文
	private static String plainText = "こんにちは。この文を「AES-256-GCM」の暗号化アルゴリズムによって暗号化します。";
	// ADD (64KiB以内)
	private static String addText = "This is an additional authenticated data. It's called the AAD. You can use any value.";

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
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);

		// add（追加認証）生成
		byte[] aad = addText.getBytes(StandardCharsets.UTF_8);

		// 平文出力
		System.out.println("Original Text : " + plainText);
		byte[] plainTextData = plainText.getBytes(StandardCharsets.UTF_8);

		// 暗号化
		byte[] encryptedData = MyCrypto.encrypt(plainTextData, MyCrypto.AES_GCM_NO_PADDING, secretKey, gcmParameterSpec,
				aad);

		// 暗号化データ出力
		System.out.println("Encrypted Text : " + Base64.getEncoder().encodeToString(encryptedData));

		// 複合化
		byte[] decryptedData = MyCrypto.decrypt(encryptedData, MyCrypto.AES_GCM_NO_PADDING, secretKey, gcmParameterSpec,
				aad);

		// 複合化データ出力
		System.out.println("Decrypted Text : " + new String(decryptedData, StandardCharsets.UTF_8));
	}

}
