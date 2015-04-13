package cz.muni.fi.data_generator;

import cz.muni.fi.data_generator.generator.ConsoleLineSender;
import cz.muni.fi.data_generator.generator.Generator;
import cz.muni.fi.data_generator.generator.HTTPLineSender;
import cz.muni.fi.data_generator.httpserver.HTTPLineSource;
import cz.muni.fi.data_generator.pcapfile.PcapLineSource;
import cz.muni.fi.data_generator.smartplugs.SmartPlugLineSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucka on 10.2.2015.
 */
public class Demo {

    public static void main(String[] args) throws IOException, InterruptedException {
        //your path to csv with smartplugs(or whatever implemented source)
        File csv = new File("/home/lucka/Dokumenty/short.csv");

        //-----------------Try HTTPLineSender----------------
        //create lineSource according to your file
        SmartPlugLineSource lineSource = new SmartPlugLineSource(csv);
        //create lineSender for your URL. Test servlet in this application is on http://localhost:8080/test/
        HTTPLineSender lineSender = new HTTPLineSender(new URL("http://localhost:8080/test/"));
        //First start TestServletForURL and THEN start demo class.
        //You should see output from Servlet in servlet console
        Generator generator = new Generator(lineSource, lineSender);
        generator.start();

        //-------------------Try sending output to more computers -------------
        //create array with ports and hostnames of your computers
        String[][] portsAndHostNames = {
                {"1024", "localhost"},
                {"1025", "localhost"},
                {"1026", "localhost"},
                {"1027", "localhost"},
        };
        //start generator
        generator.startDistributedSending(portsAndHostNames);
        lineSource.close();

    }
}
