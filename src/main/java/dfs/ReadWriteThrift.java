package dfs;

import org.apache.thrift.TDeserializer;
import org.apache.thrift.TSerializer;
import serialization.thrift.PageID;

/**
 * Created by morefree on 3/5/16.
 */
public class ReadWriteThrift {
    public static void main(String [] args) throws Exception {
        TSerializer serializer = new TSerializer();
        PageID pageID = new PageID();
        pageID.setUrl("https://www.google.com");

        byte[] data = serializer.serialize(pageID);
        System.out.println(data.length);

        TDeserializer deserializer = new TDeserializer();
        PageID newPageID = new PageID();
        deserializer.deserialize(newPageID, data);

        System.out.println(newPageID.getUrl());
    }

}
