package pay;

public class PayDTO {
	private int yId;
	private String yBankId;
	private int yAdminId;
	private int yPrice;
	private String yDate;
	private String yState;
	private int rTransportId;
	private String rDate;
	private String bId;
	private int bBalance;
	private String aName;
	private int sum;
	
	public PayDTO(int yId, String yBankId, int yAdminId, int yPrice, String yDate, String yState) {
		super();
		this.yId = yId;
		this.yBankId = yBankId;
		this.yAdminId = yAdminId;
		this.yPrice = yPrice;
		this.yDate = yDate;
		this.yState = yState;
	}
	
	public PayDTO(String yBankId, int yAdminId, int yPrice, String yDate, String yState) {
		this.yBankId = yBankId;
		this.yAdminId = yAdminId;
		this.yPrice = yPrice;
		this.yDate = yDate;
		this.yState = yState;
	}
	
	public PayDTO(int rTransportId, String rDate, String yState) {
		this.rTransportId = rTransportId;
		this.rDate = rDate;
		this.yState = yState;
	}
	
	public PayDTO() {}
	
	public String getaName() {
		return aName;
	}

	public void setaName(String aName) {
		this.aName = aName;
	}

	public String getbId() {
		return bId;
	}

	public void setbId(String bId) {
		this.bId = bId;
	}

	public int getbBalance() {
		return bBalance;
	}

	public void setbBalance(int bBalance) {
		this.bBalance = bBalance;
	}

	public int getyId() {
		return yId;
	}
	
	public void setyId(int yId) {
		this.yId = yId;
	}
	
	public String getyBankId() {
		return yBankId;
	}

	public void setyBankId(String yBankId) {
		this.yBankId = yBankId;
	}

	public int getyAdminId() {
		return yAdminId;
	}

	public void setyAdminId(int yAdminId) {
		this.yAdminId = yAdminId;
	}

	public int getyPrice() {
		return yPrice;
	}

	public void setyPrice(int yPrice) {
		this.yPrice = yPrice;
	}

	public String getyDate() {
		return yDate;
	}

	public void setyDate(String yDate) {
		this.yDate = yDate;
	}

	public String getyState() {
		return yState;
	}
	
	public void setyState(String yState) {
		this.yState = yState;
	}

	public int getrTransportId() {
		return rTransportId;
	}

	public void setrTransportId(int rTransportId) {
		this.rTransportId = rTransportId;
	}

	public String getrDate() {
		return rDate;
	}

	public void setrDate(String rDate) {
		this.rDate = rDate;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	@Override
	public String toString() {
		return "PayDTO [yId=" + yId + ", yBankId=" + yBankId + ", yAdminId=" + yAdminId + ", yPrice=" + yPrice
				+ ", yDate=" + yDate + ", yState=" + yState + ", rTransportId=" + rTransportId + ", rDate=" + rDate
				+ ", bId=" + bId + ", bBalance=" + bBalance + ", aName=" + aName + ", sum=" + sum + "]";
	}
}
