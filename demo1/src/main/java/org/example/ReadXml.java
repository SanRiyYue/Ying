package org.example;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.io.InputStream;

public class ReadXml {
    public static void main(String[] args) throws IOException {
        InputStream input = Main.class.getResourceAsStream("/book.json");
        ObjectMapper mapper = new ObjectMapper();

        //反序列化时忽略不存在的JAVABean属性:
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Book book = mapper.readValue(input, Book.class);
        System.out.println(book);
    }
}
