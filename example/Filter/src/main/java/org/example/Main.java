package org.example;


import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat  = new Tomcat();
        tomcat.setPort(Integer.getInteger("port", 8080));
        tomcat.getConnector();

        File webappDir = new File("src/main/webapp").getAbsoluteFile();

        File resourcesDir = new File(webappDir.getAbsoluteFile(), "WEB-INF/classes");

        setDir(webappDir, resourcesDir);

        Context context = tomcat.addWebapp("", webappDir.getAbsolutePath());




        WebResourceRoot resources = new StandardRoot(context);
        resources.addPreResources(
                new DirResourceSet(resources, "/WEB-INF/classes", new File("target/classes").getAbsolutePath(), "/")
        );

        context.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }

    public static void setDir(File... dir) {

        for (File f : dir) {
            if (!f.exists()) {
                boolean created = f.mkdirs();
                if (created) {
                    System.out.println("Directory " + f + " created");
                    return;
                } else {
                    System.out.println("Directory " + f + " could not be created");
                    throw new RuntimeException("Directory " + f + " could not be created");
                }
            }
        }
    }
}