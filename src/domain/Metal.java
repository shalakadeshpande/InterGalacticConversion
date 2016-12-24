package domain;

public class Metal {

	private String name;
	private String galacticCredit;
	private int numericCredit;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGalacticCredit() {
		return galacticCredit;
	}

	public void setGalacticCredit(String galacticCredit) {
		this.galacticCredit = galacticCredit;
	}

	public int getNumericCredit() {
		return numericCredit;
	}

	public void setNumericCredit(int numericCredit) {
		this.numericCredit = numericCredit;
	}

	@Override
	public String toString() {
		return "Metal [name=" + name + ", galacticCredit=" + galacticCredit
				+ ", numericCredit=" + numericCredit + "]";
	}

}
