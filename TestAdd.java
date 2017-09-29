package Tests;
import p1.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestAdd {
	@Test
	public void testAdd()throws Exception{
		Play.add("play1.txt","abcd","xyz",240);
		String s="abd asa 124\nasd ads 321\nafs sda 112\nabcd xyz 240";
		assertEquals("Match found",s,Play.show("play1.txt"));
	}
}
