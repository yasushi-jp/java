package crypto;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;

public class MyCrypto {

	/** AES/GCM/NoPadding */
	public static final String AES_GCM_NO_PADDING = "AES/GCM/NoPadding";
	/** AES/CBC/PKCS5Padding */
	public static final String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";

	/**
	 * 暗号化
	 * 
	 * @param plainData      平文データ
	 * @param transformation 暗号アルゴリズム
	 * @param key            暗号化キー
	 * @param parameterSpec  暗号パラメータ
	 * @return 暗号化データ
	 * @throws GeneralSecurityException
	 */
	public static byte[] encrypt(byte[] plainData, String transformation, Key key, AlgorithmParameterSpec parameterSpec)
			throws GeneralSecurityException {
		return encrypt(plainData, transformation, key, parameterSpec, null);
	}

	/**
	 * 暗号化
	 * 
	 * @param plainData      平文データ
	 * @param transformation 暗号アルゴリズム
	 * @param key            暗号キー
	 * @param parameterSpec  暗号パラメータ
	 * @param aad            追加認証
	 * @return 暗号化データ
	 * @throws GeneralSecurityException
	 */
	public static byte[] encrypt(byte[] plainData, String transformation, Key key, AlgorithmParameterSpec parameterSpec,
			byte[] aad) throws GeneralSecurityException {

		// Cipherインスタンス生成
		Cipher cipher = Cipher.getInstance(transformation);

		// Cipherに暗号モードを設定
		cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

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
	 * @param cipherData     暗号化データ
	 * @param transformation 暗号アルゴリズム
	 * @param key            暗号キー
	 * @param parameterSpec  暗号パラメータ
	 * @return 複合化データ
	 * @throws GeneralSecurityException
	 */
	public static byte[] decrypt(byte[] cipherData, String transformation, Key key,
			AlgorithmParameterSpec parameterSpec) throws GeneralSecurityException {
		return decrypt(cipherData, transformation, key, parameterSpec, null);
	}

	/**
	 * 複合化
	 * 
	 * @param cipherData     暗号化データ
	 * @param transformation 暗号アルゴリズム
	 * @param key            暗号キー
	 * @param parameterSpec  暗号パラメータ
	 * @param aad            追加認証
	 * @return 複合化データ
	 * @throws GeneralSecurityException
	 */
	public static byte[] decrypt(byte[] cipherData, String transformation, Key key,
			AlgorithmParameterSpec parameterSpec, byte[] aad) throws GeneralSecurityException {
		// Cipherインスタンス生成
		Cipher cipher = Cipher.getInstance(transformation);

		// Cipherに複合モードを設定
		cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

		// ADD（追加認証）設定
		if (aad != null) {
			cipher.updateAAD(aad);
		}

		// 複合化
		byte[] decryptedData = cipher.doFinal(cipherData);

		return decryptedData;
	}

}
