package cz.muni.fi.data_generator.generator;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Class representing one line of data from source. Every line must have a timestamp in seconds. Lines from some sources
 * can have also orders, in which they were written to source.
 *
 */
public class DataLine implements Comparable<DataLine>, Serializable {
    private Long timestamp;
    private Long order = null;
    private String[] data;

    /**
     * Returns array of data from line except of timestamp and order.
     *
     * @return array of data from line except of timestamp and order
     */
    public String[] getData() {
        return data;
    }

    /**
     * Returns timestamp, which indicates time, when was the data created and written
     * to file. Timestamp is in miliseconds.
     *
     * @return time, when the line was written to file in seconds
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * Returns order in which was line written to file. Lines
     * from some sources may not have order. The default value
     * is null.
     *
     * @return order in which was line written to file
     */
    public Long getOrder() { return order; }


    /**
     * Constructor of DataLine for lines with orders. You should
     * provide time of writing the line to source - timestamp in seconds.
     *
     * @param order order in which was line written to file
     * @param timestamp time, when the line was written to file in seconds
     * @param data array of data from line except of timestamp and order
     */
    public DataLine(Long order, Long timestamp, String[] data) {
        this.order = order;
        this.timestamp = timestamp;
        this.data = data;
    }

    /**
     * Constructor of DataLine for lines without orders. You should
     * provide time of writing the line to source - timestamp in seconds.
     *
     * @param timestamp time, when the line was written to file in seconds
     * @param data array of data from line except of timestamp and order
     */
    public DataLine(Long timestamp, String[] data) {
        this.timestamp = timestamp;
        this.data = data;
    }

    @Override
    public String toString() {
        String lineInString = "";
        if (order != null) {
            lineInString = order.toString();
        }
        lineInString = lineInString + " " + timestamp.toString() + " " + Arrays.toString(data);
        /*for (int i = 0; i < data.length; i++) {
            lineInString = lineInString + " " + data[i];
        }*/
        return lineInString;
    }

    @Override
    public int compareTo(DataLine o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if ((this.getTimestamp().equals(o.getTimestamp())) && order != null) {
            return Long.compare(this.getOrder(), o.getOrder());
        } else {
            return Long.compare(this.getTimestamp(), o.getTimestamp());
        }
    }
}