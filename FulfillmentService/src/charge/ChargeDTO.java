package charge;

public class ChargeDTO {
	private int gAdiminId;
	private int gInvoiceId;
	private String gProductName;
	private String gDate;
	private int gQuantity;
	private int gPrice;
	
	public ChargeDTO(int gAdiminId, int gInvoiceId, String gProductName, int gQuantity,int gPrice, String gDate) {
		this.gAdiminId = gAdiminId;
		this.gInvoiceId = gInvoiceId;
		this.gProductName = gProductName;
		this.gQuantity = gQuantity;
		this.gPrice = gPrice;
		this.gDate = gDate;
	}
	
	public ChargeDTO(int gInvoiceId, String gProductName, int gQuantity,int gPrice, String gDate) {
		this.gInvoiceId = gInvoiceId;
		this.gProductName = gProductName;
		this.gQuantity = gQuantity;
		this.gPrice = gPrice;
		this.gDate = gDate;
	}
	
	public ChargeDTO() {}
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

	public int getgAdiminId() {
		return gAdiminId;
	}
	public void setgAdiminId(int gAdiminId) {
		this.gAdiminId = gAdiminId;
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

	@Override
	public String toString() {
		return "ChargeDTO [gAdiminId=" + gAdiminId + ", gInvoiceId=" + gInvoiceId + ", gProductName=" + gProductName
				+ ", gDate=" + gDate + ", gQuantity=" + gQuantity + ", gPrice=" + gPrice + "]";
	}
	
}
