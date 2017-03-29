package org.notification.service;

import java.io.IOException;
import java.util.List;

import org.common.tools.OperateResult;
import org.notification.client.CrmServiceClient;
import org.notification.model.NotificationType;
import org.notification.model.PushBody;
import org.ost.entity.notification.visit.UnSignOutVisit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class NotificationService extends BaseService {

	@Autowired
	CrmServiceClient crmServiceClient;

	@Autowired
	GeTuiService pushService;
	
	@Autowired
	Environment  evn;

	@Scheduled(cron = "${signout.cron}")
	public void sendSignOutNotifications() {
		NotificationType type = NotificationType.SIGNOUT;
		logger.info("start send signout notification --------------");
		OperateResult<List<UnSignOutVisit>> result = crmServiceClient.queryUnSignOut(apiToken);
		if (result.success()) {
			// start push
			PushBody pBody = new PushBody();
			pBody.setApplication("onestong-crm");
			pBody.setContent(evn.getProperty(type.getText()));
			pBody.setPayLoadBody(evn.getProperty(type.getSubject()));
			try {
				pushService.pushBatch(pBody);
			} catch (IOException e) {
				logger.error("error to send " + e.getMessage());
			}
			// end push

			logger.info("query result :" + result.getData().toString() + "size = " + result.getData().size());
		} else {
			logger.error("error to query  un signout  info [" + result.getInnerException());
		}

		logger.info("start send signout notification --------------");
	}
}
