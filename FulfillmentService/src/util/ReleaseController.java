package util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import invoice.InvoiceDAO;
import invoice.InvoiceDTO;
import release.ReleaseDAO;
import release.ReleaseDTO;
import storage.StorageDAO;
import storage.StorageDTO;

public class ReleaseController {
	private static final Logger LOG = LoggerFactory.getLogger(ReleaseController.class);
	DateController dc = new DateController();
	InvoiceDAO vDao = new InvoiceDAO();
	ReleaseDAO rDao = new ReleaseDAO();
	ReleaseDTO release = null;
	StorageDAO pDao = new StorageDAO();
	StorageDTO product = new StorageDTO();
	ArrayList<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
	ArrayList<InvoiceDTO> stateList = new ArrayList<InvoiceDTO>();
	
	// 제품 상태에 따른 출고 가능 여부 판단 메소드
	public boolean isPossibleReleaseByProductState(String pState) {
		ProductState state = ProductState.valueOf(pState);
		LOG.debug(String.valueOf(state));
		switch(state) {
		case P : 
			return true;
		case 재고부족 : 
			return false;
		case 재고부족예상 :
			return true;
		}
		return false;
	}
	
	// 시간에 따른 출고 가능 여부 판단 메소드 
	public boolean isPossibleReleaseByDate(String date) {
		LOG.debug(date);
		String strDate = dc.getToday();
		strDate += dc.getNumericTime(dc.getAmPm(dc.transStringToDate(date)));
		LOG.debug("strDate : " + strDate);
		if(date.compareTo(strDate) >= 0) return false;
		else return true;
	}
	
	// 송장 상태에 따른 출고 가능 여부 판단 메소드
	public boolean isPossibleReleaseByInvoiceState(String vState) {
		InvoiceState state = InvoiceState.valueOf(vState);
		LOG.debug(String.valueOf(state));
		switch(state) {
		case 출고대기 : 
			return true;
		case 출고보류 : 
			return true;
		case 출고완료 :
			return false;
		}
		return false;
	}
	
	// 출고 처리
	public void processRelease(String date) {
		boolean releaseFlag = false;
		vList = vDao.getInvoiceListsForRelease(date); // 오늘 날짜에 해당하는 송장 리스트 가져오기
		for(InvoiceDTO ivto : vList) {
			stateList = vDao.getAllInvoiceListsById(ivto.getvId()); // 송장에 해당하는 제품 상태 얻어오기
			if(isPossibleReleaseByDate(ivto.getvDate()) == true) {
				for(InvoiceDTO vDto : stateList) { 
					if(isPossibleReleaseByInvoiceState(vDto.getvState()) == true) {
						if(isPossibleReleaseByProductState(vDto.getvProductState()) == true) {
							releaseFlag = true;
						} else {
							releaseFlag = false;
							break;
						}
					}
				}
			}
			if(releaseFlag == true) { // 출고 조건을 만족할 때 -> 출고DB에 삽입 & 송장상태 변경
				release = new ReleaseDTO(ivto.getvId(), ivto.getVlogisId(), dc.currentTime());
				rDao.addReleaseList(release);
				LOG.debug("출고상태 : " + String.valueOf(InvoiceState.출고완료));
				vDao.updateInvoiceState(String.valueOf(InvoiceState.출고완료), ivto.getvId());
				for(InvoiceDTO vDto : stateList) {
					// 출고 후에 제품 상태와 수량 변경 
					LOG.debug(String.valueOf(vDto.getvProductId()));
					product = pDao.getOneProductById(vDto.getvProductId()); 
					pDao.updateStorage(product.getpQuantity()-vDto.getvQuantity(), vDto.getvProductState(), vDto.getvProductId());
				}
				// 다음 송장에 대한 제품 수량과 비교하여 상태 변경
				List<InvoiceDTO> testList = vDao.getAllInvoiceLists();
				for(InvoiceDTO vto : testList) {
					vDao.changeProductState(vto.getvProductId(), vto.getvQuantity());
				}
			} else vDao.updateInvoiceState(String.valueOf(InvoiceState.출고보류), ivto.getvId());
		}
	}
}
