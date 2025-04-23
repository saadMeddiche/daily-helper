package com.saadmeddiche.dailyhelper.excel_files.utils;

import com.poiji.bind.Poiji;

import java.io.File;
import java.util.List;

public class ExcelDataExtractor {

    public static <O> List<O> extract(String absolutePath, Class<O> dataHolderClass) throws IllegalArgumentException {

        File file = new File(absolutePath);

        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist: " + absolutePath);
        }

        return Poiji.fromExcel(file, dataHolderClass);
    }
}
