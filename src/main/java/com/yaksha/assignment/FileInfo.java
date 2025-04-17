package com.yaksha.assignment;

import java.io.File;

public class FileInfo {

	public static void main(String[] args) {
		// **Create a file instance**
		File file = new File("fileInfo.txt");

		// Check if the file exists
		if (file.exists()) {
			System.out.println("File Information:");

			// **Get file name**
			System.out.println("File Name: " + file.getName());

			// **Get file path**
			System.out.println("File Path: " + file.getAbsolutePath());

			// **Get file size**
			System.out.println("File Size: " + file.length() + " bytes");

			// **Check if the file is readable**
			System.out.println("Is Readable: " + file.canRead());

			// **Check if the file is writable**
			System.out.println("Is Writable: " + file.canWrite());

			// **Check if the file is a directory**
			System.out.println("Is Directory: " + file.isDirectory());
		} else {
			System.out.println("File does not exist.");
		}
	}
}
