package cz.muni.fi.generatorOfData;

import cz.muni.fi.generatorOfData.dataGeneratorAPI.ConsoleLineSender;
import cz.muni.fi.generatorOfData.dataGeneratorAPI.Generator;
import cz.muni.fi.generatorOfData.httpServerGenerator.HTTPLineSource;
import cz.muni.fi.generatorOfData.pcapFileGenerator.PcapLineSource;

import java.io.File;
import java.io.IOException;

/**
 * Created by lucka on 10.2.2015.
 */
public class Demo {

    public static void main(String[] args) throws IOException, InterruptedException {
        File csv = new File("/home/lucka/Dokumenty/epa-http.txt");
        //File csv = new File("/run/media/lucka/michal_HDD/sorted.csv");
        File pcap = new File("/home/lucka/Stiahnuté/Ncapture.pcap");

        //HTTPLineSource lineSource = new HTTPLineSource(csv);
        PcapLineSource lineSource = new PcapLineSource(pcap);
        ConsoleLineSender lineSender = new ConsoleLineSender();
        //TestOfDataGenerator testOfDataGenerator = new TestOfDataGenerator(lineSource, lineSender);
        /**HTTPLineSource lineSource = new HTTPLineSource(csv);
        HTTPLineSender lineSender = new HTTPLineSender();*/
        Generator generator = new Generator(lineSource, lineSender);
        //Generator generatorWithSpeed = new Generator(lineSource, lineSender);
        //testOfDataGenerator.startWithSpeed(0.01);
        System.out.println();
        //System.out.println();
        generator.startWithSpeed(1);
        //System.out.println("again");
        //generator.start();
        //generatorWithSpeed.startWithSpeed(0.5);
        lineSource.close();
    }
}
