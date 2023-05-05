import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @author zhangsan
 * @name FileUtils
 * @date 2023-05-05 23:42
 */
public class FileUtils {

    private static final int BUFFER_SIZE = 1024;

    public static String getFileMd5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte[] buffer = new byte[BUFFER_SIZE];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (IOException | NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

}
