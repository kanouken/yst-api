package org.ost.edge.onestong.event;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ost.edge.onestong.OnestongApplication;
import org.ost.edge.onestong.services.api.event.VisitEventService;
import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.event.VisitEvents;
import org.ost.entity.event.vo.VisitEventCreateVo;
import org.ost.entity.event.vo.VisitEventUpdateVo;
import org.ost.entity.location.vo.LocationVo;
import org.ost.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OnestongApplication.class)
@Transactional
public class VisitEventTest {
	@Autowired
	VisitEventService visitEventService;
	@Test
	public void createVisitTest(){
		User u  = new User();
		u.setId(1);
		u.setName("test");
		u.settId(1);
		VisitEventCreateVo   vo = new VisitEventCreateVo();
		vo.setContent("test");
		vo.setContactType("test");
		vo.setBusChange("测试");
		vo.setContactTime("2014-01-11 23:59");
		CustomerVo cvo = new CustomerVo();
		cvo.setId(1);
		cvo.setName("test");
		vo.setCustomer(cvo);
		this.visitEventService.createVisitEvent(u, vo);
	}
	@Test
	public void visitInTest(){
		User u  = new User();
		u.setId(1);
		u.setName("test");
		u.settId(1);
		LocationVo lvo = new LocationVo();
		lvo.setLng("11111");
		lvo.setLat("23");
		
		VisitEventUpdateVo veo = new VisitEventUpdateVo();
		veo.setLocation(lvo);
		veo.seteId("5470fe5f-acab-4296-a465-3ea1279fb840");
		this.visitEventService.visitIn(u, veo);
	}
	
	@Test
	public void writeResult(){
		User u  = new User();
		u.setId(1);
		u.setName("test");
		u.settId(1);
		LocationVo lvo = new LocationVo();
		lvo.setLng("11111");
		lvo.setLat("23");
		
		VisitEventUpdateVo veo = new VisitEventUpdateVo();
		veo.setContent("test");
		veo.setLocation(lvo);
		veo.seteId("5470fe5f-acab-4296-a465-3ea1279fb840");
		this.visitEventService.writeResult(u, veo);
	}
}
