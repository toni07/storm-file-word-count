package testvaadin.aep.com.storm;

/**
 * Created by aep on 14/01/2015.
 */
public class LastReadLine {

	private Integer value;

	public LastReadLine(Integer value) {
		this.value = value;
	}

	public void increment(){
		this.value++;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
