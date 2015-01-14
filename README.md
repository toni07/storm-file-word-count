storm-file-word-count
========================

Sample project based on https://github.com/davidkiss/storm-twitter-word-count demonstrating real-time computation Storm framework (https://github.com/nathanmarz/storm).
The code reads a file, keeps stats on words occuring in this file and logs top list with of words with most count in every 10 seconds.

This project contains a simple storm topology that connects to the sample stream of the Twitter Streaming API and keeps stats on words occuring in tweets and prints top list of words with highest count in every 10 seconds.

To get started:
* Clone this repo
* Import as existing Maven project in Eclipse
* Run Topology.java
