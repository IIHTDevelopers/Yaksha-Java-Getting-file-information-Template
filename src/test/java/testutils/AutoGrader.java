package testutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;

public class AutoGrader {

	// Test if the code correctly implements file information retrieval operations
	public boolean testFileInfo(String filePath) throws IOException {
		System.out.println("Starting testFileInfo with file: " + filePath);

		File participantFile = new File(filePath); // Path to participant's file
		if (!participantFile.exists()) {
			System.out.println("File does not exist at path: " + filePath);
			return false;
		}

		FileInputStream fileInputStream = new FileInputStream(participantFile);
		JavaParser javaParser = new JavaParser();
		CompilationUnit cu;
		try {
			cu = javaParser.parse(fileInputStream).getResult()
					.orElseThrow(() -> new IOException("Failed to parse the Java file"));
		} catch (IOException e) {
			System.out.println("Error parsing the file: " + e.getMessage());
			throw e;
		}

		System.out.println("Parsed the Java file successfully.");

		// Flags to check presence and calls of specific methods
		boolean hasMainMethod = false;
		boolean calledGetName = false;
		boolean calledGetAbsolutePath = false;
		boolean calledLength = false;
		boolean calledCanRead = false;
		boolean calledCanWrite = false;
		boolean calledIsDirectory = false;

		// Check for method declarations
		for (MethodDeclaration method : cu.findAll(MethodDeclaration.class)) {
			String methodName = method.getNameAsString();
			// Check for the presence of the main method
			if (methodName.equals("main")) {
				hasMainMethod = true;
			}
		}

		// Check for method calls in the entire class
		for (MethodCallExpr methodCall : cu.findAll(MethodCallExpr.class)) {
			String methodName = methodCall.getNameAsString();
			if (methodName.equals("getName")) {
				calledGetName = true;
			} else if (methodName.equals("getAbsolutePath")) {
				calledGetAbsolutePath = true;
			} else if (methodName.equals("length")) {
				calledLength = true;
			} else if (methodName.equals("canRead")) {
				calledCanRead = true;
			} else if (methodName.equals("canWrite")) {
				calledCanWrite = true;
			} else if (methodName.equals("isDirectory")) {
				calledIsDirectory = true;
			}
		}

		// Log method presence and calls
		System.out.println("Method 'main' is " + (hasMainMethod ? "present" : "NOT present"));
		System.out.println("Method 'getName' is " + (calledGetName ? "called" : "NOT called"));
		System.out.println("Method 'getAbsolutePath' is " + (calledGetAbsolutePath ? "called" : "NOT called"));
		System.out.println("Method 'length' is " + (calledLength ? "called" : "NOT called"));
		System.out.println("Method 'canRead' is " + (calledCanRead ? "called" : "NOT called"));
		System.out.println("Method 'canWrite' is " + (calledCanWrite ? "called" : "NOT called"));
		System.out.println("Method 'isDirectory' is " + (calledIsDirectory ? "called" : "NOT called"));

		// Final result
		boolean result = hasMainMethod && calledGetName && calledGetAbsolutePath && calledLength && calledCanRead
				&& calledCanWrite && calledIsDirectory;

		System.out.println("Test result: " + result);
		return result;
	}
}
