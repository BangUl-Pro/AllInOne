package com.ironfactory.allinoneenglish.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decrypt {

	public static void decrypt(File file, File decryptFile) {

		try {
			File outFile = decryptFile;
			if (!outFile.exists()) {
				outFile.createNewFile();
			}

			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(decryptFile);

			byte[] buffer = new byte[1024 * 32];
			int read;
			fis.read();
			while ((read = fis.read(buffer, 0, buffer.length)) != -1) {
				fos.write(buffer, 0, buffer.length);
			}
			fos.flush();
			fos.close();
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
		}
	
	}
}
