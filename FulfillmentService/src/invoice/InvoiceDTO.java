package invoice;

public class InvoiceDTO {
	private int vId;
	private String vName;
	private String vTel;
	private String vAddress;
	private int vProductId;
	private String vProductName;
	private int vQuantity;
	private String vDate;
	private int vAdminId;
	private String vState;
	private int vPrice;
	
	public InvoiceDTO() {}

	public InvoiceDTO(int vId, String vName, String vTel, String vAddress, int vProductId, String vProductName, int vQuantity,
			String vDate, int vAdminId, String vState) {
		super();
		this.vId = vId;
		this.vName = vName;
		this.vTel = vTel;
		this.vAddress = vAddress;
		this.vProductId = vProductId;
		this.vProductName = vProductName;
		this.vQuantity = vQuantity;
		this.vDate = vDate;
		this.vAdminId = vAdminId;
	}
	
	public InvoiceDTO(String vName, String vTel, String vAddress, int vProductId, String vProductName, int vQuantity,
			String vDate, int vAdminId) {
		super();
		this.vName = vName;
		this.vTel = vTel;
		this.vAddress = vAddress;
		this.vProductId = vProductId;
		this.vProductName = vProductName;
		this.vQuantity = vQuantity;
		this.vDate = vDate;
		this.vAdminId = vAdminId;
	}

	public int getvId() {
		return vId;
	}

	public void setvId(int vId) {
		this.vId = vId;
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

	public int getvProductId() {
		return vProductId;
	}

	public void setvProductId(int vProductId) {
		this.vProductId = vProductId;
	}

	public String getvProductName() {
		return vProductName;
	}

	public void setvProductName(String vProductName) {
		this.vProductName = vProductName;
	}

	public int getvQuantity() {
		return vQuantity;
	}

	public void setvQuantity(int vQuantity) {
		this.vQuantity = vQuantity;
	}

	public String getvDate() {
		return vDate;
	}

	public void setvDate(String vDate) {
		this.vDate = vDate;
	}

	public int getvAdminId() {
		return vAdminId;
	}

	public void setvAdminId(int vAdminId) {
		this.vAdminId = vAdminId;
	}

	public String getvState() {
		return vState;
	}

	public void setvState(String vState) {
		this.vState = vState;
	}
	
	public int getvPrice() {
		return vPrice;
	}

	public void setvPrice(int vPrice) {
		this.vPrice = vPrice;
	}

	@Override
	public String toString() {
		return "InvoiceDTO [vId=" + vId + ", vName=" + vName + ", vTel=" + vTel + ", vAddress=" + vAddress
				+ ", vProductId=" + vProductId + ", vProductName=" + vProductName + ", vQuantity=" + vQuantity
				+ ", vDate=" + vDate + ", vAdminId=" + vAdminId + ", vState=" + vState + "]";
	}
}
