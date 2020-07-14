package com.example.fjp.core;


import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @FileName: ClassScanner
 * @Author: admin
 * @Date: 2020/5/12 10:40
 * @Description: 类扫描器
 * History:
 * <author>          <time>          <version>
 * admin           2020/5/12           版本号
 */
public class ClassScanner {
	public static List<Class<?>> scanClasses(String packageName) throws IOException, ClassNotFoundException,
	                                                                    URISyntaxException {
		List<Class<?>> classList = new ArrayList<>();
		// classLoader.getResources 需要使用此种 斜线
		String path = packageName.replace(".", "/");
		System.out.println("path = " + path);
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		// 参考 class.getResource与 classLoader.getResource 加深理解
		Enumeration<URL> resources = classLoader.getResources(path);
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			// 此处 getProtocol的使用
			if (resource.getProtocol().contains("jar")) {
				JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();
				String jarFilePath = jarURLConnection.getJarFile().getName();
				classList.addAll(getClassesFromJar(jarFilePath, path));
			} else {
				// todo 加载路径中的类
				// 包路径 用 File.separator
				path = path.replace("/", File.separator);
				if (resource.getProtocol().contains("file")) {
					classList.addAll(getClassesFromPath(path, resource));
				}
			}
		}
		return classList;
	}
	
	
	/**
	 * 扫描包路径下的全体类
	 *
	 * @param path
	 * 		包路径 （用以寻找项目本身所在的路径，避免扫描不必要的类）
	 * @param resource
	 * 		资源路径，即项目的绝对路径
	 *
	 * @return
	 *
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	private static List<Class<?>> getClassesFromPath(String path, URL resource) throws URISyntaxException,
	                                                                                   IOException {
		List<Class<?>> classes = new ArrayList<>();
		File file = new File(resource.toURI());
		// 使用FileVisitor对目录进行遍历
		Files.walkFileTree(file.toPath(), new SimpleFileVisitor<Path>() {
			// 在访问子目录前触发该方法
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				return super.preVisitDirectory(dir, attrs);
			}
			
			// 在访问文件时触发该方法
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				String filePath = file.toString().substring(file.toString().lastIndexOf(path));
				if (filePath.startsWith(path) && filePath.endsWith(".class")) {
					String classFullName = filePath.replace(File.separator, ".").substring(0, filePath.length() - 6);
					try {
						classes.add(Class.forName(classFullName));
					} catch (ClassNotFoundException e) {
						throw new IOException("类加载失败！");
					}
				}
				return super.visitFile(file, attrs);
			}
			
			// 在访问失败时触发该方法
			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				return super.visitFileFailed(file, exc);
			}
			
			// 在访问目录之后触发该方法
			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				return super.postVisitDirectory(dir, exc);
			}
		});
		
		return classes;
	}
	
	
	/**
	 * 扫描jar包下的全体类
	 *
	 * @param jarFilePath
	 * 		jar包路径
	 * @param path
	 * 		（用以寻找项目本身所在的路径，避免扫描不必要的类）
	 * 		包路径
	 *
	 * @return
	 *
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static List<Class<?>> getClassesFromJar(String jarFilePath, String path) throws IOException,
	                                                                                        ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<>();
		Enumeration<JarEntry> jarEntries;
		try (JarFile jarFile = new JarFile(jarFilePath)) {
			jarEntries = jarFile.entries();
			while (jarEntries.hasMoreElements()) {
				JarEntry jarEntry = jarEntries.nextElement();
				String entryName = jarEntry.getName();
				// ...
				//entryName: org/apache/catalina/users/AbstractRole.class
				//entryName: org/apache/catalina/users/AbstractUser.class
				//entryName: org/apache/catalina/users/Constants.class
				//entryName: org/apache/catalina/users/LocalStrings.properties
				//entryName: org/apache/catalina/users/MemoryGroup$1.class
				if (entryName.startsWith(path) && entryName.endsWith(".class")) {
					// 此处路径为 /
					String classFullName = entryName.replace("/", ".").substring(0, entryName.length() - 6);
					classes.add(Class.forName(classFullName));
				}
			}
		}
		return classes;
	}
}