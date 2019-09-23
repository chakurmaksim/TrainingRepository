package by.training.certificationCenter.controller.command;

import by.training.certificationCenter.service.action.UserFindAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserFindCommand extends Command {
    private UserFindAction action = new UserFindAction();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

    }
}
