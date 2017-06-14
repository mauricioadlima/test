package com.mixconnector.domain;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mixconnector.entity.NfeEntity;
import com.mixconnector.entity.NfeEntity.Status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NfeServiceTest {

	@Autowired
	private NfeService nfeService;

	@Test(expected = ConstraintViolationException.class)
	public void saveAll() {
		NfeEntity nfeEntity = new NfeEntity(null, null);
		nfeService.saveAll(Collections.singletonList(nfeEntity));
	}

	@Test
	public void getAllGroupedByStatus() {
		NfeEntity nfeEntity1 = new NfeEntity("1", "SP");
		nfeEntity1.setStatus(Status.ERROR);

		NfeEntity nfeEntity2 = new NfeEntity("2", "SP");
		nfeEntity2.setStatus(Status.WAITING);

		NfeEntity nfeEntity3 = new NfeEntity("3", "SP");
		nfeEntity3.setStatus(Status.WAITING);

		NfeEntity nfeEntity4 = new NfeEntity("4", "SP");
		nfeEntity4.setStatus(Status.WAITING);

		nfeService.saveAll(Arrays.asList(nfeEntity1, nfeEntity2, nfeEntity3));
		nfeService.saveAll(Collections.singletonList(nfeEntity4));

		final List<NfeStatusReport> statusReports = nfeService.getAllGroupedByStatus();
		assertEquals(3, statusReports.size());
		assertEquals(1, statusReports.stream()
									 .filter(n -> n.getStatus()
												   .equals("ERROR"))
									 .findFirst()
									 .get()
									 .getTotal());
		assertEquals(2, statusReports.stream()
									 .filter(n -> n.getStatus()
												   .equals("WAITING") && n.getLotId()
																		  .equals(nfeEntity1.getLotId()))
									 .findFirst()
									 .get()
									 .getTotal());
		assertEquals(1, statusReports.stream()
									 .filter(n -> n.getStatus()
												   .equals("WAITING") && n.getLotId()
																		  .equals(nfeEntity4.getLotId()))
									 .findFirst()
									 .get()
									 .getTotal());

	}

}