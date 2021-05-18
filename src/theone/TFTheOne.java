package theone;

import java.util.Arrays;

public class TFTheOne {
	private String[] value;
	
	public TFTheOne(String[] value) {
		this.value = value;
	}
	
	public TFTheOne bind(TFFunction fn) {
		this.value = fn.doSomething(this.value);
		return this;
	}
	
	public void printme () {
		System.out.println(Arrays.toString(this.value));
	}
	
	public void printme (int i) {
		System.out.println(this.value[i]);
	}
}

