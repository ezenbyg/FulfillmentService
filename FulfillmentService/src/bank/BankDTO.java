package bank;

public class BankDTO {
	private String bId;
	private int bAdminId;
	private int bBalance;
	
	public BankDTO() {}

	public BankDTO(String bId, int bAdminId, int bBalance) {
		super();
		this.bId = bId;
		this.bAdminId = bAdminId;
		this.bBalance = bBalance;
	}

	public String getbId() {
		return bId;
	}

	public void setbId(String bId) {
		this.bId = bId;
	}

	public int getbAdminId() {
		return bAdminId;
	}

	public void setbAdminId(int bAdminId) {
		this.bAdminId = bAdminId;
	}

	public int getbBalance() {
		return bBalance;
	}

	public void setbBalance(int bBalance) {
		this.bBalance = bBalance;
	}
}
