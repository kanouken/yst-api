package org.notification.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.notification.model.PushBody;
import org.notification.model.PushGroup;
import org.notification.properties.GetuiApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.APNPayload.SimpleAlertMsg;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.google.gson.JsonObject;

import net.sf.json.JSONObject;

@Service
public class GeTuiService extends BaseService {
	private static Logger log = Logger.getLogger(GeTuiService.class);	
	

	@Autowired
	private  GetuiApplication application;

	public String push(PushBody body) throws IOException {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setTransmissionType(2);
		template.setTransmissionContent(body.getContent());
		APNPayload apnpayload = new APNPayload();
		SimpleAlertMsg alertMsg = new SimpleAlertMsg(body.getPayLoadBody());
		apnpayload.setAlertMsg(alertMsg);
		apnpayload.setBadge(1);
		JSONObject property = application.getProperty(body.getApplication());
		if (null == property) {
			log.warn("can not found property for application " + body.getApplication());
			throw new IllegalArgumentException("property for " + body.getApplication() + "can not found!");
		}
		log.debug("found property for " + body.getApplication() + " :" + property);
		IGtPush push = new IGtPush(property.getString("host"), property.getString("appkey"),
				property.getString("master"));
		template.setAppId(property.getString("appid"));
		template.setAppkey(property.getString("appkey"));
		push.connect();
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		message.setOfflineExpireTime(1 * 1000 * 3600);
		apnpayload.addCustomMsg("payload", template.getTransmissionContent());
		template.setAPNInfo(apnpayload);
		message.setData(template);
		Target target1 = new Target();
		target1.setAppId(property.getString("appid"));
		// check target id
		if (CollectionUtils.isEmpty(body.getCids())) {
			throw new IllegalArgumentException("push target id can not be null!");
		}
		target1.setClientId(body.getCids().get(0));
		log.debug("start push with params :[" + JSONObject.fromObject(body) + "]");
		IPushResult ret = push.pushMessageToSingle(message, target1);
		log.debug("end push receive from getui server :" + ret.getResponse());
		return ret.getResponse().toString();
	}

