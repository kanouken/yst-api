package org.common.tools.push.getui;

import java.io.IOException;

import org.common.tools.push.OstCommonPush;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.APNPayload.SimpleAlertMsg;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class GeTuiPush implements OstCommonPush {

	public String host;
	public String appkey;
	public String master;
	public String appid;

	public GeTuiPush(String host, String appkey, String master, String appid) {
		super();
		this.host = host;
		this.appkey = appkey;
		this.master = master;
		this.appid = appid;
	}

	@Override
	public String apnPush(String content, String payLoadBody, String clientId) throws IOException {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setTransmissionType(2);
		template.setTransmissionContent(content);
		APNPayload apnpayload = new APNPayload();
		SimpleAlertMsg alertMsg = new SimpleAlertMsg(payLoadBody);
		apnpayload.setAlertMsg(alertMsg);
		apnpayload.setBadge(1);
		IGtPush push = new IGtPush(host, appkey, master);
		template.setAppId(appid);
		template.setAppkey(appkey);
		push.connect();
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		message.setOfflineExpireTime(1 * 1000 * 3600);
		apnpayload.addCustomMsg("payload", template.getTransmissionContent());
		template.setAPNInfo(apnpayload);
		message.setData(template);
		Target target1 = new Target();
		target1.setAppId(appid);
		// check target id
		if (clientId == null) {
			throw new IllegalArgumentException("push target id can not be null!");
		}
		target1.setClientId(clientId);
		IPushResult ret = push.pushMessageToSingle(message, target1);
		return ret.getResponse().toString();
	}

}
