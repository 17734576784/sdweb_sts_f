package com.sts.rsa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Date;

import com.kesd.common.WebConfig;

public class GenRSAKeys {

	public static void setPublicAndPrivateFilePath() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		SecureRandom secureRandom = new SecureRandom(new Date().toString()
				.getBytes());
		keyPairGenerator.initialize(1024, secureRandom);
		KeyPair keyPair = keyPairGenerator.genKeyPair();

		String real_basepath = new File(new File(WebConfig.class.getResource(
				"/").getPath()).getParent()).getParent();

		try {
			real_basepath = URLDecoder.decode(real_basepath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String publicKeyFilePath = real_basepath
				+ "\\WEB-INF\\classes\\publicKeyFile";

		// String publicKeyFilename = "F:/publicKeyFile";
		String publicKeyFilename = publicKeyFilePath;
		byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
		FileOutputStream fos = new FileOutputStream(publicKeyFilename);
		fos.write(publicKeyBytes);
		fos.close();

		String privateKeyFilePath = real_basepath
				+ "\\WEB-INF\\classes\\privateKeyFile";
		// String privateKeyFilename = "F:/privateKeyFile";
		String privateKeyFilename = privateKeyFilePath;
		byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
		fos = new FileOutputStream(privateKeyFilename);
		fos.write(privateKeyBytes);
		fos.close();
	}
	
	//
	// public static void main(String[] args) throws Exception {
	// KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
	// SecureRandom secureRandom = new SecureRandom(new
	// Date().toString().getBytes());
	// keyPairGenerator.initialize(1024, secureRandom);
	// KeyPair keyPair = keyPairGenerator.genKeyPair();
	//
	// String real_basepath = new File(new
	// File(WebConfig.class.getResource("/").getPath()).getParent()).getParent();
	//
	// try {
	// real_basepath = URLDecoder.decode(real_basepath, "UTF-8");
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// }
	//
	// String publicKeyFilePath = real_basepath +
	// "\\WEB-INF\\classes\\publicKeyFile";
	//
	// //String publicKeyFilename = "F:/publicKeyFile";
	// String publicKeyFilename = publicKeyFilePath;
	// byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
	// FileOutputStream fos = new FileOutputStream(publicKeyFilename);
	// fos.write(publicKeyBytes);
	// fos.close();
	//
	// String privateKeyFilePath = real_basepath +
	// "\\WEB-INF\\classes\\privateKeyFile";
	// //String privateKeyFilename = "F:/privateKeyFile";
	// String privateKeyFilename = privateKeyFilePath;
	// byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
	// fos = new FileOutputStream(privateKeyFilename);
	// fos.write(privateKeyBytes);
	// fos.close();
	// }
}