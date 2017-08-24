package com.vanke.tydirium.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 
 * 
 * @Description: 密码工具类
 *
 * @author: songjia
 * @date: 2017年8月14日 下午4:09:14
 */
@SuppressWarnings("deprecation")
public class CryptoUtil {

	/**
	 * 获取指定长度随机字符串
	 * 
	 * @param len
	 *            字符串长度
	 * @return
	 */
	public static String getRandomString(int len) {
		return RandomStringUtils.randomAlphanumeric(len);
	}

	/**
	 * MD5盐值加密
	 * 
	 * @param password
	 *            原始密码
	 * @param salt
	 *            盐值
	 * @return
	 */
	public static String getHash(String password, int salt) {
		String saltStr = null;
		if (salt > 0) {
			saltStr = getRandomString(salt);
		}
		return getHashWithSalt(password, saltStr);
	}

	/**
	 * MD5加密
	 * 
	 * @param str
	 *            原字符串
	 * @return
	 */
	public static String md5(String str) {
		try {
			String md5 = Hex.encodeHexString(MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8")));
			return md5;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Should not happen", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Should not happen", e);
		}
	}

	/**
	 * MD5盐值加密验证
	 * 
	 * @param password
	 *            原密码
	 * @param hash
	 *            MD5盐值加密密码
	 * @return
	 */
	public static boolean validateHash(String password, String hash) {
		String[] parts = hash.split(":");
		String salt = (parts.length == 2) ? parts[1] : null;
		return getHashWithSalt(password, salt).equals(hash);
	}

	public static String getHashWithSalt(String password, String saltStr) {
		return (saltStr == null) ? md5(password) : md5(saltStr + password) + ':' + saltStr;
	}

	public static void main(String[] args) {
		System.out.println(getRandomString(10)); // 4nwayszFDq
	}
}
