package cz.muni.fi.data_generator.generator;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class for planning the time of start of method startDistributedSending().<br />
 *
 * Example usage:<br />
 * //pick a time<br />
 * DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");<br />
 * Date date = dateFormatter.parse("2015-05-05 13:12:00");<br />
 * //provide time and generator to constructor<br />
 * ScheduledDistributedSending scheduledDistributedSending = new ScheduledDistributedSending(date, generator);<br />
 * //plan the sending <br />
 * scheduledDistributedSending.scheduleSending();<br />
 *
 */
public class ScheduledDistributedSending extends TimerTask{
    private Generator generator;
    private Date date;

    public ScheduledDistributedSending(Date date, Generator generator) {
        this.date = date;
        this.generator = generator;
    }

    public void scheduleSending() {
        Timer timer = new Timer();
        timer.schedule(generator, date);
    }

    @Override
    public void run() {
        try {
            generator.startDistributedSending();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
