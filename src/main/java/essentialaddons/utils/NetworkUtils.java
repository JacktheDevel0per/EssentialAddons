package essentialaddons.utils;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.util.Identifier;

public class NetworkUtils {
	public static final byte
		BOOLEAN = -128,
		BYTE = -127,
		SHORT = -126,
		INT = -125,
		LONG = -124,
		FLOAT = -123,
		DOUBLE = -122,
		BYTE_ARRAY = -121,
		INT_ARRAY = -120,
		LONG_ARRAY = -119,
		STRING = -118,
		TEXT = -117,
		UUID = -116,
		IDENTIFIER = -115,
		ITEM_STACK = -114,
		NBT = -113,
		POS = -112;

		public static CustomPayloadS2CPacket getCustomPayloadPacket(Identifier identifier, PacketByteBuf packetByteBuf) {
			//#if MC >= 12002
			return new CustomPayloadS2CPacket(packetByteBuf.writeIdentifier(identifier));
			//#else
			//$$ return new CustomPayloadS2CPacket(identifier, packetByteBuf);
			//#endif
		}





}
