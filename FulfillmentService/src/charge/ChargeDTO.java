package charge;

public class ChargeDTO {
	private int gId;
	private int gAdminId;
	private int gInvoiceId;
	private String gBankId;
	private String gProductName;
	private int gQuantity;
	private int gPrice;
	private String gDate;
	private String gState;
	
	public ChargeDTO(int gAdminId, int gInvoiceId, String gBankId, String gProductName, int gQuantity,int gPrice, String gDate) {
		this.gAdminId = gAdminId;
		this.gInvoiceId = gInvoiceId;
		this.gBankId = gBankId;
		this.gProductName = gProductName;
		this.gQuantity = gQuantity;
		this.gPrice = gPrice;
		this.gDate = gDate;
	}

	public ChargeDTO() {}
	
	public int getgId() {
		return gId;
	}

	public void setgId(int gId) {
		this.gId = gId;
	}

	public int getgQuantity() {
		return gQuantity;
	}

	public void setgQuantity(int gQuantity) {
		this.gQuantity = gQuantity;
	}

	public int getgPrice() {
		return gPrice;
	}

	public void setgPrice(int gPrice) {
		this.gPrice = gPrice;
	}

	public int getgAdminId() {
		return gAdminId;
	}
	
	public void setgAdminId(int gAdminId) {
		this.gAdminId = gAdminId;
	}
	
	public int getgInvoiceId() {
		return gInvoiceId;
	}
	
	public void setgInvoiceId(int gInvoiceId) {
		this.gInvoiceId = gInvoiceId;
	}
	
	public String getgProductName() {
		return gProductName;
	}
	
	public void setgProductName(String gProductName) {
		this.gProductName = gProductName;
	}
	public String getgDate() {
		return gDate;
	}
	public void setgDate(String gDate) {
		this.gDate = gDate;
	}

	public String getgState() {
		return gState;
	}

	public void setgState(String gState) {
		this.gState = gState;
	}

	public String getgBankId() {
		return gBankId;
	}

	public void setgBankId(String gBankId) {
		this.gBankId = gBankId;
	}

	@Override
	public String toString() {
		return "ChargeDTO [gId=" + gId + ", gAdminId=" + gAdminId + ", gInvoiceId=" + gInvoiceId + ", gBankId="
				+ gBankId + ", gProductName=" + gProductName + ", gQuantity=" + gQuantity + ", gPrice=" + gPrice
				+ ", gDate=" + gDate + ", gState=" + gState + "]";
	}
}
