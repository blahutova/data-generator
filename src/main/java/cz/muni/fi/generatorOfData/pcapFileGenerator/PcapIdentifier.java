package cz.muni.fi.generatorOfData.pcapFileGenerator;

import com.igormaznitsa.jbbp.JBBPParser;
import com.igormaznitsa.jbbp.io.JBBPBitInputStream;
import com.igormaznitsa.jbbp.io.JBBPByteOrder;

import java.io.IOException;

/**
 * Class, which is caring for right parsing of pcap files. It determines Endian System of
 * pcap file, essential for their parsing.
 *
 * Created by Lucka on 26.3.2015.
 */
public class PcapIdentifier {
    private JBBPParser packetParser;
    private JBBPParser headerParser;

    private static final JBBPParser PCAP_HEADER_LITTLE_ENDIAN = JBBPParser.prepare(
            "<short versionOfPcapMajor;"
                    + "<short versionOfPcapMinor;"
                    + "<int timeZone;"
                    + "<int timestampsAccuracy;"
                    + "<int lengthOfCapture;"
                    + "<int network;"
    );

    private static final JBBPParser PCAP_HEADER_BIG_ENDIAN = JBBPParser.prepare(
            "short versionOfPcapMajor;"
                    + "short versionOfPcapMinor;"
                    + "int timeZone;"
                    + "int timestampsAccuracy;"
                    + "int lengthOfCapture;"
                    + "int network;"
    );

    private static final JBBPParser PCAP_PACKET_LITTLE_ENDIAN = JBBPParser.prepare(
            "<int timestampSeconds;"
                    + "<int timestampMicroSeconds;"
                    + "<int lengthOfPacketInFile;"
                    + "<int lengthOfPacketInNetwork;"
                    + "byte [lengthOfPacketInFile] packetData;"
    );

    private static final JBBPParser PCAP_PACKET_BIG_ENDIAN = JBBPParser.prepare(
            "int timestampSeconds;"
                    + "int timestampMicroSeconds;"
                    + "int lengthOfPacketInFile;"
                    + "int lengthOfPacketInNetwork;"
                    + "byte [lengthOfPacketInFile] packetData;"
    );

    public JBBPParser getPacketParser() {
        return packetParser;
    }

    public JBBPParser getHeaderParser() {
        return headerParser;
    }

    public PcapIdentifier(JBBPBitInputStream inputStream) throws IOException {
        int magic = inputStream.readInt(JBBPByteOrder.BIG_ENDIAN);

        switch (magic) {
            case 0xA1B2C3D4: {
                // BIG ENDIAN ORDER
                this.headerParser = PCAP_HEADER_BIG_ENDIAN;
                this.packetParser = PCAP_PACKET_BIG_ENDIAN;
            } break;
            case 0xD4C3B2A1: {
                // LITTLE ENDIAN ORDER
                this.headerParser = PCAP_HEADER_LITTLE_ENDIAN;
                this.packetParser = PCAP_PACKET_LITTLE_ENDIAN;
            } break;
            default:
                throw new IOException("Wrong type of file");
        }
    }

}
