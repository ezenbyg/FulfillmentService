package charge;

public class ChargeDTO {
	private int gId;
	private int gAdminId;
	private String gBankId;
	private int gTotalPrice;
	private String gDate;
	private String gState;
	private String gInvoiceId;
	private String gShopName;
	
	public ChargeDTO() {}

	public ChargeDTO(int gAdminId, String gBankId, int gTotalPrice, String gDate) {
		super();
		this.gAdminId = gAdminId;
		this.gBankId = gBankId;
		this.gTotalPrice = gTotalPrice;
		this.gDate = gDate;
	}

	public ChargeDTO(int gAdminId, String gBankId, int gTotalPrice, String gDate, String gInvoiceId) {
		super();
		this.gAdminId = gAdminId;
		this.gBankId = gBankId;
		this.gTotalPrice = gTotalPrice;
		this.gDate = gDate;
		this.gInvoiceId = gInvoiceId;
	}

	public int getgId() {
		return gId;
	}

	public void setgId(int gId) {
		this.gId = gId;
	}

	public int getgAdminId() {
		return gAdminId;
	}

	public void setgAdminId(int gAdminId) {
		this.gAdminId = gAdminId;
	}

	public String getgBankId() {
		return gBankId;
	}

	public void setgBankId(String gBankId) {
		this.gBankId = gBankId;
	}

	public int getgTotalPrice() {
		return gTotalPrice;
	}

	public void setgTotalPrice(int gTotalPrice) {
		this.gTotalPrice = gTotalPrice;
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

	public String getgInvoiceId() {
		return gInvoiceId;
	}

	public void setgInvoiceId(String gInvoiceId) {
		this.gInvoiceId = gInvoiceId;
	}

	public String getgShopName() {
		return gShopName;
	}

	public void setgShopName(String gShopName) {
		this.gShopName = gShopName;
	}

	@Override
	public String toString() {
		return "ChargeDTO [gId=" + gId + ", gAdminId=" + gAdminId + ", gBankId=" + gBankId + ", gTotalPrice="
				+ gTotalPrice + ", gDate=" + gDate + ", gState=" + gState + ", gInvoiceId=" + gInvoiceId
				+ ", gShopName=" + gShopName + "]";
	}
}
