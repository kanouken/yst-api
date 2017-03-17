//package com.oz.onestong.services.common.resouces;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import com.oz.onestong.tools.ResourceHelper;
//
//@Service
//public class ResourceService {
//	
//	
//	/**
//	 * 下载文件
//	 * @param filename 文件名
//	 * @return
//	 */
//	public ResponseEntity<byte[]> downloadFile(String filename,String type){
//		ResponseEntity<byte[]> response = null;
//		try {
//			response = ResourceHelper.downloadFile(filename,type);
//		} catch (Exception e) {
//			//logger.error("image not found", e);
////			try {
////				//response = ResourceHelper.downloadFile(Constants.FILE_NOT_FOUND_IMG);
////			} catch (IOException e1) {
////				//logger.error("file server connection fail", e);
////			} 
//		}
//		return response;
//	}
//}
