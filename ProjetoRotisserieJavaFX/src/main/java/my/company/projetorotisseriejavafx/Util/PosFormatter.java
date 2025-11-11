package my.company.projetorotisseriejavafx.Util;

import java.nio.charset.StandardCharsets;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PosFormatter {

    // ===== ESC/POS Commands =====
    private static final byte[] INIT = new byte[]{0x1B, '@'};
    private static final byte[] CUT = new byte[]{0x1D, 'V', 0x00};

    private static final byte[] BOLD_ON = new byte[]{0x1B, 0x45, 0x01};
    private static final byte[] BOLD_OFF = new byte[]{0x1B, 0x45, 0x00};

    private static final byte[] CENTER = new byte[]{0x1B, 'a', 0x01};
    private static final byte[] LEFT = new byte[]{0x1B, 'a', 0x00};
    private static final byte[] RIGHT = new byte[]{0x1B, 'a', 0x02};

    private static final byte[] FONT_SMALL = new byte[]{0x1B, 0x4D, 0x01};
    private static final byte[] FONT_NORMAL = new byte[]{0x1D, 0x21, 0x00};
    private static final byte[] FONT_MEDIUM = new byte[]{0x1D, 0x21, 0x01};
    private static final byte[] FONT_WIDE = new byte[]{0x1D, 0x21, 0x10};
    private static final byte[] FONT_DOUBLE = new byte[]{0x1D, 0x21, 0x11};
    private static final byte[] FONT_TRIPLE = new byte[]{0x1D, 0x21, 0x22};
    private static final byte[] FONT_HUGE = new byte[]{0x1D, 0x21, 0x33};

    public static byte[] process(String text) {
        ByteArrayBuilder builder = new ByteArrayBuilder();
        builder.append(INIT);

        Pattern tagPattern = Pattern.compile("\\[(\\/)?([a-zA-Z]+)\\]");
        Matcher matcher = tagPattern.matcher(text);

        int lastEnd = 0;
        Stack<String> tagStack = new Stack<>();

        while (matcher.find()) {
            // texto antes da tag
            if (matcher.start() > lastEnd) {
                String segment = text.substring(lastEnd, matcher.start());
                builder.append(segment.getBytes(StandardCharsets.UTF_8));
            }

            boolean closing = matcher.group(1) != null;
            String tagName = matcher.group(2).toLowerCase();

            if (closing) {
                // Fecha tag -> remove do stack e aplica reset adequado
                if (!tagStack.isEmpty() && tagStack.peek().equals(tagName)) {
                    tagStack.pop();
                }
                builder.append(getCommandForTag("/" + tagName));
            } else {
                // Abre tag
                tagStack.push(tagName);
                builder.append(getCommandForTag(tagName));
            }

            lastEnd = matcher.end();
        }

        // resto do texto
        if (lastEnd < text.length()) {
            builder.append(text.substring(lastEnd).getBytes(StandardCharsets.UTF_8));
        }

        // Corte
        builder.append(CUT);
        return builder.toByteArray();
    }

    private static byte[] getCommandForTag(String tag) {
        return switch (tag) {
            // Estilo
            case "bold" -> BOLD_ON;
            case "/bold" -> BOLD_OFF;

            // Alinhamento
            case "center" -> CENTER;
            case "/center", "left", "/right" -> LEFT;
            case "right" -> RIGHT;

            // Fontes
            case "small" -> FONT_SMALL;
            case "/small" -> FONT_NORMAL;
            case "normal" -> FONT_NORMAL;
            case "medium" -> FONT_MEDIUM;
            case "/medium" -> FONT_NORMAL;
            case "wide" -> FONT_WIDE;
            case "/wide" -> FONT_NORMAL;
            case "double" -> FONT_DOUBLE;
            case "/double" -> FONT_NORMAL;
            case "triple" -> FONT_TRIPLE;
            case "/triple" -> FONT_NORMAL;
            case "huge" -> FONT_HUGE;
            case "/huge" -> FONT_NORMAL;

            default -> new byte[0];
        };
    }

    private static class ByteArrayBuilder {
        private byte[] buffer = new byte[0];

        void append(byte[] data) {
            if (data == null || data.length == 0) return;
            byte[] newBuffer = new byte[buffer.length + data.length];
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            System.arraycopy(data, 0, newBuffer, buffer.length, data.length);
            buffer = newBuffer;
        }

        byte[] toByteArray() {
            return buffer;
        }
    }
}