package org.ost.edge.onestong.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ost.edge.onestong.services.api.event.VisitEventService;
import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.event.vo.VisitEventCreateVo;
import org.ost.entity.event.vo.VisitEventUpdateVo;
import org.ost.entity.location.vo.LocationVo;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VisitEventTest {
	@Autowired
	VisitEventService visitEventService;
	@Test
	public void createVisitTest(){
		Users u  = new Users();
		u.setUserId(1);
		u.setRealname("test");
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
		Users u  = new Users();
		u.setUserId(1);
		u.setRealname("test");
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
		Users u  = new Users();
		u.setUserId(1);
		u.setRealname("test");
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
