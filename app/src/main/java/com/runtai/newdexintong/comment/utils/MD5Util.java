package com.runtai.newdexintong.comment.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	private static MessageDigest sMd5MessageDigest;
	private static StringBuilder sStringBuilder;

	static {
		try {
			sMd5MessageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		}
		sStringBuilder = new StringBuilder();
	}

	private MD5Util() {
	}

	public static String md5(String s) {
		// Log.e("MD5", s);
		sMd5MessageDigest.reset();
		sMd5MessageDigest.update(s.getBytes());

		byte digest[] = sMd5MessageDigest.digest();

		sStringBuilder.setLength(0);
		for (int i = 0; i < digest.length; i++) {
			final int b = digest[i] & 255;
			if (b < 16) {
				sStringBuilder.append('0');
			}
			sStringBuilder.append(Integer.toHexString(b));
		}

		return sStringBuilder.toString();
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

}
