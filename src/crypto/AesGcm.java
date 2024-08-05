package crypto;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesGcm {

	/** AESのキーの長さ ( 128 or 192 or 256 ) */
	public static final int AES_KEY_BIT_LENGTH = 256;
	/** GCMのNonceの長さ */
	public static final int GCM_NONCE_LENGTH = 12;
	/** GCMのタグの長さ ( 96 or 104 or 112 or 120 or 128 ) */
	public static final int GCM_TAG_BIT_LENGTH = 128;
	
	//	平文
	private static String plainText = "こんにちは。この文を「AES-256-GCM」の暗号化アルゴリズムによって暗号化します。";
	// ADD (64KiB以内)
	private static String addText ="This is an additional authenticated data. It's called the AAD. You can use any value.";
	
	public static void main(String[] args) throws GeneralSecurityException {
	
		// AES鍵生成
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(AES_KEY_BIT_LENGTH);
		SecretKey key = keyGenerator.generateKey();
		
		// nonce生成
		byte[] nonce = new byte[GCM_NONCE_LENGTH];
		SecureRandom random = new SecureRandom();
		random.nextBytes(nonce);
		
		// add（追加認証）生成
		byte[] aad = addText.getBytes(StandardCharsets.UTF_8);
		
		// 平文出力
		System.out.println("Original Text : " + plainText);

		// 暗号化
        byte[] encryptedData = encrypt(plainText.getBytes(), key, nonce, aad);;

		// 暗号化データ出力
		System.out.println("Encrypted Text : " + Base64.getEncoder().encodeToString(encryptedData));

		// 複合化
        byte[] decryptedData = decrypt(encryptedData, key, nonce, aad);
		
		// 複合化データ出力
        System.out.println("Decrypted Text : " + new String(decryptedData, StandardCharsets.UTF_8));
	}
	
	/**
	 * 暗号化
	 * 
	 * @param plainData	平文データ
	 * @param key 暗号化キー
	 * @param nonce nonce
	 * @param aad 追加認証
	 * @return 暗号化データ
	 * @throws GeneralSecurityException
	 */
	public static byte[] encrypt(byte[] plainData, SecretKey key, byte[] nonce, byte[] aad) throws GeneralSecurityException {

		// Cipherインスタンス生成
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // SecretKeySpecインスタンス生成
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

        // GCMParameterSpecインスタンス生成
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_BIT_LENGTH, nonce);

        // Cipherに暗号モードを設定
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

        // ADD（追加認証）設定
        if (aad != null) {
            cipher.updateAAD(aad);
        }

        // 暗号化
        byte[] cipherData = cipher.doFinal(plainData);

        return cipherData;
	}
	
	/**
	 * 複合化
	 * 
	 * @param cipherData 暗号化データ
	 * @param key 暗号キー
	 * @param nonce nonce
	 * @param aad 追加認証
	 * @return 複合化データ
	 * @throws GeneralSecurityException
	 */
    public static byte[] decrypt(byte[] cipherData, SecretKey key, byte[] nonce, byte[] aad) throws GeneralSecurityException {
    	// Cipherインスタンス生成
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // SecretKeySpecインスタンス生成
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

        // SecretKeySpecインスタンス生成
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_BIT_LENGTH, nonce);

        // Cipherに複合モードを設定
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);

        // ADD（追加認証）設定
        if (aad != null) {
            cipher.updateAAD(aad);
        }

        // 複合化
        byte[] decryptedData = cipher.doFinal(cipherData);

        return decryptedData;
    }

}
