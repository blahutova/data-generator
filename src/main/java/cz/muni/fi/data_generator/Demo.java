package cz.muni.fi.data_generator;

import cz.muni.fi.data_generator.generator.ConsoleLineSender;
import cz.muni.fi.data_generator.generator.Generator;
import cz.muni.fi.data_generator.generator.HTTPLineSender;
import cz.muni.fi.data_generator.pcapfile.PcapLineSource;
import cz.muni.fi.data_generator.smartplugs.SmartPlugLineSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by lucka on 10.2.2015.
 */
public class Demo {

    public static void main(String[] args) throws IOException, InterruptedException {
        File csv = new File("/home/lucka/Dokumenty/epa-http.txt");
        File csvSmart = new File("//home/lucka/Dokumenty/short.csv");
        File pcap = new File("/home/lucka/Stiahnuté/Ncapture.pcap");

        //HTTPLineSource lineSource = new HTTPLineSource(csv);
        //SmartPlugLineSource lineSource = new SmartPlugLineSource(csvSmart);
        PcapLineSource lineSource = new PcapLineSource(pcap);
        ConsoleLineSender lineSender = new ConsoleLineSender();
        //HTTPLineSender httpLineSender = new HTTPLineSender(new URL("http://127.0.0.1/lines.txt"));
        //TestOfDataGenerator testOfDataGenerator = new TestOfDataGenerator(lineSource, lineSender);
        /**HTTPLineSource lineSource = new HTTPLineSource(csv);
        HTTPLineSender lineSender = new HTTPLineSender();*/
        Generator generator = new Generator(lineSource, lineSender);
        //Generator generatorWithSpeed = new Generator(lineSource, lineSender);
        //testOfDataGenerator.startWithSpeed(0.01);
        System.out.println();
        //System.out.println();
        generator.startWithSpeed(0.001);
        //System.out.println("again");
        //generator.start();
        //generatorWithSpeed.startWithSpeed(0.5);
        //httpLineSender.close();
        lineSource.close();

    }
}
