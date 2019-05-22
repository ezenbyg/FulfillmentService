package order;

public class OrderDTO {
	
	private int oId;
	private int oAdminId;
	private int oProductId;
	private int oQuantity;
	private int oTotalPrice;
	private String oDate;
	private String oState;
	private String oPayState;
	private String oAdminName;
	private String oProductName;
	private String oBankId;
	private int total;
	
	public OrderDTO(int oAdminId, int oProductId, int oQuantity, int oTotalPrice, String oDate) {
		super();
		this.oAdminId = oAdminId;
		this.oProductId = oProductId;
		this.oQuantity = oQuantity;
		this.oTotalPrice = oTotalPrice;
		this.oDate = oDate;
	}
	
	public OrderDTO() {}

	public int getoId() {
		return oId;
	}

	public void setoId(int oId) {
		this.oId = oId;
	}

	public int getoAdminId() {
		return oAdminId;
	}

	public void setoAdminId(int oAdminId) {
		this.oAdminId = oAdminId;
	}

	public int getoProductId() {
		return oProductId;
	}

	public void setoProductId(int oProductId) {
		this.oProductId = oProductId;
	}

	public int getoQuantity() {
		return oQuantity;
	}

	public void setoQuantity(int oQuantity) {
		this.oQuantity = oQuantity;
	}

	public int getoTotalPrice() {
		return oTotalPrice;
	}

	public void setoTotalPrice(int oTotalPrice) {
		this.oTotalPrice = oTotalPrice;
	}

	public String getoDate() {
		return oDate;
	}

	public void setoDate(String oDate) {
		this.oDate = oDate;
	}

	public String getoState() {
		return oState;
	}

	public void setoState(String oState) {
		this.oState = oState;
	}

	public String getoAdminName() {
		return oAdminName;
	}

	public void setoAdminName(String oAdminName) {
		this.oAdminName = oAdminName;
	}

	public String getoProductName() {
		return oProductName;
	}

	public void setoProductName(String oProductName) {
		this.oProductName = oProductName;
	}

	public String getoBankId() {
		return oBankId;
	}

	public void setoBankId(String oBankId) {
		this.oBankId = oBankId;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getoPayState() {
		return oPayState;
	}

	public void setoPayState(String oPayState) {
		this.oPayState = oPayState;
	}

	@Override
	public String toString() {
		return "OrderDTO [oId=" + oId + ", oAdminId=" + oAdminId + ", oProductId=" + oProductId + ", oQuantity="
				+ oQuantity + ", oTotalPrice=" + oTotalPrice + ", oDate=" + oDate + ", oState=" + oState
				+ ", oPayState=" + oPayState + ", oAdminName=" + oAdminName + ", oProductName=" + oProductName
				+ ", oBankId=" + oBankId + ", total=" + total + "]";
	}
}
