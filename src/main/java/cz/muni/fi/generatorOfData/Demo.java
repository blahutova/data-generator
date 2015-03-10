package cz.muni.fi.generatorOfData;

import java.io.File;
import java.io.IOException;

/**
 * Created by lucka on 10.2.2015.
 */
public class Demo {

    public static void main(String[] args) throws IOException, InterruptedException {
        File csv = new File("/home/lucka/Dokumenty/short.csv");
        //File csv = new File("/run/media/lucka/michal_HDD/sorted.csv");

        SmartPlugLineSource lineSource = new SmartPlugLineSource(csv);
        SmartPlugLineSender lineSender = new SmartPlugLineSender();
        /**HTTPLineSource lineSource = new HTTPLineSource(csv);
        HTTPLineSender lineSender = new HTTPLineSender();*/
        Generator generator = new Generator(lineSource, lineSender);
        //Generator generatorWithSpeed = new Generator(lineSource, lineSender);
        generator.startWithSpeed(1);
        System.out.println();
        System.out.println();
        generator.startWithSpeed(1);
        //System.out.println("again");
        //generator.start();
        //generatorWithSpeed.startWithSpeed(0.5);
        lineSource.close();
    }
}
