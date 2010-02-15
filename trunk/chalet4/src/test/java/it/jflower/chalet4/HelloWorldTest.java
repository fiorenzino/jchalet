package it.jflower.chalet4;

import it.jflower.chalet4.ejb.HelloWorld;

import org.testng.annotations.Test;

public class HelloWorldTest
{
   @Test
   public void testGetText() {
      HelloWorld fixture = new HelloWorld();
      assert "Hello World!".equals(fixture.getText());
   }
}
