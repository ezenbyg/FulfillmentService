package invoice;

public class InvoiceDTO {
	private String vId;
	private int vAdminId;
	private String vShopName;
	private String vName;
	private String vTel;
	private String vAddress;
	private String vDate;
	private String vState;
	
	public InvoiceDTO() {}

	public InvoiceDTO(String vId, int vAdminId, String vShopName, String vName, String vTel, String vAddress, String vDate, String vState) {
		super();
		this.vId = vId;
		this.vAdminId = vAdminId;
		this.vShopName = vShopName;
		this.vName = vName;
		this.vTel = vTel;
		this.vAddress = vAddress;
		this.vDate = vDate;
		this.vState = vState;
	}

	public String getvId() {
		return vId;
	}

	public void setvId(String vId) {
		this.vId = vId;
	}

	public int getvAdminId() {
		return vAdminId;
	}

	public void setvAdminId(int vAdminId) {
		this.vAdminId = vAdminId;
	}

	public String getvShopName() {
		return vShopName;
	}

	public void setvShopName(String vShopName) {
		this.vShopName = vShopName;
	}

	public String getvName() {
		return vName;
	}

	public void setvName(String vName) {
		this.vName = vName;
	}

	public String getvTel() {
		return vTel;
	}

	public void setvTel(String vTel) {
		this.vTel = vTel;
	}

	public String getvAddress() {
		return vAddress;
	}

	public void setvAddress(String vAddress) {
		this.vAddress = vAddress;
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
		return "InvoiceDTO [vId=" + vId + ", vAdminId=" + vAdminId + ", vShopName=" + vShopName + ", vName=" + vName
				+ ", vTel=" + vTel + ", vAddress=" + vAddress + ", vDate=" + vDate + ", vState=" + vState + "]";
	}
}
