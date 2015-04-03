<h1>Data Generator of Existing Data Sets</h1>

This library serves for sending data from existing data sets. Data sets are in the form of csv files and they usually represents some logs to servers/websites or they can provide information about activity of some electric devices. Thanks to this API you can truly reproduce the way in which were the data sent. It means that they are sent to console (or wherever you want) exactly in that time, when they were written to file. You can easily extend the Generator, so it can work with your own data set. 

<h2>Supported Data Sets </h2>
Data sets are usually in the form of <b>csv files</b>. In these files, every entry is written to the new line and parts of information are separated with commas (or another separator defined by you). Csv file is ideal for generator, because data in it are readable for people and they can be parsed easily. <br />

You can use whatever data set you want. The only condition for data is that you must be able to provide <b>timestamp in miliseconds</b>, in which was data written to file. Every piece of data with timestamp must be on separate line. Every line can also have a number with order, but it is not mandatory. Lines can contain other optional information. In Generator, class <b>DataLine</b> represents one line of data from file. <br />

<h3>Another type of files</h3>
It' s possible to parse data also in different forms like are csv files. The proof is that this Generator implements sending packets from <b>pcap files</b>. These files are binary and they capture network data. Each packet has information about time, when he was captured, its length and another fields. Generator process packets and then you can just watch the way, how packets was captured from the network.

<h2>How to add your own dataset</h2>
How to start if you have your own data and you want to use Generator on them?<br />
<b><i>1. Implement interface LineSource</i></b> <br />
Interface LineSource represents your file, from which is Generator obtaining lines with data. In constructor of implementation of LineSource is usually used navigation to your file with data. Then you must implement methods:<br />
<i>DataLine nextLine()</i> : it returns next line from file in DataLine object. So it' s essential to know, how to parse a line from your file and create DataLine object from it. You can use some simple library for parsing csv files.<br /> 
<i>setToStart()</i>:  sets LineSource to the beginnig of file. It is needed for using the Generator more times than once.<br />
<i>close():</i>  closes the stream, from which is LineSource created.<br />

<b><i>2. Choose, where you want send the data</i></b> <br />
Implementation of Generation provides two places to send processed data: console and some URL. If you need another place, you must have your own implementation of interface LineSender.<br />

<b><i>3. Create generator from your LineSource and LineSender and start sending data in the way you wish!</i></b> <br />


<h2>Example usage</h2>
<i>//provide the path to your file with data</i><br>
File csv = new File("/path/to/file.csv");<br>
<i>//create your implementation of LineSource and LineSender</i><br>
HTTPLineSource lineSource = new HTTPLineSource(csv);<br>
HTTPLineSender lineSender = new HTTPLineSender();<br>
<i>//create Generator with LineSource and LineSender</i><br>
Generator generator = new Generator(lineSource, lineSender);<br>
<i>//start sending data to output</i><br>
generator.startWithSpeed(0.5);<br>
<i>//don't forget to close the LineSource</i><br>
lineSource.close();<br>



