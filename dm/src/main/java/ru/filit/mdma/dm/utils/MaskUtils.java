package ru.filit.mdma.dm.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class MaskUtils {
    public String last(String string, int length) {
        return string.substring(string.length() - length);
    }
}
