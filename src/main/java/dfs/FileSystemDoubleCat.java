package dfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import java.net.URI;
import static conf.Conf.*;

/**
 * Created by morefree on 3/5/16.
 */
public class FileSystemDoubleCat {
    public static void main(String []  args) throws Exception {
        String uri = TEST_FILE;
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(getPath("/")), conf);
        FSDataInputStream in = null;
        try {
            in = fs.open(new Path(uri));
            IOUtils.copyBytes(in, System.out, 4096, false);
            in.seek(0);
            IOUtils.copyBytes(in, System.out, 4096, false);
        } finally {
            IOUtils.closeStream(in);
        }
    }
}
