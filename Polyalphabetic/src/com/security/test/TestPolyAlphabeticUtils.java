package com.security.test;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.mutable.MutableInt;

import com.security.util.OperationType;
import com.security.util.PolyAlphabeticUtils;

public class TestPolyAlphabeticUtils {

	@org.junit.Test
	public void testConvert() {
		assertEquals('d', PolyAlphabeticUtils.getInstance().convert('a', OperationType.ENCRYPT, 3));
		assertEquals('z', PolyAlphabeticUtils.getInstance().convert('w', OperationType.ENCRYPT, 3));
		assertEquals('a', PolyAlphabeticUtils.getInstance().convert('z', OperationType.ENCRYPT, 1));
		assertEquals('e', PolyAlphabeticUtils.getInstance().convert('z', OperationType.ENCRYPT, 5));
	}

	@org.junit.Test
	public void testEncryptCharacter() {
		assertEquals('f', PolyAlphabeticUtils.getInstance().encryptCharacter('a', OperationType.ENCRYPT,
				new MutableInt(0), 5, 7));
		assertEquals('h', PolyAlphabeticUtils.getInstance().encryptCharacter('a', OperationType.ENCRYPT,
				new MutableInt(1), 5, 7));
		assertEquals('f', PolyAlphabeticUtils.getInstance().encryptCharacter('a', OperationType.ENCRYPT,
				new MutableInt(2), 5, 7));
		assertEquals('d', PolyAlphabeticUtils.getInstance().encryptCharacter('y', OperationType.ENCRYPT,
				new MutableInt(0), 5, 7));
		assertEquals('f', PolyAlphabeticUtils.getInstance().encryptCharacter('y', OperationType.ENCRYPT,
				new MutableInt(1), 5, 7));
		assertEquals('d', PolyAlphabeticUtils.getInstance().encryptCharacter('y', OperationType.ENCRYPT,
				new MutableInt(2), 5, 7));
	}

	@org.junit.Test
	public void testEncrypt1() {
		assertEquals("ABC", PolyAlphabeticUtils.getInstance().encrypt("ABC", new int[] { 3, 5 }));
	}

	@org.junit.Test
	public void testEncrypt2() {
		assertEquals("Aze", PolyAlphabeticUtils.getInstance().encrypt("Awz", new int[] { 3, 5 }));
	}

	@org.junit.Test
	public void testEncrypt3() {
		assertEquals("ete, n otyj btx.",
				PolyAlphabeticUtils.getInstance().encrypt("bob, i love you.", new int[] { 3, 5 }));
	}

	@org.junit.Test
	public void testDecrypt() {
		assertEquals("bob, i love you.",
				PolyAlphabeticUtils.getInstance().decrypt("ete, n otyj btx.", new int[] { 3, 5 }));
	}

}
