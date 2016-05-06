package conf;

import java.util.Arrays;
import java.util.List;

/**
 * Created by morefree on 3/5/16.
 */
public class Conf {
    public static String NAMENODE = "vm-cluster-node1";
    public static List<String> DATANODES = Arrays.asList(
            "vm-cluster-node2",
            "vm-cluster-node3",
            "vm-cluster-node4"
    );

    public static String getPath(String hdfsFile) {
        return "hdfs://" + NAMENODE + hdfsFile;
    }

    public static String TEST_FILE = "/logins/logins-2015-10-12.txt";
    public static String DEFAULT_USER = "hdfs";
}
