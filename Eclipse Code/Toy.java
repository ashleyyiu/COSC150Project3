import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

public class Toy {
    String toyName = null;
    BufferedImage image = null;
    int price = 0;

    public Toy() {
    }

    public Toy(String toyName, int price, BufferedImage image) {
        this.toyName = toyName;
        this.image = image;
        this.price = price;
    }

    public Toy(byte[] byteArray) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap(byteArray);

            int nameLength = buffer.getInt();
            byte[] nameBuffer = new byte[nameLength ];
            buffer.get(nameBuffer, 0, nameLength);
            this.toyName = new String(nameBuffer);

            this.price = buffer.getInt();

            int imageLength = buffer.getInt();
            byte[] imageBuffer = new byte[imageLength];
            buffer.get(imageBuffer, 0, imageLength);
            this.image = ImageIO.read(new ByteArrayInputStream(imageBuffer));
        }
        catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    static Toy getToyInfo(byte[] byteArray) {
        Toy toy = new Toy(byteArray);
        return toy;
    }

    public int getSizeInBytes() {
        int size = 0;
        size += Integer.BYTES + toyName.length();
        size += Integer.BYTES;
        size += Integer.BYTES + getImageSize();

        return size;
    }

    public String getToyName() {
        return toyName;
    }

    public int getPrice() {
        return price;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getImageSize() {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", baos);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray().length;
    }

    void putIntToByteArray(int number, ByteArrayOutputStream baos) throws IOException {
        ByteBuffer b = ByteBuffer.allocate(Integer.BYTES);
        b.putInt(number);
        baos.write(b.array());
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        try {
            putIntToByteArray(toyName.length(), baos);
            baos.write(toyName.getBytes());
            putIntToByteArray(price, baos);
            putIntToByteArray(getImageSize(), baos);
            ImageIO.write(image, "jpg", baos);
        }
        catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return baos.toByteArray();
    }
}
