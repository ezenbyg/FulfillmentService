package util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import charge.ChargeDAO;
import charge.ChargeDTO;
import state.AdminName;
import state.ChargeState;
import storage.SoldProductDAO;
import storage.SoldProductDTO;

public class ChargeController {
	private static final Logger LOG = LoggerFactory.getLogger(ChargeController.class);
	DateController dc = new DateController();
	SoldProductDAO spDao = new SoldProductDAO();
	List<SoldProductDTO> spList = null;
	ChargeDAO cDao = new ChargeDAO();
	ChargeDTO charge = new ChargeDTO();
	
	// 청구 상태에 따른 청구 가능 여부 판단 메소드
	public boolean isPossibleChargeByState(String spState) {
		ChargeState state = ChargeState.valueOf(spState);
		LOG.debug(String.valueOf(state));
		switch(state) {
		case 미청구 : 
			return true;
		case 청구요청 : 
			return false;
		case 청구완료 :
			return false;
		}
		return false;
	}
	
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
	
	// 청구 가능시간 여부 판단 메소드
	public boolean isPossibleChargeByDate(String date) {
		String strDate = dc.getChargeTime(date.substring(0, 7));
		if(date.substring(0, 7).compareTo(strDate) >= 0) return false;
		else return true;
	}
	
	// 청구완료처리
	public void processConfirmCharge(String date, int spAdminId, int bankId, int spTotalPrice) {
		
	}
	
	// 청구요청처리
	public void processRequestCharge(int soldShopId) {
		int total = 0;
		boolean chargeFlag = false;
		spList = spDao.selectAllListsByShopId(soldShopId);
		// SoldProduct의 chargeState 변경 및 charge 테이블에 삽입
		for(SoldProductDTO spDto : spList) {
			if(isPossibleChargeByState(spDto.getChargeState())) {
				spDao.updateSoldProductState(String.valueOf(ChargeState.청구요청), spDto.getSoldInvId());
				total += spDto.getSoldTotalPrice()*1.1+10000;
				chargeFlag = true;
			} else chargeFlag = false;
		}
		if(chargeFlag == true) {
			charge = new ChargeDTO(soldShopId, getAccount(String.valueOf(AdminName.ezen)), total, dc.currentTime());
			cDao.addChargeList(charge);
		}
	}
}
