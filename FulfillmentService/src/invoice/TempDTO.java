package invoice;

public class TempDTO {
	private String tId;
	private int tAdminId;
	private String tShopName;
	private String tDate;
	
	public TempDTO() {}

	public TempDTO(String tId, int tAdminId, String tShopName, String tDate) {
		super();
		this.tId = tId;
		this.tAdminId = tAdminId;
		this.tShopName = tShopName;
		this.tDate = tDate;
	}

	public String gettId() {
		return tId;
	}

	public void settId(String tId) {
		this.tId = tId;
	}

	public int gettAdminId() {
		return tAdminId;
	}

	public void settAdminId(int tAdminId) {
		this.tAdminId = tAdminId;
	}

	public String gettShopName() {
		return tShopName;
	}

	public void settShopName(String tShopName) {
		this.tShopName = tShopName;
	}

	public String gettDate() {
		return tDate;
	}

	public void settDate(String tDate) {
		this.tDate = tDate;
	}

	@Override
	public String toString() {
		return "TempDTO [tId=" + tId + ", tAdminId=" + tAdminId + ", tShopName=" + tShopName + ", tDate=" + tDate + "]";
	}
}
