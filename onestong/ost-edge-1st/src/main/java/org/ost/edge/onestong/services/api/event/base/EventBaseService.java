package org.ost.edge.onestong.services.api.event.base;

import org.springframework.stereotype.Service;

@Service
public abstract class EventBaseService {
	
	public static   Byte DAY  =0;
	public static Byte WEEK  =1;
	public static Byte MONTH  =2;
	public abstract void updateComment();
}