	public List<String> pushBatch(PushBody body) throws IOException {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setTransmissionType(2);
		template.setTransmissionContent(body.getContent());
		APNPayload apnpayload = new APNPayload();
		SimpleAlertMsg alertMsg = new SimpleAlertMsg(body.getPayLoadBody());
		apnpayload.setAlertMsg(alertMsg);
		apnpayload.setBadge(1);
		JSONObject property = application.getProperty(body.getApplication());
		if (null == property) {
			throw new IllegalArgumentException("property for " + body.getApplication() + "can not found!");
		}
		IGtPush push = new IGtPush(property.getString("host"), property.getString("appkey"),
				property.getString("master"));
		template.setAppId(property.getString("appid"));
		template.setAppkey(property.getString("appkey"));
		push.connect();
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		message.setOfflineExpireTime(1 * 1000 * 3600);
		apnpayload.addCustomMsg("payload", template.getTransmissionContent());
		template.setAPNInfo(apnpayload);
		message.setData(template);
		if (CollectionUtils.isEmpty(body.getCids())) {
			throw new IllegalArgumentException("push target id can not be null!");
		}
		List<String> retResults = new ArrayList<String>();
		log.info("start push with params :[" + JSONObject.fromObject(body) + "]");
		for (String cid : body.getCids()) {
			Target target1 = new Target();
			target1.setAppId(property.getString("appid"));
			target1.setClientId(cid);
			IPushResult ret = push.pushMessageToSingle(message, target1);
			retResults.add(ret.getResponse().toString());
		}
		log.info("end push with result :[" + retResults);
		return retResults;
	}

	
	//build group with tag
	public Object buildGroup(PushGroup body){	
		JSONObject property = application.getProperty(body.getApplication());
		if(null == property){
			throw new IllegalArgumentException("property for "+body.getApplication() +"can not found!");
		}
		IGtPush push = new IGtPush(property.getString("host"),
				property.getString("appkey"), property.getString("master"));
//		IGtPush push = new IGtPush(host,appKey,masterSecret);
		IQueryResult ret = null;
		String appId = property.getString("appid");
		try{
			List<String> tags = body.getTags();
			String[]cidss = body.getCids().split(",");
			if(cidss.length==1){
				String cidd="";
				 cidd = cidss[0].substring(3, cidss[0].length()-3);
				 ret = push.setClientTag(appId,cidd, tags);
					System.out.println(ret.getResponse().toString());
				      IPushResult result = push.getUserTags(appId, cidd);
				        System.out.println(result.getResponse().toString());
			}else{
			for(int i =0;i<cidss.length;i++ ){	
				//cid: cidss
				String cidd="";
				if(i==0){
					 cidd = cidss[i].substring(3, cidss[i].length());
				}else if(i==cidss.length-1){
					 cidd = cidss[i].substring(0, cidss[i].length()-3);
				}else{
					 cidd = cidss[i];
				}
				ret = push.setClientTag(appId,cidd, tags);
				System.out.println(ret.getResponse().toString());
			      IPushResult result = push.getUserTags(appId, cidd);
			        System.out.println(result.getResponse().toString());
			}}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;   
	}
	
	
	
	private  ITemplate buildNotificationTemplate(PushGroup body){
		JSONObject property = application.getProperty(body.getApplication());

		String payload = body.getPayLoadBody();//type#title#coupontypeId
		String[] payloads = payload.split("#");
		String type = payloads[0];
		String title = payloads[1];
		String groupId = payloads[2];
		String couponTypeId = null;
		if(payloads.length >3)
			couponTypeId = payloads[3];
		TransmissionTemplate template = new TransmissionTemplate();
		APNPayload apnpayload = new APNPayload();
	
        // 设置APPID与APPKEY
		template.setAppId(property.getString("appid"));
		template.setAppkey(property.getString("appkey"));
        // 设置通知栏标题与内容
        
//        template.setTitle(title);  //"请输入通知栏标题"
//        template.setText("请输入通知栏内容");
        // 配置通知栏图标
//        template.setLogo("icon.png");
        // 配置通知栏网络图标
//        template.setLogoUrl("");
//        // 设置通知是否响铃，震动，或者可清除
//        template.setIsRing(true);
//        template.setIsVibrate(true);
//        template.setIsClearable(true);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        //content   type#coupntyId#content
        JsonObject json = new JsonObject();
        json.addProperty("title", title);
        json.addProperty("type", type);
        json.addProperty("groupId", groupId);
        json.addProperty("couponTypeId", couponTypeId);
        json.addProperty("content", body.getContent());
//        StringBuilder content = new StringBuilder(type).append('#').append(couponTypeId==null?"":couponTypeId).append('#').append(body.getContent());
//        template.setTransmissionContent(content.toString());//"请输入您要透传的内容"
        template.setTransmissionContent(json.toString());
//    	SimpleAlertMsg alertMsg = new SimpleAlertMsg(body.getPayLoadBody());
        SimpleAlertMsg alertMsg = new SimpleAlertMsg(json.toString());
		apnpayload.setAlertMsg(alertMsg);
		template.setAPNInfo(apnpayload);

        return template;
	}
	
	
	public Object pushMessageToGroup(PushGroup body) {
		
		JSONObject property = application.getProperty(body.getApplication());
		if(null == property){
			throw new IllegalArgumentException("property for "+body.getApplication() +"can not found!");
		}
		IGtPush push = new IGtPush(property.getString("host"),
				property.getString("appkey"), property.getString("master"));
		
		
		ITemplate template = buildNotificationTemplate(body);
			
		//condition
        AppConditions cdt = new AppConditions(); 
        String tag = "[\""+body.getTags().get(0)+"\"]";
        List<String> tags = new ArrayList<String>();
        tags.add(tag);
        cdt.addCondition(AppConditions.TAG,tags);
		
        //applist
        List<String> appIdList = new ArrayList<String>();
        appIdList.add(property.getString("appid"));
        
        
        AppMessage message = new AppMessage();
        message.setData(template);
        // 设置消息离线，并设置离线时间
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        message.setConditions(cdt); 
        message.setAppIdList(appIdList);
        
        IPushResult ret = push.pushMessageToApp(message,"任务别名_toApp");  //,"任务别名_toApp"
        System.out.println(ret.getResponse().toString());
        return ret.getResponse();
	}

}
