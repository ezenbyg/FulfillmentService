package release;

public class ReleaseDTO {
	private int rId;
	private String rInvoiceId;
	private String rTransportName;
	private String rDate;
	private String rState;
	
	public ReleaseDTO() {}

	public int getrId() {
		return rId;
	}

	public void setrId(int rId) {
		this.rId = rId;
	}

	public String getrInvoiceId() {
		return rInvoiceId;
	}

	public void setrInvoiceId(String rInvoiceId) {
		this.rInvoiceId = rInvoiceId;
	}

	public String getrTransportName() {
		return rTransportName;
	}

	public void setrTransportName(String rTransportName) {
		this.rTransportName = rTransportName;
	}

	public String getrDate() {
		return rDate;
	}

	public void setrDate(String rDate) {
		this.rDate = rDate;
	}

	public String getrState() {
		return rState;
	}

	public void setrState(String rState) {
		this.rState = rState;
	}

	@Override
	public String toString() {
		return "ReleaseDTO [rId=" + rId + ", rInvoiceId=" + rInvoiceId + ", rTransportName=" + rTransportName
				+ ", rDate=" + rDate + ", rState=" + rState + "]";
	}
}
