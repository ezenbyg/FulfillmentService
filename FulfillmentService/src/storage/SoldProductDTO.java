package storage;

public class SoldProductDTO {
	private int serial;
	private String soldInvId;
	private int soldShopId;
	private int soldTransportId;
	private int soldId;
	private int soldQuantity;
	private int soldTotalPrice;
	private String soldDate;
	private String chargeState;
	private String supplierState;
	private String transportState;
	private String soldShopName;
	
	public SoldProductDTO() {}

	public SoldProductDTO(String soldInvId, int soldShopId, int soldTransportId, int soldId, int soldQuantity,
			int soldTotalPrice, String soldDate) {
		super();
		this.soldInvId = soldInvId;
		this.soldShopId = soldShopId;
		this.soldTransportId = soldTransportId;
		this.soldId = soldId;
		this.soldQuantity = soldQuantity;
		this.soldTotalPrice = soldTotalPrice;
		this.soldDate = soldDate;
	}

	public String getSoldInvId() {
		return soldInvId;
	}

	public void setSoldInvId(String soldInvId) {
		this.soldInvId = soldInvId;
	}

	public int getSoldShopId() {
		return soldShopId;
	}

	public void setSoldShopId(int soldShopId) {
		this.soldShopId = soldShopId;
	}

	public int getSoldTransportId() {
		return soldTransportId;
	}

	public void setSoldTransportId(int soldTransportId) {
		this.soldTransportId = soldTransportId;
	}

	public int getSoldId() {
		return soldId;
	}

	public void setSoldId(int soldId) {
		this.soldId = soldId;
	}

	public int getSoldQuantity() {
		return soldQuantity;
	}

	public void setSoldQuantity(int soldQuantity) {
		this.soldQuantity = soldQuantity;
	}

	public int getSoldTotalPrice() {
		return soldTotalPrice;
	}

	public void setSoldTotalPrice(int soldTotalPrice) {
		this.soldTotalPrice = soldTotalPrice;
	}

	public String getSoldDate() {
		return soldDate;
	}

	public void setSoldDate(String soldDate) {
		this.soldDate = soldDate;
	}

	public String getChargeState() {
		return chargeState;
	}

	public void setChargeState(String chargeState) {
		this.chargeState = chargeState;
	}

	public String getSupplierState() {
		return supplierState;
	}

	public void setSupplierState(String supplierState) {
		this.supplierState = supplierState;
	}

	public String getTransportState() {
		return transportState;
	}

	public void setTransportState(String transportState) {
		this.transportState = transportState;
	}

	public String getSoldShopName() {
		return soldShopName;
	}

	public void setSoldShopName(String soldShopName) {
		this.soldShopName = soldShopName;
	}

	@Override
	public String toString() {
		return "SoldProductDTO [serial=" + serial + ", soldInvId=" + soldInvId + ", soldShopId=" + soldShopId
				+ ", soldTransportId=" + soldTransportId + ", soldId=" + soldId + ", soldQuantity=" + soldQuantity
				+ ", soldTotalPrice=" + soldTotalPrice + ", soldDate=" + soldDate + ", chargeState=" + chargeState
				+ ", supplierState=" + supplierState + ", transportState=" + transportState + ", soldShopName="
				+ soldShopName + "]";
	}
}
