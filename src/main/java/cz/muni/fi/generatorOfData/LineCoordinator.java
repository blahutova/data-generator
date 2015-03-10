package cz.muni.fi.generatorOfData;

import org.apache.commons.csv.CSVRecord;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Lines in some sources could have been accidentally written in messed order. This class ensures
 * that this mistakes will be fixed.
 *
 * Created by Lucka on 27.2.2015.
 */
public class LineCoordinator {
    private final static int NUMBER_OF_LINES_IN_INITIALIZATION = 1000;
    private Iterator<CSVRecord> iteratorFromSource;
    private LineSource lineSource;
    private Queue<DataLine> sortedBufferOfLines = new PriorityQueue<DataLine>();

    /**
     * Constructor for LineCoordinator. You must provide some iterator
     * of lines, which you want to have in right order. Also you must define
     * the concrete type of LineSource, which you are using.
     *
     * @param iteratorFromSource iterator of lines from source
     * @param lineSource concrete type of LineSource
     */
    public LineCoordinator(Iterator iteratorFromSource, LineSource lineSource) {
        this.iteratorFromSource = iteratorFromSource;
        this.lineSource = lineSource;
        initializeSortedBufferOfLines();
    }

    private void initializeSortedBufferOfLines() {
        while (iteratorFromSource.hasNext() && sortedBufferOfLines.size() < NUMBER_OF_LINES_IN_INITIALIZATION) {
            sortedBufferOfLines.add(lineSource.makeDataLineFromLine(iteratorFromSource.next()));
        }
    }

    /**
     * With this method is LineCoordinator able to start iterating
     * the lines all over from the beginning. It' s necessary, when you want
     * to use Generator more than once.
     *
     * @param iteratorFromSource iterator of lines from source
     */
    public void setToStart(Iterator iteratorFromSource) {
        this.iteratorFromSource = iteratorFromSource;
        initializeSortedBufferOfLines();
    }

    /**
     * Method which returns next line from source and guarantees right
     * order of the lines, which could be broken while their writing to file.
     *
     * @return next line from source in right order
     */
    public DataLine nextLine() {
        if (sortedBufferOfLines.size() != 0) {
            if (iteratorFromSource.hasNext()) {
                sortedBufferOfLines.add(lineSource.makeDataLineFromLine(iteratorFromSource.next()));
            }
            return sortedBufferOfLines.poll();
        } else {
            return null;
        }
    }



}
