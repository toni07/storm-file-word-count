package testvaadin.aep.com.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.Map;

/**
 * Receives tweets and emits its words over a certain length.
 */
public class WordSplitterBolt extends BaseRichBolt {
    private final int minWordLength;

    private OutputCollector collector;

    public WordSplitterBolt(int minWordLength) {
        this.minWordLength = minWordLength;
    }

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector collector) {
        System.out.println("##WordSplitterBolt prepare");
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        System.out.println("##WordSplitterBolt execute2");
        final FakeObject obj = (FakeObject) input.getValueByField("tweet");
        String lang = obj.getLineContent();
        String text = obj.getLineContent2();
        String[] words = text.split(" ");
        for (String word : words) {
            if (word.length() >= minWordLength) {
                collector.emit(new Values(lang, word));
            }
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        System.out.println("##WordSplitterBolt declareOutputFields");
        declarer.declare(new Fields("lang", "word"));
    }
}
