package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import state.AdminName;

public class BankAccount {
	private static final Logger LOG = LoggerFactory.getLogger(BankAccount.class);
	// 계좌번호 얻기
	public String getAccount(String name) {
		AdminName admin = AdminName.valueOf(name);
		LOG.debug(String.valueOf(admin));
		switch(admin) {
		case ezen :
			return "352-1142-3666-63";
		case JH쇼핑몰 : 
			return "110-1958-1241-68";
		case SW쇼핑몰 :
			return "220-2258-5461-90";
		case GJ쇼핑몰 : 
			return "151-7654-1289-81";
		case 무신사 :
			return "430-1158-3498-68";
		case 와구와구 : 
			return "111-1113-1234-13";
		case 하이마트 : 
			return "112-1234-1233-10";
		case 언더아머 :
			return "113-1312-1657-65";
		case 이케아 :
			return "110-1951-4651-43";
		case 경기물류 :
			return "110-1952-6761-68";
		case 중부물류 :
			return "110-1953-1433-98";
		case 영남물류 :
			return "110-1954-1109-37";
		default : return null;
		}
	}
}
