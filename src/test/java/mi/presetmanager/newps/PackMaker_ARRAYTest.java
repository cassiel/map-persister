package mi.presetmanager.newps;

import static org.junit.Assert.*;

import org.junit.Test;

public class PackMaker_ARRAYTest {
	@Test
	public void testSimpleArray() {
		PackMaker p = new PackMaker_ARRAY("ONE", "TWO", "THREE");
		assertEquals("ONE", p.walk00(0).look());
		assertEquals("THREE", p.walk00(2).look());
	}
}
