package com.crio.codingame.commands;

import java.util.Iterator;
import java.util.List;

import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.Level;
import com.crio.codingame.services.IContestService;

public class ListContestCommand implements ICommand{

    private final IContestService contestService;
    
    public ListContestCommand(IContestService contestService) {
        this.contestService = contestService;
    }

    // TODO: CRIO_TASK_MODULE_CONTROLLER
    // Execute getAllContestLevelWise method of IContestService and print the result.
    // Look for the unit tests to see the expected output.
    // Sample Input Token List:- ["LIST_CONTEST","HIGH"]
    // or
    // ["LIST_CONTEST"]

    @Override
    public void execute(List<String> tokens) {

        if(tokens.get(0).equalsIgnoreCase("list-contest")){
            Level level = null;

        if(tokens.size() == 2){
            level = Level.valueOf(tokens.get(1));
        }

        List<Contest> contests = contestService.getAllContestLevelWise(level);

        System.out.print("[");
        Iterator<Contest> iterator = contests.iterator();
    while (iterator.hasNext()) {
        Contest contest = iterator.next();
        System.out.print(contest);
        if (iterator.hasNext()) {
            System.out.print(", ");
        }
     }
        System.out.print("]");
        }

    }
    
}
