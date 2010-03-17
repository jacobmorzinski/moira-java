importPackage(Packages.java.lang);
importPackage(Packages.java.nio);
importPackage(Packages.java.util);
buf = ByteBuffer.allocate(10);
var b = new Byte(0);
buf.putInt(47836422);
for (var i = 0; i<4; i++) {
buf.put(b);
var relativePosition = (buf.position() % 4);
var padding = (4 - relativePosition) % 4;
System.out.println("relPos = " + relativePosition);
System.out.println("padding = " + padding);
var byt = new Byte[padding](0);
Arrays.fill(byt, b);
}
