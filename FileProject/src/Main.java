import java.io.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        String inputFilePath = "/Users/zhangsan/testFile/Docker.dmg";
        String targetFilePath1 = "/Users/zhangsan/testFileDir1/Docker1.dmg";
        String targetFilePath2 = "/Users/zhangsan/testFileDir2/Docker2.dmg";
        File file = new File(inputFilePath);
        BufferedInputStream inputStream1 = new BufferedInputStream(new FileInputStream(file));
        BufferedInputStream inputStream2 = new BufferedInputStream(new FileInputStream(file));
        File targetFile1 = new File(targetFilePath1);
        File targetFile2 = new File(targetFilePath2);
        if (targetFile1.exists()) {
            System.out.println(targetFilePath1 + " has exist");
        }
        if (targetFile2.exists()) {
            System.out.println(targetFilePath2 + " has exist");
        }
        fileOutputStreamWriteTest(inputStream1, targetFile1);
        randomAccessFileWriteTest(inputStream2, targetFile2);
        System.out.println(file.length());
        System.out.println(targetFile1 + " path is " + targetFile1.getAbsolutePath() + " file size is " + targetFile1.length());
        System.out.println(targetFile2 + " path is " + targetFile2.getAbsolutePath() + " file size is " + targetFile2.length());
    }


    public static void fileOutputStreamWriteTest(BufferedInputStream inputStream, File targetFile) {
        long startTime = System.currentTimeMillis();
        try (FileOutputStream fos = new FileOutputStream(targetFile, true)) {
            if (targetFile.exists() && !targetFile.isDirectory() && targetFile.length() > 0) {
                inputStream.skip(targetFile.length());
            }
            byte[] buffer = new byte[8 * 1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
        } catch (IOException e) {
            String msgError = e.getMessage();
            System.out.println(msgError);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("fileOutputStreamWriteTest method cost " + (endTime - startTime) + " ms " + targetFile + "  size is " + targetFile.length());
    }

    public static void randomAccessFileWriteTest(BufferedInputStream inputStream, File targetFile) {
        long startTime = System.currentTimeMillis();
        try (RandomAccessFile saveFile = new RandomAccessFile(targetFile, "rw")) {
            if (targetFile.exists() && !targetFile.isDirectory() && targetFile.length() > 0) {
                saveFile.seek(targetFile.length());
                inputStream.skip(targetFile.length());
            }
            byte[] buffer = new byte[8 * 1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                saveFile.write(buffer, 0, len);
            }
        } catch (IOException e) {
            String msgError = e.getMessage();
            System.out.println(msgError);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("randomAccessFileWriteTest method cost " + (endTime - startTime) + " ms " + targetFile + "  size is " + targetFile.length());
    }

}