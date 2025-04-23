package com.saadmeddiche.dailyhelper.excel_files.tables;

import com.poiji.annotation.ExcelCellName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {

    @ExcelCellName("id")
    private String id;

    @ExcelCellName("email")
    private String email;

}
