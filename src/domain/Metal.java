package domain;

public class Metal {

	private String name;
	private int rate;

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Metal [name=" + name + ", rate=" + rate + "]";
	}

}
