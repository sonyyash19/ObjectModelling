package com.crio.codingame.commands;

import java.util.Iterator;
import java.util.List;

import com.crio.codingame.entities.ScoreOrder;
import com.crio.codingame.entities.User;
import com.crio.codingame.services.IUserService;

public class LeaderBoardCommand implements ICommand{

    private final IUserService userService;
    
    public LeaderBoardCommand(IUserService userService) {
        this.userService = userService;
    }

    // TODO: CRIO_TASK_MODULE_CONTROLLER
    // Execute getAllUserScoreOrderWise method of IUserService and print the result.
    // Look for the unit tests to see the expected output.
    // Sample Input Token List:- ["LEADERBOARD","ASC"]
    // or
    // ["LEADERBOARD","DESC"]

    @Override
    public void execute(List<String> tokens) {

        if(tokens.get(0).equalsIgnoreCase("leaderboard")){
            List<User> users = userService.getAllUserScoreOrderWise(ScoreOrder.valueOf(tokens.get(1)));

        Iterator<User> iterator = users.iterator();
        System.out.print("[");
        while (iterator.hasNext()) {
            User user = iterator.next();
            System.out.print(user);
            if (iterator.hasNext()) {
                System.out.print(", ");
            }
            
        }
        System.out.print("]");
        }

    }
    
}
