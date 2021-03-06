package cz.muni.fi.data_generator.generator;

import com.igormaznitsa.jbbp.io.JBBPBitInputStream;
import com.igormaznitsa.jbbp.utils.JBBPUtils;
import cz.muni.fi.data_generator.pcapfile_processing.PcapHeader;
import cz.muni.fi.data_generator.pcapfile_processing.PcapIdentifier;
import cz.muni.fi.data_generator.pcapfile_processing.PcapPacket;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Line source for pcap files.It behaves the same way like line sources for csv files, only difference is
 * that one line for PcapLineSource means one packet in file. Pcap files contains also headers, if you are interested,
 * you can read it from field headerOfFile.
 *
 */
public class PcapLineSource implements LineSource {
    private JBBPBitInputStream inputStream;
    private File pathToFile;
    private PcapIdentifier parserByEndianSystem;
    private PcapHeader headerOfFile;

    public PcapHeader getHeaderOfFile() {
        return headerOfFile;
    }

    public PcapLineSource(File pathToFile) throws IOException {
        this.pathToFile = pathToFile;
        this.inputStream = new JBBPBitInputStream(new FileInputStream(pathToFile));
        this.parserByEndianSystem = new PcapIdentifier(inputStream);
        headerOfFile = parserByEndianSystem.getHeaderParser().parse(inputStream).mapTo(PcapHeader.class);
    }

    @Override
    public DataLine nextLine() {
        try {
            if (inputStream.hasAvailableData()) {
                PcapPacket packet = parserByEndianSystem.getPacketParser().parse(inputStream).mapTo(PcapPacket.class);
                return makeDataLineFromLine(packet);
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    @Override
    public void setToStart() throws IOException {
        this.inputStream = new JBBPBitInputStream(new FileInputStream(pathToFile));
        this.parserByEndianSystem = new PcapIdentifier(inputStream);
        headerOfFile = parserByEndianSystem.getHeaderParser().parse(inputStream).mapTo(PcapHeader.class);
    }


    public DataLine makeDataLineFromLine(Object line) {
        PcapPacket packet = (PcapPacket) line;
        String[] data = {packet.getPacketInformation(), packet.getDataInformation()};
        return new DataLine((long) packet.getTimestampSeconds() * 1000, data);
    }

    @Override
    public void close() throws IOException {
        JBBPUtils.closeQuietly(inputStream);
    }

}
