/**
 * Taken from the storm-starter project on GitHub
 * https://github.com/nathanmarz/storm-starter/ 
 */
package testvaadin.aep.com.storm;

import backtype.storm.Config;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Reads a file whenever it is modified
 * @author toni07
 */
public class TwitterSampleSpout extends BaseRichSpout {

	private SpoutOutputCollector collector;
	private LinkedBlockingQueue<FakeObject> queue;
	private final int POLL_INTERVAL = 50;
	private FileSystemManager fsManager;
	private FileObject listenedFile;
	private DefaultFileMonitor fm;

	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {

		queue = new LinkedBlockingQueue<FakeObject>(1000);
		this.collector = collector;
		System.out.println("##p1a");
		final LastReadLine lastLineRead = new LastReadLine(0);
		try {
			fsManager = VFS.getManager();
			listenedFile = fsManager.resolveFile("C:/dev/logs/prediwaste-test.log");

			//1st parse of the file
			List<String> newLineList = FileIncrementalReader.readToString(listenedFile, lastLineRead);
			for (String s : newLineList) {
				final FakeObject obj = new FakeObject(s, s);
				queue.offer(obj);
			}

			//then listen for any change
			fm = new DefaultFileMonitor(new FileListener(){
				@Override
				public void fileCreated(FileChangeEvent fileChangeEvent) throws Exception {}

				@Override
				public void fileDeleted(FileChangeEvent fileChangeEvent) throws Exception {}

				@Override
				public void fileChanged(FileChangeEvent fileChangeEvent) throws Exception {
					List<String> newLineList = FileIncrementalReader.readToString(listenedFile, lastLineRead);
					for (String s : newLineList) {
						final FakeObject obj = new FakeObject(s, s);
						queue.offer(obj);
					}
				}
			});

			fm.setRecursive(true);
			fm.addFile(listenedFile);
			fm.start();
		}
		catch (FileSystemException e) {
			e.printStackTrace();
		}

		System.out.println("##p1b");
	}

	@Override
	public void nextTuple() {
		//System.out.println("##p1c");
		final FakeObject ret = queue.poll();
		if (ret == null) {
			Utils.sleep(POLL_INTERVAL);
		}
		else {
			collector.emit(new Values(ret));
		}
	}

	@Override
	public void close() {
		System.out.println("##listener stop");
		fm.stop();
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		Config ret = new Config();
		ret.setMaxTaskParallelism(1);
		return ret;
	}

	@Override
	public void ack(Object id) {
	}

	@Override
	public void fail(Object id) {
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("tweet"));
	}

}
