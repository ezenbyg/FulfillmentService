package invoice;

public class InvoiceProductDTO {
	private String pInvoiceId;
	private int ipProductId;
	private String ipProductName;
	private int ipQuantity;
	
	public InvoiceProductDTO() {}

	public InvoiceProductDTO(String pInvoiceId, int ipProductId, String ipProductName, int ipQuantity) {
		super();
		this.pInvoiceId = pInvoiceId;
		this.ipProductId = ipProductId;
		this.ipProductName = ipProductName;
		this.ipQuantity = ipQuantity;
	}

	public String getpInvoiceId() {
		return pInvoiceId;
	}

	public void setpInvoiceId(String pInvoiceId) {
		this.pInvoiceId = pInvoiceId;
	}

	public int getIpProductId() {
		return ipProductId;
	}

	public void setIpProductId(int ipProductId) {
		this.ipProductId = ipProductId;
	}

	public String getIpProductName() {
		return ipProductName;
	}

	public void setIpProductName(String ipProductName) {
		this.ipProductName = ipProductName;
	}

	public int getIpQuantity() {
		return ipQuantity;
	}

	public void setIpQuantity(int ipQuantity) {
		this.ipQuantity = ipQuantity;
	}

	@Override
	public String toString() {
		return "InvoiceProductDTO [pInvoiceId=" + pInvoiceId + ", ipProductId=" + ipProductId + ", ipProductName="
				+ ipProductName + ", ipQuantity=" + ipQuantity + "]";
	}
}
