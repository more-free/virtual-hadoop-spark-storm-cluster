package dfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import serialization.thrift.PageID;

import java.io.*;
import java.net.URI;
import java.util.function.Function;

import static conf.Conf.DEFAULT_USER;
import static conf.Conf.getPath;

/**
 * Created by morefree on 3/5/16.
 */
public class ThriftIO {
    private TSerializer serializer;
    private TDeserializer deserializer;
    private Configuration conf;
    private FileSystem fs;

    public ThriftIO() throws IOException, InterruptedException {
        serializer = new TSerializer();
        deserializer = new TDeserializer();
        conf = new Configuration();
        fs = FileSystem.get(URI.create(getPath("/")), conf, DEFAULT_USER);
    }

    public void writePageID(String path, PageID pageID) throws IOException, TException {
        Path dfsPath = new Path(path);
        FSDataOutputStream os = null;
        try {
            if (!fs.exists(dfsPath)) {
                os = fs.create(dfsPath);
            } else {
                os = fs.append(dfsPath);
            }
            byte[] data = serializer.serialize(pageID);
            os.writeInt(data.length);
            os.write(data);
        } finally {
            os.close();
        }
    }

    public PageID readPageID(byte [] data) throws IOException, TException {
        DataInputStream dataIn = new DataInputStream(new ByteArrayInputStream(data));
        byte [] payload = new byte [dataIn.readInt()];
        dataIn.read(payload);
        PageID pageID = new PageID();
        deserializer.deserialize(pageID, payload);
        return pageID;
    }

    /**
     * @param path
     * @param consumer it stops reading if consumer returns false
     * @throws IOException
     * @throws TException
     */
    public void readPageIDAsStream(String path, Function<PageID, Boolean> consumer) throws IOException, TException {
        try (FSDataInputStream dataIn = fs.open(new Path(path))) {
            while (true) {
                try {
                    int len = dataIn.readInt();
                    byte[] buf = new byte[len];
                    dataIn.readFully(buf);
                    PageID pageID = new PageID();
                    deserializer.deserialize(pageID, buf);
                    if (!consumer.apply(pageID))
                        break;
                } catch (EOFException e) {
                    break;
                }
            }
        }
    }

    public static void main(String [] args) throws Exception {

    }

    static void read() throws Exception {
        ThriftIO io = new ThriftIO();
        io.readPageIDAsStream("/play/thrift/pageid", pageid -> {
            if (pageid == null || pageid.getUrl().equals("www.yahoo.com.cn")) return false;
            else {
                System.out.println(pageid.getUrl());
                return true;
            }
        });
    }

    static void write() throws Exception {
        PageID id1 = new PageID();
        id1.setUrl("www.google.com");

        PageID id2 = new PageID();
        id2.setUrl("www.yahoo.com.cn");

        PageID id3 = new PageID();
        id3.setUrl("www.microsoft.com");

        ThriftIO io = new ThriftIO();
        String path = "/play/thrift/pageid";
        io.writePageID(path, id1);
        io.writePageID(path, id2);
        io.writePageID(path, id3);
    }
}
