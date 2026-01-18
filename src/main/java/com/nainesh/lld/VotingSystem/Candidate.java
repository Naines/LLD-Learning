package com.nainesh.lld.VotingSystem;

public class Candidate {
    String id, party, name;

    public Candidate(String id, String party, String name) {
        this.id = id;
        this.party = party;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getParty() {
        return party;
    }

    public String getName() {
        return name;
    }
}
