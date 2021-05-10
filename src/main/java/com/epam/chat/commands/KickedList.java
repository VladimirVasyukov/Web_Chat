package com.epam.chat.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class KickedList implements Command {
    private final Processor processor;

    public KickedList(Processor processor) {
        this.processor = processor;
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processor.getAllKicked(req, resp);
    }
}
