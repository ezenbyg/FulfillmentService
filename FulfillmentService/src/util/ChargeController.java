package util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bank.BankDAO;
import bank.BankDTO;
import charge.ChargeDAO;
import charge.ChargeDTO;
import state.AdminName;
import state.ChargeState;
import storage.SoldProductDAO;
import storage.SoldProductDTO;

public class ChargeController {
	private static final Logger LOG = LoggerFactory.getLogger(ChargeController.class);
	DateController dc = new DateController();
	BankAccount account = new BankAccount();
	SoldProductDAO spDao = new SoldProductDAO();
	List<SoldProductDTO> spList = null;
	ChargeDAO gDao = new ChargeDAO();
	ChargeDTO charge = new ChargeDTO();
	BankDTO bank = new BankDTO();
	BankDAO bDao = new BankDAO();
	
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
	
	// 청구 가능시간 여부 판단 메소드
	public boolean isPossibleChargeByDate(String date) {
		String strDate = dc.getChargeTime(date.substring(0, 7));
		if(date.substring(0, 7).compareTo(strDate) >= 0) return false;
		else return true;
	}
	
	// 청구완료처리
	public void processConfirmCharge(int gId, int gAdminId, String gBankId, int gTotalPrice, String gDate) {
		gDao.updateChargeState(String.valueOf(ChargeState.청구완료), gId);
		SoldProductDTO invoice = spDao.getOneInvoiceId(gAdminId, gDate);
		LOG.debug(invoice.getSoldInvId());
		spDao.updateSoldProductState(String.valueOf(ChargeState.청구완료), gAdminId, gDate);
		// 실제로 적용해야 하는 코드
		// spDao.updateSoldProductState(String.valueOf(ChargeState.청구완료), gAdminId, beforeMonth());
		bank = bDao.getOneBankList(gBankId);
		bDao.updateBank(bank, gTotalPrice, "+");
		bank = bDao.getOneBankList(gAdminId);
		bDao.updateBank(bank, gTotalPrice, "-");
	}
	
	// 청구요청처리
	public void processRequestCharge(int soldShopId, String soldDate) {
		int total = 0;
		boolean chargeFlag = false;
		spList = spDao.selectAllListsByShopId(soldShopId);
		// SoldProduct의 chargeState 변경 및 charge 테이블에 삽입
		for(SoldProductDTO spDto : spList) {
			if(isPossibleChargeByState(spDto.getChargeState())) {
				if(isPossibleChargeByDate(soldDate)) {
					spDao.updateSoldProductState(String.valueOf(ChargeState.청구요청), spDto.getSoldInvId());
					total += spDto.getSoldTotalPrice()*1.1+10000;
					chargeFlag = true;
				} else chargeFlag = false;
			} else chargeFlag = false;
		}
		if(chargeFlag == true) {
			charge = new ChargeDTO(soldShopId, account.getAccount(String.valueOf(AdminName.ezen)), total, dc.currentTime());
			gDao.addChargeList(charge);
		}
	}
}
