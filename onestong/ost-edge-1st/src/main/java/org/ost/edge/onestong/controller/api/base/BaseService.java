package org.ost.edge.onestong.controller.api.base;



public class BaseService {
	public static   Byte DAY  =0;
	public static Byte WEEK  =1;
	public static Byte MONTH  =2;
	
	
	public boolean  isAPiTOKENValid(String token){
		
		return "d278a85c-8fe4-11e4-bf65-234e21bb4733".equals(token);
	}
}
