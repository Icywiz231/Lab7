package Tests;

import p1.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestDel{
	@Test
	public void testDel()throws Exception{
		Play.delete("play1.txt","abcd");
		String s="abd asa 124\nasd ads 321\nafs sda 112";
		assertEquals("Match found",s,Play.show("play1.txt"));
	}
}

