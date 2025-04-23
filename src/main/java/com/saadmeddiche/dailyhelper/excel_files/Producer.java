package com.saadmeddiche.dailyhelper.excel_files;

import com.saadmeddiche.dailyhelper.excel_files.tables.User;
import com.saadmeddiche.dailyhelper.excel_files.utils.ExcelDataExtractor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Slf4j
public class Producer {

    private final static String ABSOLUTE_PATH = "C:\\Users\\saadm\\Desktop\\VTG_users.xlsx";

    private final static String OUTPUT_FILE_PATH = "C:\\Users\\saadm\\Desktop\\VTG_users-out.sql";

    private final static String TEMPLATE = "UPDATE utilisateur SET refkuser = '%s' WHERE email like '%s';";

    public static void produce() {

        List<User> users = ExcelDataExtractor.extract(ABSOLUTE_PATH, User.class);

        List<String> emails = List.of("akarim@royalairmaroc.com", "bahamuss12@gmail.com","billeterie@myvip.group","dyaaammar@yahoo.com","khalildrissi90@gmail.com","bellaa.amelie@gmail.com","souad.euchi@gmail.com");

        List<User> filteredUsers = users.stream().filter(user -> !emails.contains(user.getEmail())).toList();

        StringBuilder stringBuilder = new StringBuilder();
        filteredUsers.forEach(user -> stringBuilder.append(String.format(TEMPLATE, user.getId(), user.getEmail())).append("\n"));

        try {
            Files.write(
                    Path.of(OUTPUT_FILE_PATH),
                    stringBuilder.toString().getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            log.error("An error occurred: {}" , e.getMessage());
        }

    }
}
