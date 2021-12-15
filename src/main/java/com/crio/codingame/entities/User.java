package com.crio.codingame.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class User extends BaseEntity {
   
    private final String name;
    private final Integer score;
    private List <Contest> contests;
    public User(String id, String name, Integer score, List<Contest> contests) {
        this(id,name,score);
        this.contests = contests;
    }

    public User(String id, String name, Integer score) {
        this(name,score);
        this.id = id;
        this.contests = new ArrayList<Contest>();
    }

    public User(String name, Integer score) {
        this.name = name;
        this.score = score;
    }


    public String getName() {
        return name;
    }


    public Integer getScore() {
        return score;
    }

    public void addContest(Contest contest){
        contests.add(contest);
    }

    public void deleteContest(Contest contest){
        contests.removeIf(c -> c.getId() == contest.getId());
    }

    public List<Contest> getContests() {
        return contests.stream().collect(Collectors.toList());
    }

    public boolean checkIfContestExists(Contest contest){
        for(Contest c : contests){
            if(c.getId().equals(contest.getId())){
                return true;
            }
        }
        return false;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", contests=" + contests + ", name=" + name + ", score=" + score + "]";
    }
    
    
    
}

