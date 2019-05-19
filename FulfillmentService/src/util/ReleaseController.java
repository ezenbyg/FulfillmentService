package util;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import invoice.InvoiceDAO;
import invoice.InvoiceDTO;
import release.ReleaseDAO;
import release.ReleaseDTO;

public class ReleaseController {
	private static final Logger LOG = LoggerFactory.getLogger(ReleaseController.class);
	DateController dc = new DateController();
	InvoiceDAO vDao = new InvoiceDAO();
	ReleaseDAO rDao = new ReleaseDAO();
	ReleaseDTO release = null;
	ArrayList<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
	ArrayList<InvoiceDTO> stateList = new ArrayList<InvoiceDTO>();
	
	// 제품 상태에 따른 출고 가능 여부 판단 메소드
	public boolean isPossibleReleaseByState(String pState) {
		ProductState state = ProductState.valueOf(pState);
		switch(state) {
		case P : 
			return true;
		case 제품부족 : 
			return false;
		case 제품부족예상 :
			return true;
		}
		return true;
	}
	
	// 시간에 따른 출고 가능 여부 판단 메소드 
	public boolean isPossibleReleaseByDate(String date) {
		LOG.debug("isPossibleReleaseByDate() : " + date);
		String strDate = dc.getToday();
		strDate += dc.getNumericTime(dc.getAmPm(dc.transStringToDate(strDate)));
		if(date.compareTo(strDate) >= 0) return false;
		else return true;
	}
	
	// 출고 처리
	public void processRelease(String date) {
		boolean releaseFlag = false;
		vList = vDao.getInvoiceListsForRelease(date); // 오늘 날짜에 해당하는 송장 리스트 가져오기
		for(InvoiceDTO ivto : vList) {
			stateList = vDao.getAllInvoiceListsById(ivto.getvId()); // 송장에 해당하는 제품 상태 얻어오기
			if(isPossibleReleaseByDate(ivto.getvDate()) == true) {
				for(InvoiceDTO vDto : stateList) { 
					if(isPossibleReleaseByState(vDto.getvProductState()) == true) {
						releaseFlag = true;
					} else releaseFlag = false;
				}
			}
			if(releaseFlag == true) { // 출고 조건을 만족할 때 -> 출고DB에 삽입 & 송장상태 변경
				release = new ReleaseDTO(ivto.getvId(), ivto.getVlogisId(), dc.currentTime());
				rDao.addReleaseList(release);
				LOG.debug(String.valueOf(InvoiceState.출고완료));
				vDao.updateInvoiceState(String.valueOf(InvoiceState.출고완료), ivto.getvId());
			} else vDao.updateInvoiceState(String.valueOf(InvoiceState.출고보류), ivto.getvId());
		}
	}
}
