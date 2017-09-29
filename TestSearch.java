package Tests;
import p1.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestSearch {
	@Test
	public void test() throws Exception{
		Play.search("play1.txt","abcd");
		String s="abcd xyz 240";
		assertEquals("Match found",s,Play.show("play1.txt"));
	}
}
