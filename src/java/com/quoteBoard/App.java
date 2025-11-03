package com.quoteBoard;


import com.quoteBoard.controller.SystemController;
import com.quoteBoard.dto.Command;
import com.quoteBoard.utils.UIUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    private final BufferedReader br;
    private final SystemController controller;

    public App(InputStream in) {
        this.br = new BufferedReader(new InputStreamReader(in));
        this.controller = new SystemController(in);
    }


    public void run() throws IOException {
        System.out.println(UIUtils.title());

        while(true) {
            System.out.print(UIUtils.cmdPrefix());
            String cmd = br.readLine();

            if(cmd.equals(Command.TERMINATE.label())) {   //종료
                break;
            } else if(cmd.equals(Command.REGISTER.label())) {   //등록
                controller.register();
            } else if(cmd.equals(Command.LIST.label())) {   //목록
                controller.list();
            } else if(cmd.startsWith(Command.DELETE.label())) {
                String value = cmd.substring(Command.DELETE.label().length());

                Long id = idParser(value);
                controller.delete(id);
            } else if(cmd.startsWith(Command.UPDATE.label())) {
                String value = cmd.substring(Command.UPDATE.label().length());
                Long id = idParser(value);
                controller.update(id);
            } else if(cmd.equals(Command.BUILD.label())) {
                controller.build();
            }
        }
    }

    private Long idParser(String cmd) {
        Pattern pattern = Pattern.compile("\\?id=(\\d+)");
        Matcher matcher = pattern.matcher(cmd);
        if(matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }
        return -1L;
    }
}
