package org.ost.edge.onestong.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ost.edge.onestong.OnestongApplication;
import org.ost.edge.onestong.services.api.event.VisitEventService;
import org.ost.entity.event.vo.VisitEventVo;
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
		VisitEventVo   vo = new VisitEventVo();
		vo.setContent("test");
		this.visitEventService.createVisitEvent(u, vo);
	}
}
