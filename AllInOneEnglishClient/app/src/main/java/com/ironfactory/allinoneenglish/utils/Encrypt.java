package com.ironfactory.allinoneenglish.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Encrypt {

	public void encrypt(File file, File encryptFile) {
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			File outFile = encryptFile;
			int read;
			if (!outFile.exists()) {
				outFile.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(outFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			FileInputStream encFis = new FileInputStream(outFile);
			fos.write(67);
			while((read = fis.read()) != -1) {
				fos.write((char) read);
			}
			fos.flush();
			fos.close();
			System.out.println("끝");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
}
