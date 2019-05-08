package pay;

public class PayDTO {
	private int yId;
	private int yBankId;
	private int yAdminId;
	private int yPrice;
	private String yDate;
	private String yState;
	
	
	public PayDTO(int yId, int yBankId, int yAdminId, int yPrice, String yDate, String yState) {
		super();
		this.yId = yId;
		this.yBankId = yBankId;
		this.yAdminId = yAdminId;
		this.yPrice = yPrice;
		this.yDate = yDate;
		this.yState = yState;
	}
	
	public PayDTO(int yBankId, int yAdminId, int yPrice, String yDate, String yState) {
		this.yBankId = yBankId;
		this.yAdminId = yAdminId;
		this.yPrice = yPrice;
		this.yDate = yDate;
		this.yState = yState;
	}
	
	public PayDTO() {}

	public int getyId() {
		return yId;
	}
	
	public void setyId(int yId) {
		this.yId = yId;
	}
	
	public int getyBankId() {
		return yBankId;
	}


	public void setyBankId(int yBankId) {
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


	@Override
	public String toString() {
		return "PayDTO [yId=" + yId + "yBankId=" + yBankId + ", yAdminId=" + yAdminId + ", yPrice=" + yPrice + ", yDate=" + yDate
				+ ", yState=" + yState + "]";
	}
	
	
	
}
