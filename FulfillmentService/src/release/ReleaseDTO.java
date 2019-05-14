package release;

public class ReleaseDTO {
	private int rId;
	private int rTransportId;
	private int rShoppingId;
	private int rInvoiceId;
	private String rName;
	private String rTel;
	private String rAddress;
	private String rProductName;
	private int rQuantity;
	private String rDate;
	private int rPrice;
	private String rState;
	
	public ReleaseDTO() {}

	public ReleaseDTO(int rTransportId, int rShoppingId, int rInvoiceId, String rName, String rTel, String rAddress,
			String rProductName, int rQuantity, String rDate, int rPrice) {
		super();
		this.rTransportId = rTransportId;
		this.rShoppingId = rShoppingId;
		this.rInvoiceId = rInvoiceId;
		this.rName = rName;
		this.rTel = rTel;
		this.rAddress = rAddress;
		this.rProductName = rProductName;
		this.rQuantity = rQuantity;
		this.rDate = rDate;
		this.rPrice = rPrice;
	}
	
	public ReleaseDTO(int rTransportId, int rShoppingId, int rInvoiceId, String rName, String rTel, String rAddress,
			String rProductName, int rQuantity, String rDate, int rPrice, String rState) {
		super();
		this.rTransportId = rTransportId;
		this.rShoppingId = rShoppingId;
		this.rInvoiceId = rInvoiceId;
		this.rName = rName;
		this.rTel = rTel;
		this.rAddress = rAddress;
		this.rProductName = rProductName;
		this.rQuantity = rQuantity;
		this.rDate = rDate;
		this.rPrice = rPrice;
		this.rState = rState;
	}

	public int getrPrice() {
		return rPrice;
	}

	public void setrPrice(int rPrice) {
		this.rPrice = rPrice;
	}

	public int getrId() {
		return rId;
	}

	public void setrId(int rId) {
		this.rId = rId;
	}

	public int getrTransportId() {
		return rTransportId;
	}

	public void setrTransportId(int rTransportId) {
		this.rTransportId = rTransportId;
	}

	public int getrShoppingId() {
		return rShoppingId;
	}

	public void setrShoppingId(int rShoppingId) {
		this.rShoppingId = rShoppingId;
	}

	public int getrInvoiceId() {
		return rInvoiceId;
	}

	public void setrInvoiceId(int rInvoiceId) {
		this.rInvoiceId = rInvoiceId;
	}

	public String getrName() {
		return rName;
	}

	public void setrName(String rName) {
		this.rName = rName;
	}

	public String getrTel() {
		return rTel;
	}

	public void setrTel(String rTel) {
		this.rTel = rTel;
	}

	public String getrAddress() {
		return rAddress;
	}

	public void setrAddress(String rAddress) {
		this.rAddress = rAddress;
	}

	public String getrProductName() {
		return rProductName;
	}

	public void setrProductName(String rProductName) {
		this.rProductName = rProductName;
	}

	public int getrQuantity() {
		return rQuantity;
	}

	public void setrQuantity(int rQuantity) {
		this.rQuantity = rQuantity;
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
		return "ReleaseDTO [rId=" + rId + ", rTransportId=" + rTransportId + ", rShoppingId=" + rShoppingId
				+ ", rInvoiceId=" + rInvoiceId + ", rName=" + rName + ", rTel=" + rTel + ", rAddress=" + rAddress
				+ ", rProductName=" + rProductName + ", rQuantity=" + rQuantity + ", rDate=" + rDate + ", rPrice="
				+ rPrice + ", rState=" + rState + "]";
	}
}
