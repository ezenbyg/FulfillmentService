package invoice;

public class InvoiceHumanDTO {
	private int hInvoiceId;
	private String hName;
	private String hTel;
	private String hAddress;
	private String hDate;
	
	public InvoiceHumanDTO() {}
	
	public InvoiceHumanDTO(int hInvoiceId, String hName, String hTel, String hAddress, String hDate) {
		super();
		this.hInvoiceId = hInvoiceId;
		this.hName = hName;
		this.hTel = hTel;
		this.hAddress = hAddress;
		this.hDate = hDate;
	}

	public int gethInvoiceId() {
		return hInvoiceId;
	}
	
	public void sethInvoiceId(int hInvoiceId) {
		this.hInvoiceId = hInvoiceId;
	}
	
	public String gethName() {
		return hName;
	}
	
	public void sethName(String hName) {
		this.hName = hName;
	}
	
	public String gethTel() {
		return hTel;
	}

	public void sethTel(String hTel) {
		this.hTel = hTel;
	}

	public String gethAddress() {
		return hAddress;
	}
	
	public void sethAddress(String hAddress) {
		this.hAddress = hAddress;
	}
	
	public String gethDate() {
		return hDate;
	}
	
	public void sethDate(String hDate) {
		this.hDate = hDate;
	}

	@Override
	public String toString() {
		return "InvoiceHumanDTO [hInvoiceId=" + hInvoiceId + ", hName=" + hName + ", hTel=" + hTel + ", hAddress="
				+ hAddress + ", hDate=" + hDate + "]";
	}
}
