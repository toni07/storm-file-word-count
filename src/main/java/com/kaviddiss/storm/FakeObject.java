package testvaadin.aep.com.storm;

/**
 * Created by aepardeau on 14/01/2015.
 */
public class FakeObject {

	private String lineContent;
	private String lineContent2;

	public FakeObject(String lineContent, String lineContent2) {
		this.lineContent = lineContent;
		this.lineContent2 = lineContent2;
	}

	public String getLineContent() {
		return lineContent;
	}

	public void setLineContent(String lineContent) {
		this.lineContent = lineContent;
	}

	public String getLineContent2() {
		return lineContent2;
	}

	public void setLineContent2(String lineContent2) {
		this.lineContent2 = lineContent2;
	}
}
