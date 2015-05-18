package cz.muni.fi.data_generator;

import cz.muni.fi.data_generator.generator.*;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

/**
 * Demo class for trying generator.
 */
public class Demo {

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        //your path to csv with smartplugs(or whatever implemented source)
        File csv = new File("/home/lucka/Dokumenty/short.csv");

        //-----------------Try HTTPLineSender----------------
        //create lineSource according to your file
        SmartPlugLineSource lineSource = new SmartPlugLineSource(csv);
        //create lineSender for your URL. Test servlet in this application is on http://localhost:8080/test/
        UrlLineSender lineSender = new UrlLineSender(new URL("http://localhost:8080/test/"));
        //ConsoleLineSender lineSender = new ConsoleLineSender();
        //First start the Tomcat and THEN start Demo class.
        //You should see output from Servlet in Tomcat console
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
        //set ports and host names to generator
        generator.setPortsAndHostNames(portsAndHostNames);
        //generator.startDistributedSending();

        //------------------------Scheduled sending-------------------
        //DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date date = dateFormatter.parse("2015-05-05 13:12:00");
        //ScheduledDistributedSending scheduledDistributedSending = new ScheduledDistributedSending(date, generator);
        //scheduledDistributedSending.scheduleSending();
        lineSource.close();

    }
}
