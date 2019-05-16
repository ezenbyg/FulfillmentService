package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import admin.AdminDAO;
import admin.AdminDTO;
import invoice.InvoiceDAO;
import invoice.InvoiceDTO;
import invoice.InvoiceProductDTO;
import storage.StorageDAO;
import storage.StorageDTO;

public class FileController {
	private static final Logger LOG = LoggerFactory.getLogger(InvoiceDAO.class);
	ArrayList<InvoiceDTO> invoiceList = new ArrayList<InvoiceDTO>();
	ArrayList<InvoiceProductDTO> productList = new ArrayList<InvoiceProductDTO>();
	ArrayList<String> fullFileName = new ArrayList<String>();
	ArrayList<String> splitFileName = new ArrayList<String>();
	ArrayList<String> filePath = new ArrayList<String>();
	StorageDAO pDao = new StorageDAO();
	StorageDTO pDto = new StorageDTO();
	AdminDAO aDao = new AdminDAO();
	AdminDTO admin = new AdminDTO();
	InvoiceDTO invoice = new InvoiceDTO();
	InvoiceDAO vDao = new InvoiceDAO();
	
	// 송장 읽고 DB에 넣기
	public void readCSV() {
		// 해당 디렉터리에 있는 파일 목록 읽기 
		String path = "C:\\Temp\\shop\\";
		File dir = new File(path);
		File[] fileList = dir.listFiles();

		int number = 0; 
		for(File file : fileList) {
			if(file.isFile()) {
				fullFileName.add(file.getName());
				filePath.add(path+fullFileName.get(number));
				StringTokenizer st = new StringTokenizer(fullFileName.get(number), ".");
				splitFileName.add(st.nextToken()); 
				admin = aDao.getOneAdminByName(splitFileName.get(number).substring(12));
				LOG.debug(splitFileName.get(number).substring(0, 12));
				LOG.debug(splitFileName.get(number));
				invoice.setvId(createInvoiceNumber(splitFileName.get(number)));
				LOG.debug(createInvoiceNumber(splitFileName.get(number)));
				invoice.setvAdminId(admin.getaId());
				invoice.setvShopName(splitFileName.get(number).substring(12));
				String year = splitFileName.get(number).substring(0, 4);
				String month = splitFileName.get(number).substring(4, 6);
				String date = splitFileName.get(number).substring(6, 8);
				String hour = splitFileName.get(number).substring(8, 10);
				String minute = splitFileName.get(number).substring(10, 12);
				LOG.debug(year+"-"+month+"-"+date+" "+hour+":"+minute);
				invoice.setvDate(year+"-"+month+"-"+date+" "+hour+":"+minute);
				LOG.debug(splitFileName.get(number).substring(0, 12));
			}
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
	                    new FileInputStream(filePath.get(number)), "euc-kr"));
	            String line = "";
	            int count = 0;
	            
	            while ((line = br.readLine()) != null) { // 파일 읽기
	            	count++;
	            	InvoiceProductDTO product = new InvoiceProductDTO();
	            	product.setpInvoiceId(invoice.getvId());
	                String[] token = line.split(",", -1);
	                for(int p=0; p<5; p++) {
	                	if((token[p] != null) && !(token[p].equals(" ")) && !(token[p].equals(""))) {
	                		LOG.debug(token[p] + " ");
	                		System.out.println(token[p] + " ");
							if(p==0)invoice.setvName(token[p]);
							if(p==1)invoice.setvTel(token[p]);
							if(p==2)invoice.setvAddress(token[p]);
							if(p==3) {
								product.setIpProductId(Integer.parseInt(token[p]));
								pDto = pDao.getOneProductById(Integer.parseInt(token[p]));
								invoice.setvPrice(pDto.getpPrice());
							}
							if(p==4)product.setIpQuantity(Integer.parseInt(token[p]));
	                	}
	                }
	                if(count==1) vDao.addInvoice(invoice);
	                vDao.addInvoiceProduct(product);
	            }
				moveDirectory(); // 읽은 파일들을 폴더 이동 시킨다.
				number++;
	            br.close();
			}
	        catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
	
	public void moveDirectory() {
		File folder1 = new File("C:\\Temp\\complete");
		File folder2 = new File("C:\\Temp\\shop");
		copy(folder1, folder2);
		delete(folder1.toString());
	}
	
	public static void copy(File sourceF, File targetF){
		File[] target_file = sourceF.listFiles();
		for (File file : target_file) {
			File temp = new File(targetF.getAbsolutePath() + File.separator + file.getName());
			if(file.isDirectory()){
				temp.mkdir();
				copy(file, temp);
			} else {
			        FileInputStream fis = null;
				FileOutputStream fos = null;
				try {
					fis = new FileInputStream(file);
					fos = new FileOutputStream(temp) ;
					byte[] b = new byte[4096];
					int cnt = 0;
					while((cnt=fis.read(b)) != -1){
						fos.write(b, 0, cnt);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					try {
						fis.close();
						fos.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
						
				}
			}
		   }
	    }
		
    public static void delete(String path) {
		File folder = new File(path);
		try {
			if(folder.exists()){
			    File[] folder_list = folder.listFiles();
					
			    for (int i = 0; i < folder_list.length; i++) {
				if(folder_list[i].isFile()) {
					folder_list[i].delete();
				}else {
					delete(folder_list[i].getPath());
				}
				folder_list[i].delete();
			    }
			}
		} catch (Exception e) {
			e.getStackTrace();
		}  
	}
	
	// 송장 번호 생성
    public String createInvoiceNumber(String fileName) {
		ArrayList<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
		String date = fileName.substring(0, 8);
		String name = fileName.substring(12);
		admin = aDao.getOneAdminByName(name);
		LOG.debug(String.valueOf(admin.getaId()).substring(0, 2));
		String idNum = String.valueOf(admin.getaId()).substring(0, 2);
		String count = String.valueOf((int)((Math.random()*89)+10));
		String invoiceNumber = date + idNum + count;
		vList = vDao.getAllInvoiceLists();
		boolean complete = true;
		
		LOG.debug(vList.toString());
		// 중복 방지
		while(complete) {
			if(vList.isEmpty()) { complete = false; break; }
			for(InvoiceDTO invoice : vList) {
				if(invoice.getvId().equals(invoiceNumber)) {
					invoiceNumber = date + String.valueOf((int)((Math.random()*89)+10)) + idNum;
				} else {
					complete = false;
					break;
				}
			}
		}
		LOG.debug(String.valueOf(invoiceNumber));
		return invoiceNumber;
	}
}
