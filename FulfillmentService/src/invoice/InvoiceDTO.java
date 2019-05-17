package invoice;

public class InvoiceDTO {
	private String vId;
	private int vAdminId;
	private String vShopName;
	private String vName;
	private String vTel;
	private String vAddress;
	private String vDate;
	private int vPrice;
	private String vState;
	private int vlogisId;
	private int vQuantity;
	private int vProductId;
	private String vProductName;
	private String vTransportName;
	private String vProductState;
	private String vlogisName;
	
	public InvoiceDTO() {}

	public InvoiceDTO(String vId, int vAdminId, String vShopName, String vName, String vTel, String vAddress, String vDate, int vPrice, String vState) {
		super();
		this.vId = vId;
		this.vAdminId = vAdminId;
		this.vShopName = vShopName;
		this.vName = vName;
		this.vTel = vTel;
		this.vAddress = vAddress;
		this.vDate = vDate;
		this.vPrice = vPrice;
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

	public int getvPrice() {
		return vPrice;
	}

	public void setvPrice(int vPrice) {
		this.vPrice = vPrice;
	}

	public String getvState() {
		return vState;
	}

	public void setvState(String vState) {
		this.vState = vState;
	}

	public int getvQuantity() {
		return vQuantity;
	}

	public void setvQuantity(int vQuantity) {
		this.vQuantity = vQuantity;
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

	public String getvTransportName() {
		return vTransportName;
	}

	public void setvTransportName(String vTransportName) {
		this.vTransportName = vTransportName;
	}

	public int getVlogisId() {
		return vlogisId;
	}

	public void setVlogisId(int vlogisId) {
		this.vlogisId = vlogisId;
	}

	public String getvProductState() {
		return vProductState;
	}

	public void setvProductState(String vProductState) {
		this.vProductState = vProductState;
	}

	public String getVlogisName() {
		return vlogisName;
	}

	public void setVlogisName(String vlogisName) {
		this.vlogisName = vlogisName;
	}

	@Override
	public String toString() {
		return "InvoiceDTO [vId=" + vId + ", vAdminId=" + vAdminId + ", vShopName=" + vShopName + ", vName=" + vName
				+ ", vTel=" + vTel + ", vAddress=" + vAddress + ", vDate=" + vDate + ", vPrice=" + vPrice + ", vState="
				+ vState + ", vlogisId=" + vlogisId + ", vQuantity=" + vQuantity + ", vProductId=" + vProductId
				+ ", vProductName=" + vProductName + ", vTransportName=" + vTransportName + ", vProductState="
				+ vProductState + ", vlogisName=" + vlogisName + "]";
	}
}
