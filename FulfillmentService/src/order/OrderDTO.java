package order;

public class OrderDTO {
	
	private int oId;
	private int oAdminId;
	private int oProductId;
	private int oQuantity;
	private int oPrice;
	private int oTotalPrice;
	private String oDate;
	
	public OrderDTO(int oId, int oAdminId, int oProductId, int oQuantity, int oPrice, int oTotalPrice, String oDate) {
		super();
		this.oId = oId;
		this.oAdminId = oAdminId;
		this.oProductId = oProductId;
		this.oQuantity = oQuantity;
		this.oPrice = oPrice;
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

	public int getoPrice() {
		return oPrice;
	}

	public void setoPrice(int oPrice) {
		this.oPrice = oPrice;
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

	@Override
	public String toString() {
		return "OrderDTO [oId=" + oId + ", oAdminId=" + oAdminId + ", oProductId=" + oProductId + ", oQuantity="
				+ oQuantity + ", oPrice=" + oPrice + ", oTotalPrice=" + oTotalPrice + ", oDate=" + oDate + "]";
	}
}