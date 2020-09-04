package util;

public class FileUtil {
	static public String getExtension(String filePath){

	    String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
	    return "."+fileName.substring(filePath.lastIndexOf(".")+1);
	}
	static public String getRandomName(String filePath){
		String time = String.valueOf(System.currentTimeMillis());
	    String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
	    return time+"."+fileName.substring(filePath.lastIndexOf(".")+1);
	}
	private void Test() {
		
	}

}
