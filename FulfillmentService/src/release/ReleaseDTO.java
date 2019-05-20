package release;

public class ReleaseDTO {
	private int rId;
	private String rInvoiceId;
	private int rTransportId;
	private String rDate;
	private String rState;
	private String rTransportName;
	public ReleaseDTO() {}

	public ReleaseDTO(String rInvoiceId, int rTransportId, String rDate) {
		super();
		this.rInvoiceId = rInvoiceId;
		this.rTransportId = rTransportId;
		this.rDate = rDate;
	}

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

	public String getrState() {
		return rState;
	}

	public void setrState(String rState) {
		this.rState = rState;
	}

	public String getrTransportName() {
		return rTransportName;
	}

	public void setrTransportName(String rTransportName) {
		this.rTransportName = rTransportName;
	}

	@Override
	public String toString() {
		return "ReleaseDTO [rId=" + rId + ", rInvoiceId=" + rInvoiceId + ", rTransportId=" + rTransportId + ", rDate="
				+ rDate + ", rState=" + rState + ", rTransportName=" + rTransportName + "]";
	}
}
