package org.smpp.pdu;

import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;
import org.smpp.util.ByteBuffer;

public class DeliverSMTest {
		
	@Test
	public final void test() throws InvalidPDUException, PDUException {
		
		String bufferHex = "000000d4000000050000000000004140353737320001013235343732313134383139310003003537373200040"
				+ "0010000010003007a69643a30393636323739333432207375623a30303120646c7672643a303031207375626d697420646"
				+ "174653a3133303330363037303420646f6e6520646174653a3133303330363037303520737461743a554e44454c4956206"
				+ "572723a30303020746578743a494e534f20414c4552542c20313030302c204461001e000b303936363237393334320004270"
				+ "001051401000d736166617269636f6d2d6b6500";
//		String bufferHex = "000000d3000000050000000000004140353737320001013235343732313134383139310003003537373200040"
//				+ "0010000010003007a69643a30393636323739333432207375623a30303120646c7672643a303031207375626d697420646"
//				+ "174653a3133303330363037303420646f6e6520646174653a3133303330363037303520737461743a554e44454c4956206"
//				+ "572723a30303020746578743a494e534f20414c4552542c20313030302c204461001e000a3039363632373933343204270"
//				+ "001051401000d736166617269636f6d2d6b6500";
		ByteBuffer buffer = new ByteBuffer(hexStringToByteArray(bufferHex));
		PDU pdu = PDU.createPDU(new ByteBuffer(buffer.getBuffer())); // buffer gets cleared, which we don't want, so supply a copy instead.
		assertThat(pdu).isInstanceOf(DeliverSM.class);
//		assertThat(pdu.debugString()).isEqualTo("(deliver: (pdu: 211 5 0 16704) (addr: 1 1 254721148191)  (addr: 3 0 5772)  (sm: msg: id:0966279342 sub:001 dlvrd:001 submit date:1303060704 done date:1303060705 stat:UNDELIV err:000 text:INSO ALERT, 1000, Da)  (opt: ) ) ");
//		assertThat(pdu.getData().getHexDump()).isEqualTo(buffer.getHexDump());
		
		DeliverSM deliverSm = (DeliverSM) pdu;
		String receiptedMessageId = deliverSm.getReceiptedMessageId();
		assertThat(receiptedMessageId).isEqualTo("0966279342");
		assertThat(pdu.isValid()).isFalse(); // FIXME this passes and I don't think it should.
	}
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

}
