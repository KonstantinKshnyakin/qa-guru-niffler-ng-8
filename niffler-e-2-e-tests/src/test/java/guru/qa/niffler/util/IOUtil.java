package guru.qa.niffler.util;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

@ParametersAreNonnullByDefault
public class IOUtil {

    @SneakyThrows
    public static @Nonnull InputStream getInputStream(@Nonnull String resourcePath) {
        return new ClassPathResource(resourcePath).getInputStream();
    }

    @SneakyThrows
    public static @Nullable BufferedImage getBufferedImage(String resourcePath) {
        return ImageIO.read(getInputStream(resourcePath));
    }

    @SneakyThrows
    public static void writeBufferedImage(BufferedImage image, String resourcePath) {
        File file = new File("src/test/resources/" + resourcePath);
        ImageIO.write(image, "png", file);
    }

    @SneakyThrows
    public static @Nonnull byte[] writeImageToByteArr(BufferedImage image) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", os);
            return os.toByteArray();
        }
    }
}
