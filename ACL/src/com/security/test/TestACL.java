package com.security.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.security.model.AccessControl;
import com.security.util.ACL;

public class TestACL {

	@Test
	public void test_constructAccessControls() {
		ACL acl = new ACL(10, "testFilesFolder");
		List<AccessControl> accessControls = acl.getAccessControls();
		for (AccessControl accessControl : accessControls) {
			System.out.println("File:" + accessControl.getFileName() + " UserAccess: ");
			for (String user : accessControl.getUserAccesses().keySet()) {
				System.out.println("USER:" + user + " RIGHT: " + accessControl.getUserAccesses().get(user));
			}
		}
	}

	@Test
	public void test_getFiles() {
		List<String> files = new ACL().getFiles("testFilesFolder");
		assertEquals(4, files.size());
		assertTrue(files.contains("file1.txt"));
		assertTrue(files.contains("file4.txt"));
	}

	@Test
	public void test_createUsers() {
		List<String> users = new ACL().createUsers(4);
		assertEquals(4, users.size());
		assertEquals("User1", users.get(0));
		assertEquals("User4", users.get(3));
	}

}
