package cz.muni.fi.data_generator.pcapfile_processing;

import com.igormaznitsa.jbbp.mapper.Bin;

/**
 * Class representing one packet from pcap file.
 *
 */
@Bin
public class PcapPacket {
    private int timestampSeconds;
    private int timestampMicroSeconds;
    private int lengthOfPacketInFile;
    private int lengthOfPacketInNetwork;
    private byte [] packetData;

    public int getTimestampSeconds() {
        return timestampSeconds;
    }

    public void setTimestampSeconds(int timestampSeconds) {
        this.timestampSeconds = timestampSeconds;
    }

    public int getTimestampMicroSeconds() {
        return timestampMicroSeconds;
    }

    public void setTimestampMicroSeconds(int timestampMicroSeconds) {
        this.timestampMicroSeconds = timestampMicroSeconds;
    }

    public int getLengthOfPacketInFile() {
        return lengthOfPacketInFile;
    }

    public void setLengthOfPacketInFile(int lengthOfPacketInFile) {
        this.lengthOfPacketInFile = lengthOfPacketInFile;
    }

    public int getLengthOfPacketInNetwork() {
        return lengthOfPacketInNetwork;
    }

    public void setLengthOfPacketInNetwork(int lengthOfPacketInNetwork) {
        this.lengthOfPacketInNetwork = lengthOfPacketInNetwork;
    }

    public byte[] getPacketData() {
        return packetData;
    }

    public void setPacketData(byte[] packetData) {
        this.packetData = packetData;
    }

    public String getPacketInformation() {
        return  "Timestamp (us): " + Long.toString((long) timestampMicroSeconds & 0xFFFFFFFFL) + ", " +
                "Length : " + lengthOfPacketInFile + ", " +
                "Orig.Length : " + lengthOfPacketInNetwork + ", "+ packetData.toString();
    }

    public String getDataInformation() {
        return new String(packetData);
    }
}
