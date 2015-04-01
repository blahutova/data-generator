package cz.muni.fi.data_generator.pcapfile;

import com.igormaznitsa.jbbp.mapper.Bin;

/**
 * Class representing the header of the packer from pcap file.
 *
 * Created by Lucka on 26.3.2015.
 */
@Bin
public class PcapHeader {
    private short versionOfPcapMajor;
    private short versionOfPcapMinor;
    private int timeZone;
    private int timestampsAccuracy;
    private int lengthOfCapture;
    private int network;

    public short getVersionOfPcapMajor() {
        return versionOfPcapMajor;
    }

    public void setVersionOfPcapMajor(short versionOfPcapMajor) {
        this.versionOfPcapMajor = versionOfPcapMajor;
    }

    public short getVersionOfPcapMinor() {
        return versionOfPcapMinor;
    }

    public void setVersionOfPcapMinor(short versionOfPcapMinor) {
        this.versionOfPcapMinor = versionOfPcapMinor;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public int getTimestampsAccuracy() {
        return timestampsAccuracy;
    }

    public void setTimestampsAccuracy(int timestampsAccuracy) {
        this.timestampsAccuracy = timestampsAccuracy;
    }

    public int getLengthOfCapture() {
        return lengthOfCapture;
    }

    public void setLengthOfCapture(int lengthOfCapture) {
        this.lengthOfCapture = lengthOfCapture;
    }

    public int getNetwork() {
        return network;
    }

    public void setNetwork(int network) {
        this.network = network;
    }

    @Override
    public String toString() {
        return "Version "+ versionOfPcapMajor +'.'+ versionOfPcapMinor +
                "\nGMT to local correction: "+ timeZone +
                "\nAccuracy of timestamps: "+ timestampsAccuracy +
                "\nMax length of captured packets, in octets: "+ lengthOfCapture +
                "\nData link type: "+network;
    }
}
