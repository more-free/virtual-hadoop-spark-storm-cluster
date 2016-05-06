package dfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

import static conf.Conf.*;

/**
 * Created by morefree on 3/5/16.
 */
public class FileCopyWithProgress {

    public static void main(String [] args) throws Exception {
        String localFile = "/Users/morefree/Development/play/play.rb";
        InputStream in = new BufferedInputStream(new FileInputStream(localFile));

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(getPath(TEST_FILE)), conf, DEFAULT_USER);
        FSDataOutputStream out = fs.create(new Path("/play/play.rb"), new Progressable() {
            public void progress() {
                System.out.print(".");
            }
        });

        IOUtils.copyBytes(in, out, 4096, true); // auto-close
    }
}
