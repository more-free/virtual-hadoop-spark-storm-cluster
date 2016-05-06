package dfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.InputStream;
import java.net.URI;
import static conf.Conf.*;

/**
 * Created by morefree on 3/5/16.
 */
public class FileSystemCat {
    public static void main(String []  args) throws Exception {
        String uri = TEST_FILE;
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(getPath("/")), conf);
        InputStream in = null;
        try {
            in = fs.open(new Path(uri));
            byte [] buf = new byte[35];
            IOUtils.readFully(in, buf, 0, buf.length);
            System.out.println(new String(buf));
            IOUtils.readFully(in, buf, 0, buf.length);
            System.out.println(new String(buf));
        } finally {
            IOUtils.closeStream(in);
        }
    }
}
