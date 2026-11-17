import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PixelDiff {
    public static void main(String[] args) throws Exception {
        Path baselineDir = Paths.get(args.length > 0 ? args[0] : "pixel-baseline");
        Path currentDir = Paths.get(args.length > 1 ? args[1] : "pixel-current");
        int tolerance = args.length > 2 ? Integer.parseInt(args[2]) : 8;
        double maxRatio = args.length > 3 ? Double.parseDouble(args[3]) : 0.01;
        Path report = Paths.get(args.length > 4 ? args[4] : "pixel-report.txt");
        int ignoreTop = args.length > 5 ? Integer.parseInt(args[5]) : 90;
        int ignoreBottom = args.length > 6 ? Integer.parseInt(args[6]) : 40;

        List<Path> files = new ArrayList<>();
        try (var stream = Files.list(baselineDir)) {
            stream.filter(p -> p.getFileName().toString().endsWith(".png")).forEach(files::add);
        }
        files.sort(Comparator.comparing(p -> p.getFileName().toString()));

        List<String> lines = new ArrayList<>();
        int failed = 0;
        for (Path file : files) {
            String name = file.getFileName().toString();
            Path current = currentDir.resolve(name);
            if (!Files.exists(current)) {
                lines.add("MISSING  " + name);
                failed++;
                continue;
            }
            BufferedImage a = ImageIO.read(file.toFile());
            BufferedImage b = ImageIO.read(current.toFile());
            if (a == null || b == null || a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight()) {
                lines.add("FAIL  " + name + "  dimension mismatch");
                failed++;
                continue;
            }
            int startY = Math.min(ignoreTop, a.getHeight());
            int endY = Math.max(startY, a.getHeight() - ignoreBottom);
            long diff = 0;
            long total = (long) a.getWidth() * (endY - startY);
            for (int y = startY; y < endY; y++) {
                for (int x = 0; x < a.getWidth(); x++) {
                    int pa = a.getRGB(x, y);
                    int pb = b.getRGB(x, y);
                    int dr = Math.abs(((pa >> 16) & 0xff) - ((pb >> 16) & 0xff));
                    int dg = Math.abs(((pa >> 8) & 0xff) - ((pb >> 8) & 0xff));
                    int db = Math.abs((pa & 0xff) - (pb & 0xff));
                    if (dr > tolerance || dg > tolerance || db > tolerance) diff++;
                }
            }
            double ratio = (double) diff / total;
            String state = ratio <= maxRatio ? "PASS" : "FAIL";
            if (!state.equals("PASS")) failed++;
            lines.add(String.format("%s  %s  diff=%d/%d  ratio=%.4f%%", state, name, diff, total, ratio * 100));
        }
        lines.add(String.format("threshold=%.2f%%  tolerance=%d/channel  failed=%d", maxRatio * 100, tolerance, failed));
        Files.write(report, lines);
        lines.forEach(System.out::println);
        System.exit(failed > 0 ? 1 : 0);
    }
}
