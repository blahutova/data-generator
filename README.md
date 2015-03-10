<h1>Data Generator of Existing Data Sets</h1>

This library serves for sending data from existing data sets. Data sets are in the form of csv files and they usually represents some logs to servers/websites or they can provide information about activity of some electric devices. Thanks to this API you can truly reproduce the way in which were the data sent. It means that they are sent to console (or wherever you want) exactly in that time, when they were written to file. You can easily extend the Generator, so it can work with your own data set. 

<h2>Data sets </h2>
You can use whatever data set you want. The only condition for data is that you must be able to provide <b>timestamp in seconds</b>, in which was data written to file. Every piece of data with timestamp must be on separate line. Every line can also have a number with order, but it is not mandatory. Lines can contain other optional information. In Generator, class DataLine represents one line of data from file. 

<h2>Line source</h2>
Interface LineSource represents your file, from which is Generator obtaining lines with data. In constructor of implementation of LineSource is usually used navigation to your file with data. Then you must implement methods:<br>
<b>nextLine()</b> : it returns next line from file in DataLine object.<br>
<b>setToStart()</b>:  sets LineSource to the beginnig of file. It is needed for using the Generator more times than once.<br>
<b>makeDataLineFromLine()</b>: it is used to make DataLine object from line of your data set.

<h2>Line sender</h2>
Interface for sending one line to the console or other place, which you need. You must implement only one method <b>send(DataLine line)</b>. It writes one DataLine to given output.

<h2>Class Generator</h2>
After implementation of your LineSource and LineSender, you can provide them to constructor of Generator. Then you can start sending your data to output in the way you want. Generator obtains two methods:<br>
<b>startSendingData()</b>: starts sending data to output in real-time speed, according to their timestamps. You can see the reproduced way, how was data written to the file.<br>
<b>startSendingDataWithSpeed(double speedCoefficient)</b>: sending data to output in speed, which you determined with speed coefficient (can't be zero).

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



