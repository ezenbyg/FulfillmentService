package invoice;

public class InvoiceDTO {
	private int vId;
	private int vAdminId;
	private String vDate;
	private String vState;
	
	public InvoiceDTO() {}

	public InvoiceDTO(int vAdminId, String vDate) {
		super();
		this.vAdminId = vAdminId;
		this.vDate = vDate;
	}

	public int getvId() {
		return vId;
	}

	public void setvId(int vId) {
		this.vId = vId;
	}

	public int getvAdminId() {
		return vAdminId;
	}

	public void setvAdminId(int vAdminId) {
		this.vAdminId = vAdminId;
	}

	public String getvDate() {
		return vDate;
	}

	public void setvDate(String vDate) {
		this.vDate = vDate;
	}

	public String getvState() {
		return vState;
	}

	public void setvState(String vState) {
		this.vState = vState;
	}

	@Override
	public String toString() {
		return "InvoiceDTO [vId=" + vId + ", vAdminId=" + vAdminId + ", vDate=" + vDate + ", vState=" + vState + "]";
	}
}
