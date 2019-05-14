package storage;

public class SoldProductDTO {
	private int serial;
	private int soldAdminId;
	private int soldId;
	private String soldName;
	private int soldPrice;
	private int soldTotalPrice;
	private int soldQuantity;
	private String soldDate;
	
	public SoldProductDTO() {}

	public SoldProductDTO(int soldAdminId, int soldId, String soldName, int soldPrice, int soldTotalPrice, int soldQuantity, String soldDate) {
		super();
		this.soldAdminId = soldAdminId;
		this.soldId = soldId;
		this.soldName = soldName;
		this.soldPrice = soldPrice;
		this.soldTotalPrice = soldTotalPrice;
		this.soldQuantity = soldQuantity;
		this.soldDate = soldDate;
	}

	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}

	public int getSoldAdminId() {
		return soldAdminId;
	}

	public void setSoldAdminId(int soldAdminId) {
		this.soldAdminId = soldAdminId;
	}

	public int getSoldId() {
		return soldId;
	}

	public void setSoldId(int soldId) {
		this.soldId = soldId;
	}

	public String getSoldName() {
		return soldName;
	}

	public void setSoldName(String soldName) {
		this.soldName = soldName;
	}

	public int getSoldPrice() {
		return soldPrice;
	}

	public void setSoldPrice(int soldPrice) {
		this.soldPrice = soldPrice;
	}

	public int getSoldTotalPrice() {
		return soldTotalPrice;
	}

	public void setSoldTotalPrice(int soldTotalPrice) {
		this.soldTotalPrice = soldTotalPrice;
	}

	public int getSoldQuantity() {
		return soldQuantity;
	}

	public void setSoldQuantity(int soldQuantity) {
		this.soldQuantity = soldQuantity;
	}

	public String getSoldDate() {
		return soldDate;
	}

	public void setSoldDate(String soldDate) {
		this.soldDate = soldDate;
	}

	@Override
	public String toString() {
		return "SoldProductDTO [serial=" + serial + ", soldAdminId=" + soldAdminId + ", soldId=" + soldId
				+ ", soldName=" + soldName + ", soldPrice=" + soldPrice + ", soldTotalPrice=" + soldTotalPrice
				+ ", soldQuantity=" + soldQuantity + ", soldDate=" + soldDate + "]";
	}
}
