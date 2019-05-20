package storage;

public class StorageDTO {
	private int pId;
	private String pName;
	private String pImgName;
	private int pPrice;
	private int pQuantity;
	private int pAdminId;
	private String pState;
	private String pSupplierName;
	private int pSupplierId;
	
	public StorageDTO() {}

	public StorageDTO(int pId, String pName, String pImgName, int pPrice, int pQuantity, int pAdminId) {
		super();
		this.pId = pId;
		this.pName = pName;
		this.pImgName = pImgName;
		this.pPrice = pPrice;
		this.pQuantity = pQuantity;
		this.pAdminId = pAdminId;
	}
	
	public StorageDTO(int pQuantity, String pState) {
		super();
		this.pQuantity = pQuantity;
		this.pState = pState;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getpImgName() {
		return pImgName;
	}

	public void setpImgName(String pImgName) {
		this.pImgName = pImgName;
	}

	public int getpPrice() {
		return pPrice;
	}

	public void setpPrice(int pPrice) {
		this.pPrice = pPrice;
	}

	public int getpQuantity() {
		return pQuantity;
	}

	public void setpQuantity(int pQuantity) {
		this.pQuantity = pQuantity;
	}

	public int getpAdminId() {
		return pAdminId;
	}

	public void setpAdminId(int pAdminId) {
		this.pAdminId = pAdminId;
	}

	public String getpState() {
		return pState;
	}

	public void setpState(String pState) {
		this.pState = pState;
	}

	public String getpSupplierName() {
		return pSupplierName;
	}

	public void setpSupplierName(String pSupplierName) {
		this.pSupplierName = pSupplierName;
	}

	public int getpSupplierId() {
		return pSupplierId;
	}

	public void setpSupplierId(int pSupplierId) {
		this.pSupplierId = pSupplierId;
	}

	@Override
	public String toString() {
		return "StorageDTO [pId=" + pId + ", pName=" + pName + ", pImgName=" + pImgName + ", pPrice=" + pPrice
				+ ", pQuantity=" + pQuantity + ", pAdminId=" + pAdminId + ", pState=" + pState + ", pSupplierName="
				+ pSupplierName + ", pSupplierId=" + pSupplierId + "]";
	}
}
