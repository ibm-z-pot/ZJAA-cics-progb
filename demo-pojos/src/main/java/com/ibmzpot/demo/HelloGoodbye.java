package com.ibmzpot.demo;

import java.io.UnsupportedEncodingException;

import com.ibm.cics.server.Program;
import com.ibm.cics.server.CommAreaHolder;
import com.ibm.cics.server.CicsConditionException;
import com.ibm.cics.server.InvalidRequestException;

public class HelloGoodbye
{ 
    private static final String CCSID = System.getProperty("com.ibm.cics.jvmserver.local.ccsid");
    private static final String PROGC = "PROGC";

    public static void main(CommAreaHolder cah)
    {
        String progaMessage = new String(cah.getValue());
        String helloMessage = "Hello from Java                 ";
        String goodbyeMessage = "Goodbye from Java               ";

        // LINK to PROGC
        try {
            Program prog = new Program();
            prog.setName(PROGC);
            byte[] progcCA = helloMessage.getBytes(CCSID);
            prog.link(progcCA);
        } catch (UnsupportedEncodingException ue) {
            throw new RuntimeException(ue);
        } catch (InvalidRequestException ire) {
            System.out.println("Invalid request on link - INVREQ");
        } catch (CicsConditionException cce) {
            throw new RuntimeException(cce);
        }    
        
        // RETURN to PROGA
        try {
            byte[] goodbyeCA = goodbyeMessage.getBytes(CCSID);
            cah.setValue(goodbyeCA);
        } catch (UnsupportedEncodingException ue) {
            throw new RuntimeException(ue);     
        } 
    }
}
