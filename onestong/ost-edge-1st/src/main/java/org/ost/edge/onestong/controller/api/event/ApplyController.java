package org.ost.edge.onestong.controller.api.event;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "approval")
public class ApplyController {

	@PostMapping(value = "")
	public void createApply() {

	}

	@PutMapping(value = "/{id}")
	public void updateApply() {

	}

	@DeleteMapping(value = "")
	public void deleteApply() {

	}

}
