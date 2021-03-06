package com.amazonaws.lambda.demo;

import static org.junit.Assert.*;


import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.demo.db.SegmentsDAO;
import com.amazonaws.lambda.demo.http.DeleteVideoSegmentRequest;
import com.amazonaws.lambda.demo.http.DeleteVideoSegmentResponse;
import com.amazonaws.lambda.demo.model.Segment;
import com.amazonaws.services.lambda.runtime.Context;

public class DeleteSegmentHandlerTest {

	@Test
    public void deleteVideoSegment() {
		SegmentsDAO cd = new SegmentsDAO();
		try {
		Segment tyler = cd.getSegment("555");
        DeleteVideoSegmentRequest req = new DeleteVideoSegmentRequest(tyler.getID());
        DeleteVideoSegmentResponse res = new DeleteSegmentHandler().handleRequest(req, createContext("Delete"));
        DeleteVideoSegmentResponse res2 = new DeleteSegmentHandler().handleRequest(req, createContext("Delete"));
        DeleteVideoSegmentRequest req3 = new DeleteVideoSegmentRequest();
        req3.setName("Delete the void");
        // now delete
        Assert.assertEquals(200, res.statusCode);
        Assert.assertNotEquals("DeleteSegment(555)", res.toString());
        Assert.assertEquals(422, res2.statusCode);
        Assert.assertNotEquals("Error(555, statusCode=422, err=Unable to delete segment.)", res2.toString());
		}catch (Exception e) {
			fail("didn't work:" + e.getMessage());
			

		
		}
    }
	
	Context createContext(String apiCall) {
        TestContext2 ctx = new TestContext2();
        ctx.setFunctionName(apiCall);
        return ctx;
    }

}
