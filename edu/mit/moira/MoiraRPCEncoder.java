package edu.mit.moira;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;


/**
 * 
 * Netty will need to have a GDB encoder/decoder.
 * 
 * The Moira RPC format is:
 * 
 * 4-byte total length (including these 4 bytes)
 * 4-byte version number (MR_VERSION_2 == 2)
 * 4-byte opcode (from client) or status (from server)
 * 4-byte argc
 * 
 * 4-byte len, followed by null-terminated string, padded to 4-byte boundary
 *   (the len doesn't include the padding)
 * ...
 *
 * (followed by more packets if status was MR_MORE_DATA)
 *
 * All numbers are in network byte order.
 * 
 * 
 * @author jmorzins
 *
 */
public class MoiraRPCEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
