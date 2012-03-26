package edu.mit.moira;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;

import edu.mit.moira.internal.Constants;

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
public class MoiraRPCDecoder extends FrameDecoder {

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		// Are the length bytes available?
		if (buffer.readableBytes() < 4) {
			return null;
		}

		// Is the rest of the buffer available?
		buffer.markReaderIndex();
		int length = buffer.readInt() - 4;
		if (buffer.readableBytes() < length) {
			buffer.resetReaderIndex();
			return null;
		}

		// Read the frame
		ChannelBuffer frame = buffer.readBytes(length);
		
		int mrversion = frame.readInt();
		if (mrversion != Constants.MR_VERSION_2) {
			throw new UnsupportedEncodingException("Unexpected Moira RPC version");
		}
		int opcode = frame.readInt();
		int argc = frame.readInt();
		byte[][] args = new byte[argc][];
		int[] argl = new int[argc];
		for (int i=0; i<argc; i++) {
			int argLen = frame.readInt();
			argl[i] = argLen;
			args[i] = new byte[argLen];
			frame.readBytes(args[i], 0, argLen);
			int remainder = argLen % 4;         // 0,1,2,3
			int padding = (4 - remainder) % 4;  // 0,3,2,1
			frame.skipBytes(padding);
		}
		
		MoiraParams mp = new MoiraParams(opcode, args);
		return mp;
	}

}
